package com.staple.probkaesp;
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

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> statusTextLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusGetOrPost = new MutableLiveData<>();

    // LiveData to observe button click event
    private MutableLiveData<Boolean> buttonClickedLiveData = new MutableLiveData<>();
    private Esp8266Api esp8266Api;
    private NsdDiscovery nsdDiscovery;

    private File jsonFile;

    public void initializeEsp8266Api(String ipAddress) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        esp8266Api = retrofit.create(Esp8266Api.class);
    }

    public void onInit(MainActivity activity) {
        statusGetOrPost.setValue(false);
        jsonFile = new File(activity.getCacheDir(), activity.getString(R.string.json_path));
        if (jsonFile.exists()) {
            Log.d("JSONFILE", loadJsonFromCache(jsonFile));
            nsdDiscovery = new NsdDiscovery(activity, this);
            nsdDiscovery.startDiscovery();
        }
        else {
            Intent intent = new Intent(activity, LottieActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }

    }

    // Method to handle button click
    public void onStatusButtonClick() {
        Log.d("BTN", "TAPPED");
        // Update the LiveData to notify the View that the button was clicked
        buttonClickedLiveData.setValue(true);

        if (esp8266Api != null) {
            fetchSwitchStatus();
        } else {
            statusTextLiveData.setValue("Ошибка: ESP8266 API не инициализирован");
        }
    }

    // Method for executing the API request to fetch switch status
    private void fetchSwitchStatus() {
        if(statusGetOrPost.getValue())
            esp8266Api.getSensorData().enqueue(new ResponseGetHandler(statusTextLiveData));
        else {
            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), loadJsonFromCache(jsonFile));
            esp8266Api.postConfig(requestBody).enqueue(new ResponsePostHandler(statusGetOrPost));
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
