package com.staple.probkaesp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.staple.probkaesp.databinding.ActivitySensorSelectionBinding;
import com.staple.probkaesp.fetcher.view.FetcherActivity;
import com.staple.probkaesp.utils.activity.ActivityUtils;

import java.io.File;

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

            ActivityUtils.startNewActivity(this, FetcherActivity.class);
        });
    }

}