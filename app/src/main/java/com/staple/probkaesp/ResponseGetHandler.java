package com.staple.probkaesp;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseGetHandler implements Callback<ResponseBody> {

    MutableLiveData<String> statusTextLiveData;
    MutableLiveData<Boolean> statusGetOrPost;
    DBHandler dbHandler;

    public ResponseGetHandler(MutableLiveData<Boolean> statusGetOrPost, DBHandler dbHandler, MutableLiveData<String> statusTextLiveData) {
        this.statusGetOrPost = statusGetOrPost;
        this.statusTextLiveData = statusTextLiveData;
        this.dbHandler = dbHandler;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            String jsonString = response.body().string();
            Log.d("JSON", jsonString);
            List<SensorData<?>> sensorDataList = SensorDataFactory.parseSensorDataListFromJson(jsonString);

            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            for (int i = 0; i < sensorDataList.size(); i++)
            {
                dbHandler.addNewSensorData(sensorDataList.get(i).getSensorType(), sensorDataList.get(i).getData().toString(), currentTime);
            }

            //String result = convertSensorDataListToString(sensorDataList);
            String result = dbHandler.getAllSensorData();
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
