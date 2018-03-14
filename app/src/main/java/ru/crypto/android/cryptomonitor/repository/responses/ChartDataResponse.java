package ru.crypto.android.cryptomonitor.repository.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChartDataResponse {

    @SerializedName("Data")
    public List<ChartDataObj> chartValues;
}
