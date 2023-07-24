package com.staple.probkaesp;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseHandler implements Callback<SwitchStatus> {

    MutableLiveData<String> statusTextLiveData;

    public ResponseHandler(MutableLiveData<String> statusTextLiveData) {
        this.statusTextLiveData = statusTextLiveData;
    }

    @Override
    public void onResponse(Call<SwitchStatus> call, Response<SwitchStatus> response) {
        if (response.isSuccessful()) {
            SwitchStatus switchStatus = response.body();
            float speed = switchStatus.getSpeed();
            statusTextLiveData.setValue(speed + " km/h");
        } else {
            statusTextLiveData.setValue("Ошибка при получении статуса");
        }
    }

    @Override
    public void onFailure(Call<SwitchStatus> call, Throwable t) {
        statusTextLiveData.setValue("Ошибка при отправке запроса");
    }

}
