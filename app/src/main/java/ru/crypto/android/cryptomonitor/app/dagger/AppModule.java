package ru.crypto.android.cryptomonitor.app.dagger;

import android.content.ContentResolver;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;

@Module
public class AppModule {

    @Provides
    @PerApplication
    ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }
}