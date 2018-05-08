package ru.crypto.android.cryptomonitor.ui.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.notification.NotificationUtils;
import ru.crypto.android.cryptomonitor.repository.utils.SettingsUtil;
import ru.crypto.android.cryptomonitor.services.JobUtil;
import ru.crypto.android.cryptomonitor.ui.addcurrency.AddCurrencyActivity;
import ru.crypto.android.cryptomonitor.ui.base.BaseAсtivity;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;

import static ru.crypto.android.cryptomonitor.ui.settings.SettingsActivity.SYNC_PERIOD_KEY;

public class MainActivity extends BaseAсtivity<MainViewModel> implements SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.add_currency_fab)
    FloatingActionButton addCurrencyFab;
    @BindView(R.id.list_iv)
    ImageView currencyListBtn;
    @BindView(R.id.chart_iv)
    ImageView currencyChartBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void onError(Throwable throwable) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        showList();

        addCurrencyFab.setOnClickListener(v -> startActivity(new Intent(this, AddCurrencyActivity.class)));

        currencyListBtn.setOnClickListener(v -> showList());

        currencyChartBtn.setOnClickListener(v -> showCharts());

        registerSharedPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterSharedPreferences();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (SYNC_PERIOD_KEY.equals(key)) {
            JobUtil.scheduleJob(this, SettingsUtil.getInt(this, SYNC_PERIOD_KEY));
        }
    }

    private void unRegisterSharedPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void registerSharedPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    private void showList() {
        currencyListBtn.setSelected(true);
        currencyChartBtn.setSelected(false);
        scaleView(currencyListBtn, true);
        scaleView(currencyChartBtn, false);
        viewPager.setCurrentItem(0, true);
    }

    private void showCharts() {
        currencyListBtn.setSelected(false);
        currencyChartBtn.setSelected(true);
        scaleView(currencyListBtn, false);
        scaleView(currencyChartBtn, true);
        viewPager.setCurrentItem(1, true);
    }
    private void scaleView(View v, boolean up) {
        ViewCompat.animate(v)
                .scaleX(up ? 1.2F : 1F)
                .scaleY(up ? 1.2F : 1F)
                .setDuration(150)
                .start();
    }
}