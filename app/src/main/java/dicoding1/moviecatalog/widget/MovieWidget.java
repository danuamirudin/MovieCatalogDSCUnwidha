package dicoding1.moviecatalog.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Objects;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.ui.DetailActivity;

import static dicoding1.moviecatalog.utilities.Static.GENRE;
import static dicoding1.moviecatalog.utilities.Static.ID;
import static dicoding1.moviecatalog.utilities.Static.LANGUAGE;
import static dicoding1.moviecatalog.utilities.Static.OVERVIEW;
import static dicoding1.moviecatalog.utilities.Static.POPULARITY;
import static dicoding1.moviecatalog.utilities.Static.POSTER_IMAGE;
import static dicoding1.moviecatalog.utilities.Static.RELEASE_DATE;
import static dicoding1.moviecatalog.utilities.Static.TITTLE;
import static dicoding1.moviecatalog.utilities.Static.VOTE_AVERAGE;
import static dicoding1.moviecatalog.utilities.Static.VOTE_COUNT;

public class MovieWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "dicoding1.moviecatalog.TOAST_ACTION";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, MovieWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movie_widget);

        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, MovieWidget.class);
        toastIntent.setAction(MovieWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.layout.movie_widget);
        for (int appWidgetId : appWidgetIds) {
            Log.d("Special", "Update widget " + appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager.getInstance(context);
        if (Objects.requireNonNull(intent.getAction()).equals(TOAST_ACTION)) {
            intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            //Toast.makeText(context, intent.getStringExtra(EXTRA_ITEM), Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(context, DetailActivity.class);
            mIntent.putExtra(ID, intent.getStringExtra(ID));
            mIntent.putExtra(POSTER_IMAGE, intent.getStringExtra(POSTER_IMAGE));
            mIntent.putExtra(TITTLE, intent.getStringExtra(TITTLE));
            mIntent.putExtra(OVERVIEW, intent.getStringExtra(OVERVIEW));
            mIntent.putExtra(RELEASE_DATE, intent.getStringExtra(RELEASE_DATE));
            mIntent.putExtra(GENRE, intent.getStringExtra(GENRE));
            mIntent.putExtra(LANGUAGE, intent.getStringExtra(LANGUAGE));
            mIntent.putExtra(VOTE_COUNT, intent.getStringExtra(VOTE_COUNT));
            mIntent.putExtra(VOTE_AVERAGE, intent.getStringExtra(VOTE_AVERAGE));
            mIntent.putExtra(POPULARITY, intent.getStringExtra(POPULARITY));
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);

        }
        super.onReceive(context, intent);

    }
}

