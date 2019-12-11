package dicoding1.moviecatalog.utilities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

@SuppressLint("Registered")
public class mAppCompatActivity extends AppCompatActivity {
    public static String mCurrentLocale;
    private Locale currentLocale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mCurrentLocale = getLocaleStr();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentLocale = getLocaleStr();
        currentLocale = getLocale();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Locale locale = getLocale();

        if (!locale.equals(currentLocale)) {
            mCurrentLocale = getLocaleStr();
            currentLocale = locale;
            recreate();
        }
    }

    public Locale getLocale() {
        return getResources().getConfiguration().locale;
    }

    public String getLocaleStr() {
        if (getLocale().toString().equalsIgnoreCase("en_US")) {
            return "en_US";
        } else {
            return "id";
        }
    }
}
