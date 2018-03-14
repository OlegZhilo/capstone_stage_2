package ru.crypto.android.cryptomonitor.ui.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.db.chart.view.LineChartView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.Period;
import ru.crypto.android.cryptomonitor.ui.base.BaseFragment;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import ru.crypto.android.cryptomonitor.ui.chart.controller.ChartCurrencyController;
import ru.crypto.android.cryptomonitor.ui.main.MainViewModel;
import ru.crypto.android.cryptomonitor.ui.view.StartSnapHelper;
import ru.surfstudio.easyadapter.recycler.EasyAdapter;
import ru.surfstudio.easyadapter.recycler.ItemList;

public class ChartCurrencyFragment extends BaseFragment<MainViewModel> {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.chart)
    LineChartView chartView;

    @BindView(R.id.day_btn)
    Button dayBtn;

    @BindView(R.id.week_btn)
    Button weekBtn;

    @BindView(R.id.month_btn)
    Button monthBtn;

    @BindView(R.id.year_btn)
    Button yearBtn;

    private EasyAdapter adapter = new EasyAdapter();
    private ChartCurrencyAdapter chartCurrencyAdapter = new ChartCurrencyAdapter();
    private ChartCurrencyController chartCurrencyController = new ChartCurrencyController();

    private String currentSym = "ETH";
    private Period currentPeriod = Period.DAY;

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        chartCurrencyAdapter.attachTo(chartView);

        dayBtn.setOnClickListener(v -> {
            currentPeriod = Period.DAY;
            loadChart();
        });

        weekBtn.setOnClickListener(v -> {
            currentPeriod = Period.WEEK;
            loadChart();
        });

        monthBtn.setOnClickListener(v -> {
            currentPeriod = Period.MONTH;
            loadChart();
        });

        yearBtn.setOnClickListener(v -> {
            currentPeriod = Period.YEAR;
            loadChart();
        });
    }

    @Override
    protected void onStartVisibleView() {
        getViewModel().getCurrencyLiveData().observeForever(this::renderCurrencyList);
        getViewModel().getChartLiveData().observeForever(this::renderChart);
        getViewModel().loadCurrencies();
    }

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_currency_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void loadChart() {
        getViewModel().load(currentSym, currentPeriod);
    }

    private void renderCurrencyList(List<Currency> list) {
        adapter.setItems(ItemList.create().addAll(list, chartCurrencyController));
    }

    private void renderChart(List<ChartData> list) {
        chartCurrencyAdapter.setData(currentPeriod, list);
    }
}
