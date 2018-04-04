package ru.crypto.android.cryptomonitor.repository;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.provider.currency.CurrencyContentValues;
import ru.crypto.android.cryptomonitor.provider.currency.CurrencyContract;
import ru.crypto.android.cryptomonitor.provider.currency.CurrencyCursor;
import ru.crypto.android.cryptomonitor.provider.currency.CurrencySelection;
import ru.crypto.android.cryptomonitor.repository.utils.TransformUtil;

@PerApplication
public class CurrencyRepository {

    private static final String TO_SYM = "USD";
    private CurrencyApi currencyApi;
    private ContentResolver contentResolver;

    @Inject
    public CurrencyRepository(CurrencyApi currencyApi, ContentResolver contentResolver) {
        this.currencyApi = currencyApi;
        this.contentResolver = contentResolver;
    }

    public Observable<List<Currency>> getCurrencies() {
        return currencyApi.getCurrencies()
                .map(TransformUtil::transformCollection);
    }

    public Observable<List<Currency>> getCurrency(String currencyId) {
        return currencyApi.getCurrency(currencyId)
                .map(TransformUtil::transformCollection);
    }

    public Observable<List<ChartData>> getCurrencyHistory(String fromSym, Period period) {
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

    public long saveCurrency(Currency currency) {
        return ContentUris.parseId(getCurrencyContentValues(currency).insert(contentResolver));
    }

    public int saveCurrencies(List<Currency> currencies) {
        ContentValues[] currencyContentValues = Stream.of(currencies)
                .map(this::getCurrencyContentValues)
                .map(CurrencyContentValues::values)
                .toArray(ContentValues[]::new);
        return contentResolver.bulkInsert(CurrencyContract.CONTENT_URI, currencyContentValues);
    }

    public List<Currency> getCachedCurrencies() {
        CurrencySelection currencySelection = new CurrencySelection();
        CurrencyCursor currencyCursor = currencySelection.query(contentResolver);
        return currencyCursor.getCurrencies();
    }

    public Observable<List<Currency>> getMergedCurrencies() {
        return getCurrencies()
                .onErrorReturnItem(getCachedCurrencies())
                .flatMap(remoteCurrencies -> getAsyncCachedCurrencies(), (remoteCurrencies, localCurrencies) ->
                        Stream.of(remoteCurrencies)
                                .map(remoteCurrency -> {
                                    boolean isCached = Stream.of(localCurrencies)
                                            .anyMatch(localCurrency -> localCurrency.getId().equals(remoteCurrency.getId()));
                                    if (isCached) {
                                        remoteCurrency.setFavorite(true);
                                    }
                                    return remoteCurrency;
                                })
                                .toList());
    }

    public Observable<List<Currency>> getAsyncCachedCurrencies() {
        return Observable.fromCallable(this::getCachedCurrencies);
    }

    public long deleteCurrency(Currency currency) {
        CurrencySelection selection = new CurrencySelection();
        selection.id(currency.getId());
        return selection.delete(contentResolver);
    }

    public int clearCurrencyTable() {
        return new CurrencySelection().delete(contentResolver);
    }

    @NonNull
    private CurrencyContentValues getCurrencyContentValues(Currency currency) {
        CurrencyContentValues values = new CurrencyContentValues();
        values.putId(currency.getId());
        values.putName(currency.getName());
        values.putPercentchange1h(currency.getPercentChange1H());
        values.putPercentchange7d(currency.getPercentChange7D());
        values.putPercentchange24h(currency.getPercentChange24H());
        values.putRank(currency.getRank());
        values.putSymbol(currency.getSymbol());
        return values;
    }
}