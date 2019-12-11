package dicoding1.moviecatalog.utilities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.http.Api;
import dicoding1.moviecatalog.http.Helper;
import dicoding1.moviecatalog.model.MovieModel;
import dicoding1.moviecatalog.model.ResultMovie;
import dicoding1.moviecatalog.notification.MovieDailyReceiver;
import dicoding1.moviecatalog.notification.MovieUpcomingReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dicoding1.moviecatalog.utilities.mAppCompatActivity.mCurrentLocale;

public class SettingsPrefActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        SwitchPreference switchReminder;
        SwitchPreference switchToday;

        MovieDailyReceiver movieDailyReceiver = new MovieDailyReceiver();
        MovieUpcomingReceiver movieUpcomingReceiver = new MovieUpcomingReceiver();

        List<ResultMovie> resultMovies, sameMovieList;
        Api api;



        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            resultMovies = new ArrayList<>();
            sameMovieList = new ArrayList<>();

            switchReminder = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));
            switchReminder.setOnPreferenceChangeListener(this);
            switchToday = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
            switchToday.setOnPreferenceChangeListener(this);


            Preference myPref = findPreference(getString(R.string.key_lang));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean b = (boolean) newValue;

            if(key.equals(getString(R.string.key_today_reminder))){
                if(b){
                    movieDailyReceiver.setAlarm(getActivity());
                }else{
                    movieDailyReceiver.cancelAlarm(getActivity());
                }
            }else{
                if(b){
                    setReleaseAlarm();
                }else{
                    movieUpcomingReceiver.cancelAlarm(getActivity());
                }
            }

            return true;
        }

        private void setReleaseAlarm(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String now = dateFormat.format(date);

            api = Helper.getInstanceRetrofit();
            api.getUpcoming(mCurrentLocale)
                    .enqueue(new Callback<MovieModel>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    resultMovies = response.body().getResults();
                                    for(ResultMovie resultMovie : resultMovies){
                                        if(resultMovie.getReleaseDate().equals(now)){
                                            sameMovieList.add(resultMovie);
                                            Log.v("Special", "Upcoming : " + sameMovieList.size());
                                        }
                                    }
                                    movieUpcomingReceiver.setAlarm(getActivity(), sameMovieList);
                                }
                            }
                            else {
                                Toast.makeText(getActivity(), "Get List Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
