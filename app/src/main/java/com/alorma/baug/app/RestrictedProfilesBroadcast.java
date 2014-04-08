package com.alorma.baug.app;

import android.annotation.TargetApi;
import android.app.Activity;
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

            final PendingResult result = goAsync();
            final Bundle currentRestrictions = intent.getBundleExtra(Intent.EXTRA_RESTRICTIONS_BUNDLE);

            // Creamos las restriciones
            ArrayList<RestrictionEntry> restrictions = new ArrayList<RestrictionEntry>();
            RestrictionEntry entry1 = new RestrictionEntry(KEY_BOOLEAN, true);
            entry1.setTitle("Dangerous enabled");
            entry1.setType(RestrictionEntry.TYPE_BOOLEAN);

            // Añadimos sus valores si ya han sido creados
            if (currentRestrictions != null) {
                if (currentRestrictions.containsKey(KEY_BOOLEAN)) {
                    entry1.setSelectedState(currentRestrictions.getBoolean(KEY_BOOLEAN));
                }
            }

            restrictions.add(entry1);

            Bundle extras = new Bundle();
            extras.putParcelableArrayList(Intent.EXTRA_RESTRICTIONS_LIST, restrictions);
            result.setResult(Activity.RESULT_OK, null, extras);
            result.finish();
        }
    }
}
