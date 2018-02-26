package ru.crypto.android.cryptomonitor.domain;


public class Currency {

    private String id;
    private String name;
    private String lastUpdated;
    private String volume24hUsd;
    private String availableSupply;
    private String marketCapUsd;
    private String percentChange1H;
    private String percentChange24H;
    private String percentChange7D;
    private String priceBtc;
    private String priceUsd;
    private String rank;
    private String symbol;
    private String totalSupply;


    public Currency(String id,
                    String name,
                    String lastUpdated,
                    String volume24hUsd,
                    String availableSupply,
                    String marketCapUsd,
                    String percentChange1H,
                    String percentChange24H,
                    String percentChange7D,
                    String priceBtc,
                    String priceUsd,
                    String rank,
                    String symbol,
                    String totalSupply) {
        this.id = id;
        this.name = name;
        this.lastUpdated = lastUpdated;
        this.volume24hUsd = volume24hUsd;
        this.availableSupply = availableSupply;
        this.marketCapUsd = marketCapUsd;
        this.percentChange1H = percentChange1H;
        this.percentChange24H = percentChange24H;
        this.percentChange7D = percentChange7D;
        this.priceBtc = priceBtc;
        this.priceUsd = priceUsd;
        this.rank = rank;
        this.symbol = symbol;
        this.totalSupply = totalSupply;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getVolume24hUsd() {
        return volume24hUsd;
    }

    public String getAvailableSupply() {
        return availableSupply;
    }

    public String getMarketCapUsd() {
        return marketCapUsd;
    }

    public String getPercentChange1H() {
        return percentChange1H;
    }

    public String getPercentChange24H() {
        return percentChange24H;
    }

    public String getPercentChange7D() {
        return percentChange7D;
    }

    public String getPriceBtc() {
        return priceBtc;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public String getRank() {
        return rank;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getTotalSupply() {
        return totalSupply;
    }
}