package ru.crypto.android.cryptomonitor.repository.common.network;


import dagger.Module;
import dagger.Provides;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;

/**
 * Модуль предоставления url для продакшена
 */
@Module
public class ServerUrlModule {

    @Provides
    @PerApplication
    String provideBaseApiUrl() {
        return ServerUrls.CRYPTO_API_URL;
    }
}
