package com.staple.probkaesp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> statusTextLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusGetOrPost = new MutableLiveData<>();

    // LiveData to observe button click event
    private MutableLiveData<Boolean> buttonClickedLiveData = new MutableLiveData<>();
    private HashMap<String, Esp8266Api> ipIdMap = new HashMap<>();
    private NsdDiscovery nsdDiscovery;
    private String curUUId;

    private File jsonFile;
    private  boolean eraseFlag = false;

    private Timer timer = new Timer();
    private Runnable handshakeLambda;

    public void initializeEsp8266Api(String hostName, String ipAddress) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ipIdMap.put(hostName, retrofit.create(Esp8266Api.class));
    }

    public void onInit(Runnable handshakeLambda, Context context) {
        this.handshakeLambda = handshakeLambda;
        statusGetOrPost.setValue(false);

        curUUId = SaveLoadResult.loadResult("SystemSensors", "uuid", context);

        jsonFile = new File(context.getCacheDir(), context.getString(R.string.json_path));
        if (jsonFile.exists()) {
            Log.d("JSONFILE", loadJsonFromCache(jsonFile));
            nsdDiscovery = new NsdDiscovery(context, this);
            nsdDiscovery.startDiscovery();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    updateState(context);
                }
            }, 0, 3000);

        }
        else {
            handshakeLambda.run();
            //timer.cancel();
        }

    }

    private void updateState(Context context) {

        nsdDiscovery.stopDiscovery();
        nsdDiscovery.startDiscovery();

        for (Map.Entry<String, Esp8266Api> entry : ipIdMap.entrySet()) {
            Log.d("ENTRY", entry.getKey());
            if (entry.getValue() != null) {
                fetchSwitchStatus(entry);
            } else {
                statusTextLiveData.postValue("Ошибка: ESP8266 API не инициализирован");
            }
        }
    }

    // Method to handle button click
    public void onConfigButtonClick(Activity activity) {
        Log.d("BTN", "TAPPED");
        // Update the LiveData to notify the View that the button was clicked
        buttonClickedLiveData.setValue(true);
        ActivityUtils.startNewActivityAndFinishCurrent(activity, SensorSelectionActivity.class);
        timer.cancel();
    }

    public void onRefreshButtonClick() {
        statusGetOrPost.setValue(false);
        eraseFlag = true;
    }

    // Method for executing the API request to fetch switch status
    private void fetchSwitchStatus(Map.Entry<String, Esp8266Api> entry) {
        if(statusGetOrPost.getValue()) {
            Log.d("RETROFIT","GET");
            entry.getValue().getSensorData().enqueue(new ResponseGetHandler(statusGetOrPost, statusTextLiveData));
        }
        else {
            Log.d("FETCH",curUUId);
            if(entry.getKey().equals(curUUId)) {
                Log.d("RETROFIT", "POST");
                RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), eraseFlag? "ERASE": loadJsonFromCache(jsonFile));
                entry.getValue().
                        postConfig(requestBody).
                        enqueue(new ResponsePostHandler(statusGetOrPost, handshakeLambda));
                eraseFlag = false;
            }
        }
    }

    // Method to get the LiveData with the text status
    public LiveData<String> getStatusTextLiveData() {
        return statusTextLiveData;
    }

    // Expose LiveData to observe button click event from the View
    public LiveData<Boolean> getButtonClickedLiveData() {
        return buttonClickedLiveData;
    }

    private String loadJsonFromCache(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
