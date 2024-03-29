package com.staple.probkaesp;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseGetHandler implements Callback<ResponseBody> {

    MutableLiveData<String> statusTextLiveData;
    MutableLiveData<Boolean> statusGetOrPost;

    public ResponseGetHandler(MutableLiveData<Boolean> statusGetOrPost, MutableLiveData<String> statusTextLiveData) {
        this.statusGetOrPost = statusGetOrPost;
        this.statusTextLiveData = statusTextLiveData;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//        if (response.isSuccessful()) {
//            SwitchStatus switchStatus = response.body();
//            float speed = switchStatus.getSpeed();
//            statusTextLiveData.setValue(speed + " km/h");
//        } else {
//            statusTextLiveData.setValue("Ошибка при получении статуса");
//        }

        try {
            String jsonString = response.body().string();
            Log.d("JSON", jsonString);
            List<SensorData<?>> sensorDataList = SensorDataFactory.parseSensorDataListFromJson(jsonString);

            String result = convertSensorDataListToString(sensorDataList);
            statusTextLiveData.postValue(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        statusTextLiveData.setValue("Ошибка при отправке запроса");
        statusGetOrPost.setValue(false);
    }

    public static String convertSensorDataListToString(List<SensorData<?>> sensorDataList) {
        StringBuilder sb = new StringBuilder();
        for (SensorData<?> sensorData : sensorDataList) {
            String sensorType = sensorData.getSensorType();
            Object data = sensorData.getData();
            Log.d("TYPE", sensorData.getData().getClass().toString());
            sb.append("Sensor Type: ").append(sensorType).append(", Data: ").append(data).append("\n");
        }
        return sb.toString();
    }

}
