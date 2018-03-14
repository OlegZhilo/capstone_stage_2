package ru.crypto.android.cryptomonitor.ui.main;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.repository.Period;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import timber.log.Timber;


public class MainViewModel extends BaseViewModel {

    private CurrencyRepository repository;
    private MutableLiveData<List<Currency>> currencyLiveData;
    private MutableLiveData<List<ChartData>> chartLiveData;

    public MainViewModel(CurrencyRepository repository, SchedulersProvider schedulersProvider) {
        super(schedulersProvider);
        this.repository = repository;
        this.currencyLiveData = new MutableLiveData<>();
        this.chartLiveData = new MutableLiveData<>();
    }

    public LiveData<List<ChartData>> getChartLiveData() {
        return chartLiveData;
    }

    public LiveData<List<Currency>> getCurrencyLiveData() {
        return currencyLiveData;
    }


    public void loadCurrencies() {
        subscribeIoHandleError(repository.getCurrencies(),
                list -> currencyLiveData.postValue(list),
                Timber::e);
    }

    public void load(String fromSym, Period period) {
        subscribeIoHandleError(repository.getHistory(fromSym, period),
                chartData -> chartLiveData.postValue(chartData),
                Timber::e);
    }

}
