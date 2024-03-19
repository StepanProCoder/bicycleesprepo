package com.staple.probkaesp.datamodels;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.staple.probkaesp.datamodels.SensorData;
import com.staple.probkaesp.datamodels.SensorDataDeserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorDataFactory {
    private static final Map<String, Class<?>> typeMappings = new HashMap<>();

    static {
        typeMappings.put("id", String.class);
        typeMappings.put("Gerkon", Float.class);
        // Добавьте другие типы датчиков, если необходимо
    }

    public static Class<?> getDataType(String sensorType) {
        return typeMappings.get(sensorType);
    }

    public static List<SensorData<?>> parseSensorDataListFromJson(String jsonString) {
        List<SensorData<?>> sensorDataList = new ArrayList<>();

        // Создаем экземпляр Gson с пользовательским адаптером для динамического определения типа данных
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SensorData.class, new SensorDataDeserializer())
                .create();

        // Парсим JSON-строку в список объектов SensorDataModel
        Type listType = new TypeToken<List<SensorData<?>>>() {}.getType();
        sensorDataList = gson.fromJson(jsonString, listType);

        return sensorDataList;
    }
}

