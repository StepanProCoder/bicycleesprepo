package com.staple.probkaesp;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SensorSelectionViewModel extends ViewModel {

    public void saveJsonToCache(String fileName, String jsonData, SensorSelectionActivity activity) {
        try {
            File file = new File(activity.getCacheDir(), fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonData.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void navigateToNextScreen(SensorSelectionActivity activity) {
        // Завершаем активность и переходим на активность MainActivity
        Intent bluetoothIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(bluetoothIntent);
        activity.finish();
    }

}
