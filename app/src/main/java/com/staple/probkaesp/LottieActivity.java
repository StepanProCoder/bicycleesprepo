package com.staple.probkaesp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.staple.probkaesp.databinding.ActivityLottieBinding;
import com.staple.probkaesp.databinding.ActivityMainBinding;

public class LottieActivity extends AppCompatActivity {

    private LottieViewModel lottieViewModel;
    private ActivityLottieBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up Data Binding
        binding = ActivityLottieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lottieViewModel = new ViewModelProvider(this).get(LottieViewModel.class);

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottieViewModel.onButtonClicked();
                binding.lottieStart.animate().translationX(2000).setDuration(2000).setStartDelay(0);
            }
        });

        lottieViewModel.getNavigateToNextScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean navigate) {
                if (navigate) {
                    lottieViewModel.navigateToNextScreen(LottieActivity.this);
                }
            }
        });
    }

}
