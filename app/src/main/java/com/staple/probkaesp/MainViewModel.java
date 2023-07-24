package com.staple.probkaesp;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> statusTextLiveData = new MutableLiveData<>();
    private Esp8266Api esp8266Api;
    private NsdDiscovery nsdDiscovery;

    // LiveData to observe button click event
    private MutableLiveData<Boolean> buttonClickedLiveData = new MutableLiveData<>();

    public void initializeEsp8266Api(String ipAddress) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        esp8266Api = retrofit.create(Esp8266Api.class);
    }

    public void onInit(Context context) {
        nsdDiscovery = new NsdDiscovery(context, this);
        nsdDiscovery.startDiscovery();
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
        esp8266Api.getSwitchStatus().enqueue(new ResponseHandler(statusTextLiveData));
    }

    // Method to get the LiveData with the text status
    public LiveData<String> getStatusTextLiveData() {
        return statusTextLiveData;
    }

    // Expose LiveData to observe button click event from the View
    public LiveData<Boolean> getButtonClickedLiveData() {
        return buttonClickedLiveData;
    }
}
