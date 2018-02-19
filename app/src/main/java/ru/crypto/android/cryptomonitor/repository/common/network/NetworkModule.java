package ru.crypto.android.cryptomonitor.repository.common.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.crypto.android.cryptomonitor.BuildConfig;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import timber.log.Timber;

@Module
public class NetworkModule {

    private static final String HTTP_LOG_TAG = "OkHttp";

    @Provides
    @PerApplication
    Retrofit provideRetrofit(OkHttpClient okHttpClient,
                             Gson gson,
                             String apiUrl) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @PerApplication
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.tag(HTTP_LOG_TAG).d(message));
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        return logging;
    }

}