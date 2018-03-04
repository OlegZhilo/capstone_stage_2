package ru.crypto.android.cryptomonitor.ui.list;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import timber.log.Timber;


public class MainViewModel extends BaseViewModel {

    private CurrencyRepository repository;
    private MutableLiveData<List<Currency>> currencyLiveData;

    public MainViewModel(CurrencyRepository repository, SchedulersProvider schedulersProvider) {
        super(schedulersProvider);
        this.repository = repository;
        this.currencyLiveData = new MutableLiveData<>();
    }

    LiveData<List<Currency>> getCurrencyLiveData() {
        return currencyLiveData;
    }

    void loadCurrencies() {
        subscribeIoHandleError(repository.getCurrencies(),
                list -> currencyLiveData.postValue(list),
                Timber::e);
    }

}
