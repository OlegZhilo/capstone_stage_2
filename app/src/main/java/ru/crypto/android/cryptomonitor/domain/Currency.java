package ru.crypto.android.cryptomonitor.domain;


public class Currency {

    private String id;
    private String name;
    private String volume24hUsd;
    private String percentChange1H;
    private String percentChange24H;
    private String percentChange7D;
    private String priceUsd;
    private String rank;
    private String symbol;

    public Currency(String id,
                    String name,
                    String volume24hUsd,
                    String percentChange1H,
                    String percentChange24H,
                    String percentChange7D,
                    String priceUsd,
                    String rank,
                    String symbol) {
        this.id = id;
        this.name = name;
        this.volume24hUsd = volume24hUsd;
        this.percentChange1H = percentChange1H;
        this.percentChange24H = percentChange24H;
        this.percentChange7D = percentChange7D;
        this.priceUsd = priceUsd;
        this.rank = rank;
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVolume24hUsd() {
        return volume24hUsd;
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

    public String getPriceUsd() {
        return priceUsd;
    }

    public String getRank() {
        return rank;
    }

    public String getSymbol() {
        return symbol;
    }
}