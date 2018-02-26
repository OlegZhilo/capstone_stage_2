package ru.crypto.android.cryptomonitor.repository;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.utils.TransformUtil;

@PerApplication
public class CurrencyRepository {

    private CurrencyApi currencyApi;

    @Inject
    public CurrencyRepository(CurrencyApi currencyApi) {
        this.currencyApi = currencyApi;
    }

    public Observable<List<Currency>> getCurrencies() {
        return currencyApi.getCurrencies()
                .map(TransformUtil::transformCollection);
    }

    public Observable<List<Currency>> getCurrency(String currencyId) {
        return currencyApi.getCurrency(currencyId)
                .map(TransformUtil::transformCollection);
    }
}
