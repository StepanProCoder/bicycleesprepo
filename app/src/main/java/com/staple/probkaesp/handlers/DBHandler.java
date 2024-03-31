package com.staple.probkaesp.handlers;

import android.content.Context;

import com.staple.probkaesp.datamodels.SensorDataDao;
import com.staple.probkaesp.datamodels.SensorDataEntity;
import com.staple.probkaesp.datamodels.SensorDatabase;

import java.util.List;

public class DBHandler {

    private SensorDataDao sensorDataDao;

    public DBHandler(Context context) {
        SensorDatabase sensorDatabase = SensorDatabase.getDatabase(context);
        sensorDataDao = sensorDatabase.sensorDataDao();
    }

    public void addNewSensorData(String uuid, String sensorType, String sensorData, String curTime) {
        SensorDataEntity sensorDataEntity = new SensorDataEntity();
        sensorDataEntity.uuid = uuid;
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
            result.append("UUID: ").append(entity.uuid)
                    .append(", Sensor Type: ").append(entity.sensorType)
                    .append(", Sensor Data: ").append(entity.sensorData)
                    .append(", Timestamp: ").append(entity.timestamp).append("\n");
        }
        return result.toString();
    }

    public void updateDB() {
        sensorDataDao.deleteAll();
    }
}
