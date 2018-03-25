package ru.crypto.android.cryptomonitor.app;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import ru.crypto.android.cryptomonitor.app.dagger.DaggerAppComponent;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initDi();
        initLogger();
    }

    private void initDi() {
        DaggerAppComponent.builder()
                .context(this)
                .build()
                .inject(this);
    }


    public static void initLogger(){
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
