package com.staple.probkaesp.datamodels;

import com.google.gson.*;

import org.osmdroid.util.GeoPoint;

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
        Object data;
        if (sensorType.equals("geo")) {
            double latitude = Double.valueOf(jsonData.getAsString().split(",")[0]);
            double longitude = Double.valueOf(jsonData.getAsString().split(",")[1]);
            data = new GeoPoint(latitude, longitude);
        }
        else {
            data = context.deserialize(jsonData, dataType);
        }

        return new SensorData<>(sensorType, data);
    }
}
