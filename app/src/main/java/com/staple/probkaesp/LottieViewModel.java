package com.staple.probkaesp;

import android.content.Intent;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LottieViewModel extends ViewModel {

    private MutableLiveData<Boolean> navigateToNextScreen = new MutableLiveData<>();

    public LiveData<Boolean> getNavigateToNextScreen() {
        return navigateToNextScreen;
    }

    public void onButtonClicked() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNextScreen.setValue(true);
            }
        }, 1000);

    }

    public void navigateToNextScreen(LottieActivity activity) {
        Intent intent = new Intent(activity, RadiusSelectionActivity.class);
        activity.startActivity(intent);
    }

}
