package ru.crypto.android.cryptomonitor.repository;


import android.support.annotation.IntRange;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.crypto.android.cryptomonitor.repository.responses.ChartDataResponse;
import ru.crypto.android.cryptomonitor.repository.responses.CurrencyResponse;

import static ru.crypto.android.cryptomonitor.repository.common.network.ServerUrls.CURRENCIES_URL;
import static ru.crypto.android.cryptomonitor.repository.common.network.ServerUrls.CURRENCY_HISTORY_DAYS;
import static ru.crypto.android.cryptomonitor.repository.common.network.ServerUrls.CURRENCY_HISTORY_HOURS;
import static ru.crypto.android.cryptomonitor.repository.common.network.ServerUrls.CURRENCY_URL;

public interface CurrencyApi {

    @GET(CURRENCIES_URL)
    Observable<List<CurrencyResponse>> getCurrencies();

    @GET(CURRENCY_URL)
    Observable<List<CurrencyResponse>> getCurrency(@Path("id") String id);

    @GET(CURRENCY_HISTORY_DAYS)
    Observable<ChartDataResponse> getHistoryDays(
            @Query("fsym") String fromSym,
            @Query("tsym") String toSym,
            @Query("limit") @IntRange(from = 1, to = 365) int limit);

    @GET(CURRENCY_HISTORY_HOURS)
    Observable<ChartDataResponse> getHistoryHours(
            @Query("fsym") String fromSym,
            @Query("tsym") String toSym,
            @Query("limit") @IntRange(from = 1, to = 24) int limit);
}
