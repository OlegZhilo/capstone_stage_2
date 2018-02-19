package ru.crypto.android.cryptomonitor.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.crypto.android.cryptomonitor.R;

public class MainActivity extends AppCompatActivity implements LifecycleOwner{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
