package dicoding1.moviecatalog.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.model.ResultMovie;
import dicoding1.moviecatalog.ui.DetailActivity;
import dicoding1.moviecatalog.utilities.Static;

import static dicoding1.moviecatalog.utilities.Static.GENRE;
import static dicoding1.moviecatalog.utilities.Static.ID;
import static dicoding1.moviecatalog.utilities.Static.LANGUAGE;
import static dicoding1.moviecatalog.utilities.Static.NOTIFICATION_CHANNEL_ID;
import static dicoding1.moviecatalog.utilities.Static.NOTIFICATION_ID;
import static dicoding1.moviecatalog.utilities.Static.OVERVIEW;
import static dicoding1.moviecatalog.utilities.Static.POPULARITY;
import static dicoding1.moviecatalog.utilities.Static.POSTER_IMAGE;
import static dicoding1.moviecatalog.utilities.Static.RELEASE_DATE;
import static dicoding1.moviecatalog.utilities.Static.TITTLE;
import static dicoding1.moviecatalog.utilities.Static.VOTE_AVERAGE;
import static dicoding1.moviecatalog.utilities.Static.VOTE_COUNT;

public class MovieUpcomingReceiver extends BroadcastReceiver{
    private static int id = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        String description = String.valueOf(String.format(context.getString(R.string.release_today_msg), intent.getStringExtra(Static.TITTLE)));
        sendNotification(context, context.getString(R.string.app_name), description, id, intent);
    }

    private void sendNotification(Context context, String title, String desc, int id, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        intent.setClass(context, DetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);
        }
        Objects.requireNonNull(notificationManager).notify(id, builder.build());
    }

    public void setAlarm(Context context, List<ResultMovie> resultMovies) {
        int delay = 0;

        for (ResultMovie movie : resultMovies) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieUpcomingReceiver.class);
            intent.putExtra(ID, movie.getId());
            intent.putExtra(POSTER_IMAGE, "https://image.tmdb.org/t/p/w780" + movie.getPosterPath());
            intent.putExtra(TITTLE, movie.getTitle());
            intent.putExtra(OVERVIEW, movie.getOverview());
            intent.putExtra(RELEASE_DATE, movie.getReleaseDate());
            intent.putExtra(GENRE, movie.getGenreStr());
            intent.putExtra(LANGUAGE, movie.getOriginalLanguage());
            intent.putExtra(VOTE_COUNT, movie.getVoteCount());
            intent.putExtra(VOTE_AVERAGE, movie.getVoteAverage());
            intent.putExtra(POPULARITY, movie.getPopularity());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                if (alarmManager != null) {
                    alarmManager.setInexactRepeating(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis() + delay,
                            AlarmManager.INTERVAL_DAY,
                            pendingIntent
                    );
                }
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (alarmManager != null) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis() + delay, pendingIntent);
                }
            }
            id += 1;
            delay += 3000;
            Log.v("Special", movie.getTitle());
        }
    }
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }
    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieUpcomingReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
