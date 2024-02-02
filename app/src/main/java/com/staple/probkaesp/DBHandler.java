package com.staple.probkaesp;

import android.content.Context;

import java.util.List;

public class DBHandler {

    private SensorDataDao sensorDataDao;

    public DBHandler(Context context) {
        SensorDatabase sensorDatabase = SensorDatabase.getDatabase(context);
        sensorDataDao = sensorDatabase.sensorDataDao();
    }

    public void addNewSensorData(String sensorType, String sensorData, String curTime) {
        SensorDataEntity sensorDataEntity = new SensorDataEntity();
        sensorDataEntity.sensorType = sensorType;
        sensorDataEntity.sensorData = sensorData;
        sensorDataEntity.timestamp = curTime;
        sensorDataDao.insert(sensorDataEntity);
    }

    public String getSensorData(String sensorType) {
        SensorDataEntity sensorDataEntity = sensorDataDao.getSensorData(sensorType);
        return sensorDataEntity != null ? sensorDataEntity.sensorData : "";
    }

    public String getAllSensorData() {
        List<SensorDataEntity> sensorDataEntities = sensorDataDao.getAllSensorData();
        StringBuilder result = new StringBuilder();
        for (SensorDataEntity entity : sensorDataEntities) {
            result.append("Sensor Type: ").append(entity.sensorType)
                    .append(", Sensor Data: ").append(entity.sensorData)
                    .append(", Timestamp: ").append(entity.timestamp).append("\n");
        }
        return result.toString();
    }

    public void updateDB() {
        sensorDataDao.deleteAll();
    }
}
