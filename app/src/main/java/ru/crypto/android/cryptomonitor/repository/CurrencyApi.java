package ru.crypto.android.cryptomonitor.repository;


import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.crypto.android.cryptomonitor.repository.responses.CurrencyResponse;

import static ru.crypto.android.cryptomonitor.repository.common.network.ServerUrls.CURRENCIES_URL;

public interface CurrencyApi {

    @GET(CURRENCIES_URL)
    Observable<CurrencyResponse> getCurrencies();
}
