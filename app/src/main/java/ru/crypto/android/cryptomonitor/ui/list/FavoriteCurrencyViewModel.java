package ru.crypto.android.cryptomonitor.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Pair;

import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.Observable;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.notification.NotificationUtils;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.repository.Period;
import ru.crypto.android.cryptomonitor.repository.utils.SettingsUtil;
import ru.crypto.android.cryptomonitor.services.JobUtil;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import ru.crypto.android.cryptomonitor.ui.settings.SettingsActivity;
import ru.crypto.android.cryptomonitor.ui.widget.WidgetUpdateService;
import timber.log.Timber;

import static ru.crypto.android.cryptomonitor.repository.CurrencyRepository.DEFAULT_PERIOD;
import static ru.crypto.android.cryptomonitor.repository.utils.SettingsUtil.EMPTY_INT_SETTING;


public class FavoriteCurrencyViewModel extends BaseViewModel {

    private final CurrencyRepository repository;
    private MutableLiveData<List<Currency>> currencyLiveData;
    private MutableLiveData<List<ChartData>> chartLiveData;

    public FavoriteCurrencyViewModel(CurrencyRepository repository, SchedulersProvider schedulersProvider) {
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
        subscribeIoHandleError(Observable.combineLatest(repository.getCacheCurrenciesWithRemotes(), repository.getCurrencyForNotificationAsync(), Pair::create),
                pair -> {
                    Stream.of(pair.first)
                            .forEach(currency -> {
                                if(currency.getId().equals(pair.second)) {
                                    currency.setNotifiable(true);
                                }
                            });
                    currencyLiveData.postValue(pair.first);
                },
                Timber::e);
    }

    public void setCurrency(List<Currency> list) {
        subscribeIoHandleError(repository.getCurrencyForNotificationAsync(),
                pair -> {
                    Stream.of(list)
                            .forEach(currency -> {
                                if(currency.getId().equals(pair)) {
                                    currency.setNotifiable(true);
                                }
                            });
                    currencyLiveData.postValue(list);
                },
                Timber::e);
    }

    public void load(String fromSym, Period period) {
        subscribeIoHandleError(repository.getCurrencyHistory(fromSym, period),
                chartData -> chartLiveData.postValue(chartData),
                Timber::e);
    }

    public void saveNotifyCurrency(Context context, Currency currency) {
        repository.saveCurrencyForNotification(currency);
        loadCurrencies();
        startNotification(context, currency);
        WidgetUpdateService.start(context);
    }

    private void startNotification(Context context, Currency currency) {
        NotificationUtils.notify(context, currency);
        int period = SettingsUtil.getInt(context, SettingsActivity.SYNC_PERIOD_KEY);
        if(period == EMPTY_INT_SETTING)
            period = DEFAULT_PERIOD;
        JobUtil.scheduleJob(context, period);
    }
}
