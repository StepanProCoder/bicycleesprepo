package com.staple.probkaesp;

public class BikeData {
    private int wheelRadius;
    private boolean hasSpeedometer;
    private boolean hasPressureMeter;
    private boolean hasHeartRateMonitor;
    private String speedometerText;
    private String pressureMeterText;
    private String heartRateMonitorText;

    public void setSpeedometer(boolean hasSpeedometer, String speedometerText) {
        this.hasSpeedometer = hasSpeedometer;
        this.speedometerText = speedometerText;
    }

    public void setPressureMeter(boolean hasPressureMeter, String pressureMeterText) {
        this.hasPressureMeter = hasPressureMeter;
        this.pressureMeterText = pressureMeterText;
    }

    public void setHeartRateMonitor(boolean hasHeartRateMonitor, String heartRateMonitorText) {
        this.hasHeartRateMonitor = hasHeartRateMonitor;
        this.heartRateMonitorText = heartRateMonitorText;
    }

    public void setWheelRadius(int selectedRadius) {
        this.wheelRadius = selectedRadius;
    }
}
