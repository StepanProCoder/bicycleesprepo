package com.staple.probkaesp.handshaker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.staple.probkaesp.R;
import com.staple.probkaesp.datamodels.HandshakeData;
import com.staple.probkaesp.databinding.ActivityHandshakeBinding;
import com.staple.probkaesp.handshaker.presenter.HandshakePresenter;

public class HandshakeActivity extends AppCompatActivity
 {
    private HandshakePresenter presenter;
    private Button submitButton;
    private EditText ssidEditText, passwordEditText, idEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handshake);
        submitButton = findViewById(R.id.sendDataButton);
        ssidEditText = findViewById(R.id.ssidEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        idEditText = findViewById(R.id.idEditText);

        presenter = new HandshakePresenter(this);

        setupSubmitButton();
    }

    private void setupSubmitButton()
    {
        submitButton.setOnClickListener(view -> {
            presenter.onSubmitButtonDidClick(new HandshakeData(ssidEditText.getText().toString(), passwordEditText.getText().toString(), idEditText.getText().toString()));
        });
    }

}