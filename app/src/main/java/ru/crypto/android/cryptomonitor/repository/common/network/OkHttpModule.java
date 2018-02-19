package ru.crypto.android.cryptomonitor.repository.common.network;


import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;

/**
 * этот модуль вынесен отдельно для возможности замены его при интеграционном тестировании
 */
@Module
public class OkHttpModule {
    private static final int NETWORK_TIMEOUT = 10; //sec

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS);

        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        return okHttpClientBuilder.build();
    }
}
