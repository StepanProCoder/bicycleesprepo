package com.staple.probkaesp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView statusTextView;
    private Esp8266Api esp8266Api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NsdDiscovery nsdDiscovery = new NsdDiscovery(this);
        nsdDiscovery.startDiscovery();

        statusTextView = findViewById(R.id.statusTextView);

        // Обработка нажатия кнопки
        Button statusButton = findViewById(R.id.statusButton);
        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esp8266Api = nsdDiscovery.esp8266Api;
                getSwitchStatus();
            }
        });
    }

    private void getSwitchStatus() {
        Call<SwitchStatus> call = esp8266Api.getSwitchStatus();
        call.enqueue(new Callback<SwitchStatus>() {
            @Override
            public void onResponse(Call<SwitchStatus> call, Response<SwitchStatus> response) {
                if (response.isSuccessful()) {
                    SwitchStatus switchStatus = response.body();
                    Log.d("SUCC", response.message());
                    float speed = response.body().getSpeed();
                    // Обработка полученного статуса геркона
                    statusTextView.setText(speed + " km/h");

                } else {
                    Toast.makeText(MainActivity.this, "Ошибка при получении статуса", Toast.LENGTH_SHORT).show();
                    try {
                        Log.d("FAIL", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<SwitchStatus> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка при отправке запроса", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
