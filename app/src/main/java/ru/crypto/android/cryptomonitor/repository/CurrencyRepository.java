package ru.crypto.android.cryptomonitor.repository;

import javax.inject.Inject;

import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;

@PerApplication
public class CurrencyRepository {

    private CurrencyApi currencyApi;

    @Inject
    public CurrencyRepository(CurrencyApi currencyApi) {
        this.currencyApi = currencyApi;
    }
}
