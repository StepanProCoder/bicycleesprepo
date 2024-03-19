package com.staple.probkaesp.datamodels;

import androidx.room.*;

import java.util.List;

@Dao
public interface SensorDataDao {

    @Insert
    void insert(SensorDataEntity sensorDataEntity);

    @Query("SELECT * FROM sensordata WHERE sensor_type = :sensorType")
    SensorDataEntity getSensorData(String sensorType);

    @Query("SELECT * FROM sensordata")
    List<SensorDataEntity> getAllSensorData();

    @Query("DELETE FROM sensordata")
    void deleteAll();
}

