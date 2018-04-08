package ru.crypto.android.cryptomonitor.ui.addcurrency;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.annimon.stream.Stream;

import java.util.List;

import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import timber.log.Timber;

public class AddCurrencyViewModel extends BaseViewModel {

    private final CurrencyRepository repository;
    private MutableLiveData<List<Currency>> currencyLiveData;

    public AddCurrencyViewModel(CurrencyRepository repository, SchedulersProvider schedulersProvider) {
        super(schedulersProvider);
        this.repository = repository;
        this.currencyLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Currency>> getCurrencyLiveData() {
        return currencyLiveData;
    }

    public void loadCurrencies() {
        subscribeIoHandleError(repository.getRemoteCurrenciesWithCache(),
                list -> currencyLiveData.postValue(list),
                Timber::e);
    }

    public void onFavoriteClick(Currency clickedCurrency) {
        long actionResult = clickedCurrency.isFavorite()
                ? repository.deleteCurrency(clickedCurrency)
                : repository.saveCurrency(clickedCurrency);

        if (actionResult > 0) {
            List<Currency> currencyList = currencyLiveData.getValue();
            if (currencyList != null) {
                List<Currency> newCurrencyList;
                newCurrencyList = Stream.of(currencyList)
                        .map(currency -> {
                            if (currency.getId().equals(clickedCurrency.getId())) {
                                currency.setFavorite(!clickedCurrency.isFavorite());
                            }
                            return currency;
                        }).toList();
                currencyLiveData.setValue(newCurrencyList);
            }
        }
    }
}