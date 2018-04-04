package ru.crypto.android.cryptomonitor.domain;


import java.util.Objects;

public class Currency {

    private static final String EMPTY_STR = "";

    private String id = EMPTY_STR;
    private String name = EMPTY_STR;
    private String volume24hUsd = EMPTY_STR;
    private String percentChange1H = EMPTY_STR;
    private String percentChange24H = EMPTY_STR;
    private String percentChange7D = EMPTY_STR;
    private String priceUsd = EMPTY_STR;
    private String rank = EMPTY_STR;
    private String symbol = EMPTY_STR;
    private boolean isFavorite;

    public Currency() {
        //stub constructor
    }

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
        this.isFavorite = false;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", volume24hUsd='" + volume24hUsd + '\'' +
                ", percentChange1H='" + percentChange1H + '\'' +
                ", percentChange24H='" + percentChange24H + '\'' +
                ", percentChange7D='" + percentChange7D + '\'' +
                ", priceUsd='" + priceUsd + '\'' +
                ", rank='" + rank + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return isFavorite == currency.isFavorite &&
                Objects.equals(id, currency.id) &&
                Objects.equals(name, currency.name) &&
                Objects.equals(volume24hUsd, currency.volume24hUsd) &&
                Objects.equals(percentChange1H, currency.percentChange1H) &&
                Objects.equals(percentChange24H, currency.percentChange24H) &&
                Objects.equals(percentChange7D, currency.percentChange7D) &&
                Objects.equals(priceUsd, currency.priceUsd) &&
                Objects.equals(rank, currency.rank) &&
                Objects.equals(symbol, currency.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, volume24hUsd, percentChange1H, percentChange24H, percentChange7D, priceUsd, rank, symbol, isFavorite);
    }
}