
package ru.crypto.android.cryptomonitor.domain;

import java.util.Date;

public class ChartData {

    private Double price;
    private Date date;

    public ChartData(Double price, Long date) {
        this.price = price;
        this.date = new Date(date * 1000);
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
