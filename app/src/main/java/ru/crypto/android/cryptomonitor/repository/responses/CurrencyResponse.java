
package ru.crypto.android.cryptomonitor.repository.responses;

import com.google.gson.annotations.SerializedName;

import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.common.network.Transformable;

public class CurrencyResponse implements Transformable<Currency> {

    @SerializedName("24h_volume_usd")
    private String volume24hUsd;
    @SerializedName("available_supply")
    private String availableSupply;
    @SerializedName("id")
    private String id;
    @SerializedName("last_updated")
    private String lastUpdated;
    @SerializedName("market_cap_usd")
    private String marketCapUsd;
    @SerializedName("name")
    private String name;
    @SerializedName("percent_change_1h")
    private String percentChange1H;
    @SerializedName("percent_change_24h")
    private String percentChange24H;
    @SerializedName("percent_change_7d")
    private String percentChange7D;
    @SerializedName("price_btc")
    private String priceBtc;
    @SerializedName("price_usd")
    private String priceUsd;
    @SerializedName("rank")
    private String rank;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("total_supply")
    private String totalSupply;

    @Override
    public Currency transform() {
        return new Currency(id,
                name,
                lastUpdated,
                volume24hUsd,
                availableSupply,
                marketCapUsd,
                percentChange1H,
                percentChange24H,
                percentChange7D,
                priceBtc,
                priceUsd,
                rank,
                symbol,
                totalSupply);
    }
}