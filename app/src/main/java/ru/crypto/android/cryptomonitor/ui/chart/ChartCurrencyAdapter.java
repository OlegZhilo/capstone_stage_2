package ru.crypto.android.cryptomonitor.ui.chart;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

import com.annimon.stream.Collectors;
import com.annimon.stream.ComparatorCompat;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.db.chart.animation.Animation;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.internal.functions.Functions;
import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.repository.Period;
import ru.crypto.android.cryptomonitor.ui.view.ChartAdapter;

public class ChartCurrencyAdapter extends ChartAdapter<ChartData, LineChartView> {

    private SimpleDateFormat simpleDayformat = new SimpleDateFormat("dd E", Locale.US);
    private SimpleDateFormat simpleHourformat = new SimpleDateFormat("h a", Locale.US);
    private SimpleDateFormat simpleMonthformat = new SimpleDateFormat("MMM", Locale.US);

    private Period period;
    private Paint thresPaint;

    public ChartCurrencyAdapter() {
        thresPaint = new Paint();
        thresPaint.setColor(Color.parseColor("#D6D7D5"));
        thresPaint.setStyle(Paint.Style.STROKE);
        thresPaint.setAntiAlias(true);
        thresPaint.setStrokeWidth(Tools.fromDpToPx(.5f));
    }

    public void setData(Period period, List<ChartData> data) {
        this.period = period;
        setData(data);
    }

    @Override
    public void setData(List<ChartData> data) {

        List<ChartData> reduceData = getReduceData(data);

        String[] mLabels = getLables(reduceData);
        float[] mValues = getValues(reduceData);
        double maxPrice = getMaxPrice(reduceData);

        LineSet dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setGradientFill(new int[]{Color.parseColor("#49CAD3F2"), Color.parseColor("#ffffff")}, new float[]{0f, 1f})
                .setSmooth(true)
                .setThickness(2)
                .beginAt(0);

        ArrayList<ChartSet> set = new ArrayList<>();
        set.add(dataset);
        chartView.reset();
        chartView.addData(set);

        int step = (int) (maxPrice / 5);

        if (step == 0) {
            step = 1;
        }

        showAfterDismiss((float) maxPrice, step);
    }

    private void showAfterDismiss(float maxPrice, int step) {
        chartView.setAxisBorderValues(0, maxPrice)
                .setAxisLabelsSpacing((int) Tools.fromDpToPx(8f))
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setGrid(4, 0, thresPaint)
                .setStep(step)
                .show(new Animation()
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(350)
                        .fromAlpha(0));
    }

    private List<ChartData> getReduceData(List<ChartData> data) {
        switch (period) {
            case DAY:
                return Stream.of(data)
                        .sample(3)
                        .toList();
            case MONTH:
                return Stream.of(data)
                        .sample(6)
                        .toList();
            case YEAR:
                return Stream.of(data)
                        .groupBy(chartData -> getSimpleFormat().format(chartData.getDate()))
                        .map(entity -> Stream.of(entity.getValue())
                                .max((o1, o2) -> new BigDecimal(o1.getPrice()).compareTo(new BigDecimal(o2.getPrice())))
                                .get())
                        .sortBy(ChartData::getDate)
                        .toList();

            case WEEK:
            default:
                return data;
        }
    }

    private String[] getLables(List<ChartData> data) {
        return Stream.of(data)
                .map(value -> getSimpleFormat().format(value.getDate()))
                .toList()
                .toArray(new String[]{});
    }

    private SimpleDateFormat getSimpleFormat() {
        switch (period) {
            case DAY:
                return simpleHourformat;
            case WEEK:
            case MONTH:
                return simpleDayformat;
            case YEAR:
                return simpleMonthformat;
            default:
                return simpleDayformat;
        }
    }

    private double getMaxPrice(List<ChartData> data) {
        return Stream.of(data)
                .map(ChartData::getPrice)
                .max(Functions.naturalOrder())
                .get();
    }

    private float[] getValues(List<ChartData> data) {
        float[] values = new float[data.size()];
        Stream.of(data)
                .map(ChartData::getPrice)
                .map(d -> BigDecimal.valueOf(d).floatValue())
                .forEachIndexed((index, aFloat) -> values[index] = aFloat);
        return values;
    }

}
