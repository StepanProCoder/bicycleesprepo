package com.staple.probkaesp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.staple.probkaesp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up Data Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel using ViewModelProvider
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Observe changes in LiveData and update UI
        mainViewModel.getStatusTextLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String statusText) {
                binding.statusTextView.setText(statusText);
            }
        });

        // Set Click Listener for the statusButton using Data Binding
        binding.configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.onConfigButtonClick(MainActivity.this);
            }
        });

        binding.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.onRefreshButtonClick();
            }
        });

        mainViewModel.onInit(() -> { Log.d("BACK TO HANDSHAKE", "TO HANDSHAKE"); mainViewModel.deleteFile(); ActivityUtils.startNewActivityAndFinishCurrent(this, HandshakeActivity.class); }, this);
    }
}
