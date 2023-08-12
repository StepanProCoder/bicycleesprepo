package com.staple.probkaesp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;

import com.staple.probkaesp.databinding.ActivityRadiusSelectionBinding;

import java.util.ArrayList;
import java.util.List;

public class RadiusSelectionActivity extends AppCompatActivity {

    private RadiusSelectionViewModel radiusSelectionViewModel;
    private ActivityRadiusSelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_radius_selection);

        radiusSelectionViewModel = new ViewModelProvider(this).get(RadiusSelectionViewModel.class);
        binding.setLifecycleOwner(this);

        binding.radiusSpinner.setAdapter(radiusSelectionViewModel.initRadiusSpinner(this));

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadius = Integer.parseInt(binding.radiusSpinner.getSelectedItem().toString());
                radiusSelectionViewModel.setSelectedRadius(selectedRadius);
                radiusSelectionViewModel.startLottieAnimation();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        radiusSelectionViewModel.navigateToNextScreen(selectedRadius, RadiusSelectionActivity.this);
                    }
                }, 1000);
            }
        });
    }

}
