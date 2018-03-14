package ru.crypto.android.cryptomonitor.repository.common.network;

public class ServerUrls {

    public static final String CRYPTO_API_URL = "https://api.coinmarketcap.com/v1/";
    private static final String CURRENCY_HISTORY = "https://min-api.cryptocompare.com/data/";

    public static final String CURRENCIES_URL = CRYPTO_API_URL + "ticker/";
    public static final String CURRENCY_URL = CURRENCIES_URL + "{id}/";

    public static final String CURRENCY_HISTORY_DAYS = CURRENCY_HISTORY + "histoday";
    public static final String CURRENCY_HISTORY_HOURS = CURRENCY_HISTORY + "histohour";
}
