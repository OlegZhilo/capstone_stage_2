package ru.crypto.android.cryptomonitor.repository.responses;

import com.google.gson.annotations.SerializedName;

import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.repository.common.network.Transformable;

public class ChartDataObj implements Transformable<ChartData> {

    @SerializedName("high")
    private Double price;
    @SerializedName("time")
    private Long date;

    @Override
    public ChartData transform() {
        return new ChartData(price, date);
    }
}
