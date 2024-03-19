package com.staple.probkaesp.utils.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtils
{
    public static void startNewActivity(Context context, Class<? extends Activity> activityClass)
    {
        Intent intent = new Intent(context, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
