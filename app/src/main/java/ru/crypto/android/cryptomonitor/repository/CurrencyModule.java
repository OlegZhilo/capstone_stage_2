package ru.crypto.android.cryptomonitor.repository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;

@Module
public class CurrencyModule {

    @PerApplication
    @Provides
    CurrencyApi provideCurrencyAPi(Retrofit retrofit) {
        return retrofit.create(CurrencyApi.class);
    }
}
