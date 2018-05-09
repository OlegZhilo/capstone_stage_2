package ru.crypto.android.cryptomonitor.app.dagger;

import android.content.ContentResolver;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import ru.crypto.android.cryptomonitor.services.analytic.GoogleAnalyticsApi;

@Module
public class AppModule {

    @Provides
    @PerApplication
    ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }

    @Provides
    @PerApplication
    GoogleAnalyticsApi provideGoogleAnalyticsApi(Context context) {
        return new GoogleAnalyticsApi(context);
    }
}