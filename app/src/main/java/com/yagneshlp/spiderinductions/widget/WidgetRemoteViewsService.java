package com.yagneshlp.spiderinductions.widget;


import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetRemoteViewsService extends RemoteViewsService {

    private static final String TAG = WidgetRemoteViewsService.class.getSimpleName();

    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.d(TAG, "onGetViewFactory: " + "Service called");
        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);

    }

}




