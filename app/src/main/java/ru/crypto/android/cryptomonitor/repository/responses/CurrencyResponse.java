
package ru.crypto.android.cryptomonitor.repository.responses;

import com.google.gson.annotations.SerializedName;

public class CurrencyResponse {

    @SerializedName("24h_volume_usd")
    private String volume24hUsd;
    @SerializedName("available_supply")
    private String AvailableSupply;
    @SerializedName("id")
    private String Id;
    @SerializedName("last_updated")
    private String LastUpdated;
    @SerializedName("market_cap_usd")
    private String MarketCapUsd;
    @SerializedName("name")
    private String Name;
    @SerializedName("percent_change_1h")
    private String PercentChange1H;
    @SerializedName("percent_change_24h")
    private String PercentChange24H;
    @SerializedName("percent_change_7d")
    private String PercentChange7D;
    @SerializedName("price_btc")
    private String PriceBtc;
    @SerializedName("price_usd")
    private String PriceUsd;
    @SerializedName("rank")
    private String Rank;
    @SerializedName("symbol")
    private String Symbol;
    @SerializedName("total_supply")
    private String TotalSupply;

    public String getVolume24hUsd() {
        return  volume24hUsd;
    }

    public void set4hVolumeUsd(String volume24hUsd) {
        this.volume24hUsd = volume24hUsd;
    }

    public String getAvailableSupply() {
        return AvailableSupply;
    }

    public void setAvailableSupply(String availableSupply) {
        AvailableSupply = availableSupply;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        LastUpdated = lastUpdated;
    }

    public String getMarketCapUsd() {
        return MarketCapUsd;
    }

    public void setMarketCapUsd(String marketCapUsd) {
        MarketCapUsd = marketCapUsd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPercentChange1H() {
        return PercentChange1H;
    }

    public void setPercentChange1H(String percentChange1H) {
        PercentChange1H = percentChange1H;
    }

    public String getPercentChange24H() {
        return PercentChange24H;
    }

    public void setPercentChange24H(String percentChange24H) {
        PercentChange24H = percentChange24H;
    }

    public String getPercentChange7D() {
        return PercentChange7D;
    }

    public void setPercentChange7D(String percentChange7D) {
        PercentChange7D = percentChange7D;
    }

    public String getPriceBtc() {
        return PriceBtc;
    }

    public void setPriceBtc(String priceBtc) {
        PriceBtc = priceBtc;
    }

    public String getPriceUsd() {
        return PriceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        PriceUsd = priceUsd;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getTotalSupply() {
        return TotalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        TotalSupply = totalSupply;
    }

}
