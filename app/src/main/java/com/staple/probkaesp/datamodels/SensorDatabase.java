package com.staple.probkaesp.datamodels;

import androidx.room.*;
import android.content.Context;

@Database(entities = {SensorDataEntity.class}, version = 1)
public abstract class SensorDatabase extends RoomDatabase {

    public abstract SensorDataDao sensorDataDao();

    private static SensorDatabase INSTANCE;

    public static SensorDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SensorDatabase.class, "sensor_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}

