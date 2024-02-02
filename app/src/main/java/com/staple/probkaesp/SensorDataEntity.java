package com.staple.probkaesp;

import androidx.room.*;

@Entity(tableName = "sensordata")
public class SensorDataEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "sensor_type")
    public String sensorType;

    @ColumnInfo(name = "sensor_data")
    public String sensorData;

    @ColumnInfo(name = "timestamp")
    public String timestamp;
}
