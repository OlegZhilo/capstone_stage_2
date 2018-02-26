package ru.crypto.android.cryptomonitor.repository;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.crypto.android.cryptomonitor.repository.responses.CurrencyResponse;

import static ru.crypto.android.cryptomonitor.repository.common.network.ServerUrls.CURRENCIES_URL;
import static ru.crypto.android.cryptomonitor.repository.common.network.ServerUrls.CURRENCY_URL;

public interface CurrencyApi {

    @GET(CURRENCIES_URL)
    Observable<List<CurrencyResponse>> getCurrencies();

    @GET(CURRENCY_URL)
    Observable<List<CurrencyResponse>> getCurrency(@Path("id")String id);
}
