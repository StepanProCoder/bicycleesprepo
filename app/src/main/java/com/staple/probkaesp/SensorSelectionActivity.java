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

        setupSubmitButton();
    }

    private void setupSubmitButton() {
        binding.submitButton.setOnClickListener(view -> {
            sensorSelectionViewModel.saveBikeDataToJson(
                    new File(getCacheDir(), getString(R.string.json_path)),
                    binding.speedometerCheckbox.isChecked(),
                    binding.speedometerEditText.getText().toString(),
                    binding.pressureMeterCheckbox.isChecked(),
                    binding.pressureMeterEditText.getText().toString(),
                    binding.heartRateMonitorCheckbox.isChecked(),
                    binding.heartRateMonitorEditText.getText().toString(),
                    getIntent().getIntExtra("selectedRadius", 20)
            );

            ActivityUtils.startNewActivityAndFinishCurrent(this, MainActivity.class);
        });
    }

}