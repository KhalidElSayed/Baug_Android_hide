package com.alorma.baug.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.action1).setOnClickListener(this);
        findViewById(R.id.action2).setOnClickListener(this);
        findViewById(R.id.action3).setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView);

        boolean enabled = checkRestriction(RestrictedProfilesBroadcast.KEY_BOOLEAN);
        findViewById(R.id.action3).setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action1:
                int result1 = 34 / 2;
                showResult("Result: " + result1);
                break;
            case R.id.action2:
                int result2 = 0 / 2;
                showResult("Result: " + result2);
                break;
            case R.id.action3:
                int result3 = 34 / 0;
                showResult("Result: " + result3);
                break;
        }
    }

    private void showResult(String message) {
        textView.setText(message);
    }

    private boolean checkRestriction(String restrictionKey) {
        boolean isEnabled = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Bundle mRestrictionsBundle =
                    ((UserManager) getSystemService(Context.USER_SERVICE))
                            .getApplicationRestrictions(getPackageName());

            if (mRestrictionsBundle != null && mRestrictionsBundle.containsKey(restrictionKey)) {
                isEnabled = mRestrictionsBundle.getBoolean(restrictionKey);
            }
        }
        return isEnabled;
    }
}
