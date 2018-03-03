package ru.crypto.android.cryptomonitor.ui.list;


import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;


public class MainViewModel extends BaseViewModel {

    private CurrencyRepository repository;

    public MainViewModel(CurrencyRepository repository, SchedulersProvider schedulersProvider) {
        super(schedulersProvider);
        this.repository = repository;
    }

    public void loadCurrencies() {
        subscribeIoHandleError(repository.getCurrencies(),
                list -> {
                    int i = 0;
                },
                throwable -> {

                });
    }

}
