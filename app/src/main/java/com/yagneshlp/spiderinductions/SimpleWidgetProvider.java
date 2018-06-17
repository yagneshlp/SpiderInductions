package com.yagneshlp.spiderinductions;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import com.yagneshlp.spiderinductions.tasks.Task3Activity;

public class SimpleWidgetProvider extends AppWidgetProvider {

    public static final String Update_tasks = "android.appwidget.action.APPWIDGET_UPDATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(Update_tasks)) {
            ComponentName component = new ComponentName(context,SimpleWidgetProvider.class);
            int appWidgetIds[] = mgr.getAppWidgetIds(component);
            Log.e("received", intent.getAction());
            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
            mgr.updateAppWidget(component,new RemoteViews(context.getPackageName(), R.layout.simple_widget));
            onUpdate(context,mgr,appWidgetIds);
        }
        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {
            // Set up the intent that starts the ListViewService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, WidgetRemoteViewsService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(appWidgetIds[i], R.id.widgetListView, intent);
            // Trigger listview item click
            Intent startActivityIntent = new Intent(context, Task3Activity.class);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widgetListView, startActivityPendingIntent);
            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews  object above.
            //rv.setEmptyView(R.id.list_view, R.id.empty_view);
            //
            // Do additional processing specific to this app widget...
            //
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

