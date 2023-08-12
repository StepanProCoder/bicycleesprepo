package com.staple.probkaesp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.staple.probkaesp.databinding.ActivityMainBinding;
import com.staple.probkaesp.databinding.ActivitySensorSelectionBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SensorSelectionActivity extends AppCompatActivity {

    private SensorSelectionViewModel sensorSelectionViewModel;
    private ActivitySensorSelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up Data Binding
        binding = ActivitySensorSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel using ViewModelProvider
        sensorSelectionViewModel = new ViewModelProvider(this).get(SensorSelectionViewModel.class);
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем данные о выбранных датчиках
                boolean hasSpeedometer = binding.speedometerCheckbox.isChecked();
                boolean hasPressureMeter = binding.pressureMeterCheckbox.isChecked();
                boolean hasHeartRateMonitor = binding.heartRateMonitorCheckbox.isChecked();

                // Получаем данные из полей ввода
                String speedometerText = binding.speedometerEditText.getText().toString();
                String pressureMeterText = binding.pressureMeterEditText.getText().toString();
                String heartRateMonitorText = binding.heartRateMonitorEditText.getText().toString();

                // Создаем объект BikeData с данными о выбранных датчиках
                BikeData bikeData = new BikeData();
                bikeData.setSpeedometer(hasSpeedometer, speedometerText);
                bikeData.setPressureMeter(hasPressureMeter, pressureMeterText);
                bikeData.setHeartRateMonitor(hasHeartRateMonitor, heartRateMonitorText);

                // Получаем переданный из предыдущей активности выбранный радиус колеса
                Intent intent = getIntent();
                int selectedRadius = intent.getIntExtra("selectedRadius", 20); // Значение по умолчанию, если данные не были переданы

                bikeData.setWheelRadius(selectedRadius);

                // Преобразуем BikeData в JSON строку с помощью Gson
                Gson gson = new Gson();
                String jsonData = gson.toJson(bikeData);

                // Сохраняем JSON строку в файл
                sensorSelectionViewModel.saveJsonToCache(getString(R.string.json_path), jsonData, SensorSelectionActivity.this);

                sensorSelectionViewModel.navigateToNextScreen(SensorSelectionActivity.this);
            }
        });
    }

}