package com.staple.probkaesp;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SensorSelectionViewModel extends ViewModel {

    public void saveBikeDataToJson(File file, boolean hasSpeedometer, String speedometerText,
                                   boolean hasPressureMeter, String pressureMeterText,
                                   boolean hasHeartRateMonitor, String heartRateMonitorText,
                                   int selectedRadius) {
        BikeData bikeData = new BikeData();
        bikeData.setSpeedometer(hasSpeedometer, speedometerText);
        bikeData.setPressureMeter(hasPressureMeter, pressureMeterText);
        bikeData.setHeartRateMonitor(hasHeartRateMonitor, heartRateMonitorText);
        bikeData.setWheelRadius(selectedRadius);

        Gson gson = new Gson();
        String jsonData = gson.toJson(bikeData);

        saveJsonToCache(file, jsonData);
    }
    private void saveJsonToCache(File file, String jsonData) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonData.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
