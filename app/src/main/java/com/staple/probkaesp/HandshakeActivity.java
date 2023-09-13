package com.staple.probkaesp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.staple.probkaesp.databinding.ActivityHandshakeBinding;
import com.staple.probkaesp.databinding.ActivitySensorSelectionBinding;

import java.io.File;

public class HandshakeActivity extends AppCompatActivity {

    private HandShakeViewModel handshakeViewModel;
    private ActivityHandshakeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up Data Binding
        binding = ActivityHandshakeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel using ViewModelProvider
        handshakeViewModel = new ViewModelProvider(this).get(HandShakeViewModel.class);

        handshakeViewModel.initializeHandshakeApi();

        setupSubmitButton();
    }

    private void setupSubmitButton() {
        binding.sendDataButton.setOnClickListener(view -> {
            handshakeViewModel.postHandshake(new HandshakeData(binding.ssidEditText.getText().toString(), binding.passwordEditText.getText().toString(), binding.idEditText.getText().toString()));
            ActivityUtils.startNewActivityAndFinishCurrent(this, LottieActivity.class);
        });
    }

}