package com.staple.probkaesp.fetcher.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.staple.probkaesp.R;
import com.staple.probkaesp.databinding.ActivityMainBinding;
import com.staple.probkaesp.fetcher.presenter.FetcherPresenter;

public class FetcherActivity extends AppCompatActivity
{
    private FetcherPresenter presenter;

    private Button configButton;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configButton = findViewById(R.id.configButton);
        refreshButton = findViewById(R.id.refreshButton);

        presenter = new FetcherPresenter(this);

        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onConfigButtonClick();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRefreshButtonClick();
            }
        });
    }
}
