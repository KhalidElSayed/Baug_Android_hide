package com.alorma.baug.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Bernat on 08/04/2014.
 */
public class RestrictedProfilesBroadcast extends BroadcastReceiver {
    public static final String KEY_BOOLEAN = "KEY_1";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            PendingResult result = goAsync();
            Bundle extras = new Bundle();
            Intent customIntent = new Intent();
            customIntent.setClass(context, SettingsActivity.class);
            extras.putParcelable(Intent.EXTRA_RESTRICTIONS_INTENT, customIntent);
            result.setResult(Activity.RESULT_OK, null, extras);
            result.finish();
        }
    }
}
