package ru.crypto.android.cryptomonitor.ui.view;

import android.support.annotation.NonNull;

import com.db.chart.model.LineSet;
import com.db.chart.view.ChartView;

import java.util.List;

public abstract class ChartAdapter<D, T extends ChartView> {

    protected T chartView;

    public final void attachTo(T chartView) {
        this.chartView = chartView;
    }

    public abstract void setData(List<D> data);

    private void setData(@NonNull String[] labels, @NonNull float[] values) {
        throw new IllegalStateException("Method setData must be override");
    }
}
