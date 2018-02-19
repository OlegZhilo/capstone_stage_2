package ru.crypto.android.cryptomonitor.app;

import android.app.Application;

import ru.crypto.android.cryptomonitor.app.dagger.AppComponent;
import ru.crypto.android.cryptomonitor.app.dagger.DaggerAppComponent;

public class App extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDi();
    }

    private void initDi() {
        appComponent = DaggerAppComponent.builder()
                .build();
    }
}
