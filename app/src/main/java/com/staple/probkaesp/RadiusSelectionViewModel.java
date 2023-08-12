package com.staple.probkaesp;

import android.content.Intent;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class RadiusSelectionViewModel extends ViewModel {

    private MutableLiveData<Integer> selectedRadius = new MutableLiveData<>();
    private MutableLiveData<Boolean> startAnimation = new MutableLiveData<>();

    public void setSelectedRadius(int radius) {
        selectedRadius.setValue(radius);
    }

    public void startLottieAnimation() {
        startAnimation.setValue(true);
    }

    public ArrayAdapter<String> initRadiusSpinner(RadiusSelectionActivity activity) {
        List<String> radiusValues = new ArrayList<>();
        for (int i = 20; i <= 40; i++) {
            radiusValues.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, radiusValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    public void navigateToNextScreen(int selectedRadius, RadiusSelectionActivity activity) {
        Intent intent = new Intent(activity, SensorSelectionActivity.class);
        intent.putExtra("selectedRadius", selectedRadius);
        activity.startActivity(intent);
    }

}
