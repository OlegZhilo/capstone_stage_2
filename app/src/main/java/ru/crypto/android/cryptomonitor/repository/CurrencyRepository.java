package ru.crypto.android.cryptomonitor.repository;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.utils.TransformUtil;

@PerApplication
public class CurrencyRepository {

    private static final String TO_SYM = "USD";
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

    public Observable<List<ChartData>> getHistory(String fromSym, Period period) {
        switch (period) {
            case DAY:
                return currencyApi.getHistoryHours(fromSym, TO_SYM, period.getCount())
                        .map(chartDataResponse -> chartDataResponse.chartValues)
                        .map(TransformUtil::transformCollection);
            case WEEK:
            case MONTH:
            case YEAR:
                return currencyApi.getHistoryDays(fromSym, TO_SYM, period.getCount())
                        .map(chartDataResponse -> chartDataResponse.chartValues)
                        .map(TransformUtil::transformCollection);
                default:
                    throw new IllegalArgumentException("Unsupported Period: " + period.name());
        }
    }
}
