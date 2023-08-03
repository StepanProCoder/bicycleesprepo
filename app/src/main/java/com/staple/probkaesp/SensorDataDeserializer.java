package com.staple.probkaesp;

import android.util.Log;

import com.google.gson.*;

import java.lang.reflect.Type;

public class SensorDataDeserializer implements JsonDeserializer<SensorData<?>> {
    @Override
    public SensorData<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String sensorType = jsonObject.get("sensor_type").getAsString();
        Class<?> dataType = SensorDataFactory.getDataType(sensorType);

        if (dataType == null) {
            throw new JsonParseException("Unknown sensor type: " + sensorType);
        }

        JsonElement jsonData = jsonObject.get("data");
        Object data = context.deserialize(jsonData, dataType);

        return new SensorData<>(sensorType, data);
    }
}
