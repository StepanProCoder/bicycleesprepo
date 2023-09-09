package com.staple.probkaesp;

public class SensorData<T> {

    private String sensorType;
    private T data;

    public SensorData(String sensorType, T data) {
        this.sensorType = sensorType;
        this.data = data;
    }

    public String getSensorType() {
        return sensorType;
    }

    public T getData() {
        return data;
    }
}
