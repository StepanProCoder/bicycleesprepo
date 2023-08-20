package com.staple.probkaesp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtils {

    public static void startNewActivity(Context context, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void startNewActivityAndFinishCurrent(Activity currentActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }
}
