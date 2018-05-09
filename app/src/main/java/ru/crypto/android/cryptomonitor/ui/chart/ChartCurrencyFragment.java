package ru.crypto.android.cryptomonitor.ui.chart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import ru.crypto.android.cryptomonitor.ui.chart.controller.EmptyStateController;
import ru.crypto.android.cryptomonitor.ui.list.FavoriteCurrencyViewModel;
import ru.surfstudio.easyadapter.recycler.EasyAdapter;
import ru.surfstudio.easyadapter.recycler.ItemList;

import static ru.crypto.android.cryptomonitor.ui.view.Utils.getDrawableForCurrency;
import static ru.crypto.android.cryptomonitor.ui.view.Utils.getSpannedCurrencyString;

public class ChartCurrencyFragment extends BaseFragment<FavoriteCurrencyViewModel> {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.chart)
    LineChartView chartView;

    @BindView(R.id.empty_chart_state)
    TextView emptyChartStateView;

    @BindView(R.id.periods_btns)
    TabLayout btnTabs;

    @BindView(R.id.icon_iv)
    View currencyView;

    @BindView(R.id.coin_name_tv)
    TextView coinNameTv;

    private EasyAdapter adapter = new EasyAdapter();
    private ChartCurrencyAdapter chartCurrencyAdapter = new ChartCurrencyAdapter();
    private ChartCurrencyController chartCurrencyController = new ChartCurrencyController(this::onCurrencyClick);
    private EmptyStateController emptyStateController = new EmptyStateController();

    private String currentSym;
    private Period currentPeriod = Period.DAY;

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        chartCurrencyAdapter.attachTo(chartView);
        swipeRefreshLayout.setOnRefreshListener(getViewModel()::loadCurrencies);

        btnTabs.setTabsFromPagerAdapter(new Pager(getActivity()));
        btnTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        currentPeriod = Period.DAY;
                        break;
                    case 1:
                        currentPeriod = Period.WEEK;
                        break;
                    case 2:
                        currentPeriod = Period.MONTH;
                        break;
                    case 3:
                        currentPeriod = Period.YEAR;
                        break;
                }
                loadChart();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //empty
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //empty
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
            getViewModel().loadCurrencies();
        }
    }

    @Override
    protected void onStartVisibleView() {
        getViewModel().getCurrencyLiveData().observeForever(this::renderCurrencyList);
        getViewModel().getChartLiveData().observeForever(this::renderChart);
        getViewModel().loadCurrencies();
    }

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return FavoriteCurrencyViewModel.class;
    }

    @Override
    protected void onError(Throwable throwable) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_currency_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onCurrencyClick(Currency currency) {
        currencyView.setBackgroundResource(getDrawableForCurrency(getActivity(), currency));
        coinNameTv.setText(getSpannedCurrencyString(getActivity(), currency));
        currentSym = currency.getSymbol();
        loadChart();
    }

    private void loadChart() {
        getViewModel().load(currentSym, currentPeriod);
    }

    private void renderCurrencyList(List<Currency> list) {
        adapter.setItems(ItemList.create()
                .addIf(list.isEmpty(), emptyStateController)
                .addAll(list, chartCurrencyController));

        if (TextUtils.isEmpty(currentSym) && !list.isEmpty()) {
            onCurrencyClick(list.get(0));
        }

        swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 500);
    }

    private void renderChart(List<ChartData> list) {
        if (!list.isEmpty()) {
            chartCurrencyAdapter.setData(currentPeriod, list);
            //Objects.requireNonNull(btnTabs.getTabAt(0)).select();
        }

        emptyChartStateView.setVisibility(list.isEmpty() ? View.VISIBLE: View.INVISIBLE);

    }
}
