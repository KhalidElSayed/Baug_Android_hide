package com.alorma.baug.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;

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
                try {
                    int result3 = 34 / 0;
                    showResult("Result: " + result3);
                } catch (ArithmeticException e) {
                    sendError(e);
                }
                break;
        }
    }

    private void sendError(Exception e) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            sendNewError(e);
        } else {
            sendOldError(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void sendNewError(Exception e) {
        ApplicationErrorReport report = new ApplicationErrorReport();
        report.packageName = report.processName = getApplication().getPackageName();
        report.time = System.currentTimeMillis();
        report.type = ApplicationErrorReport.TYPE_CRASH;
        report.systemApp = false;

        ApplicationErrorReport.CrashInfo crash = new ApplicationErrorReport.CrashInfo();
        crash.exceptionClassName = e.getClass().getSimpleName();
        crash.exceptionMessage = e.getMessage();

        StringWriter writer = new StringWriter();
        PrintWriter printer = new PrintWriter(writer);
        e.printStackTrace(printer);

        crash.stackTrace = writer.toString();

        StackTraceElement stack = e.getStackTrace()[0];
        crash.throwClassName = stack.getClassName();
        crash.throwFileName = stack.getFileName();
        crash.throwLineNumber = stack.getLineNumber();
        crash.throwMethodName = stack.getMethodName();

        report.crashInfo = crash;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClassName("com.google.android.feedback", "com.google.android.feedback.FeedbackActivity");
        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
        startActivity(intent);
    }

    private void sendOldError(Exception e) {
        // DO something with exception
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
