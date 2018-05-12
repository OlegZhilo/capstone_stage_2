package ru.crypto.android.cryptomonitor.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceObserver;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import timber.log.Timber;

public class WidgetUpdateService extends IntentService {

    public static final String WIDGET_ACTION_UPDATE = WidgetUpdateService.class.getCanonicalName() + ":update.action";

    @Inject
    CurrencyRepository repository;

    @Inject
    SchedulersProvider schedulersProvider;

    public static void start(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(WIDGET_ACTION_UPDATE);
        context.startService(intent);
    }

    public WidgetUpdateService() {
        super(WidgetUpdateService.class.getCanonicalName());
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (WIDGET_ACTION_UPDATE.equals(action)) {
                handleActionUpdateWidgets();
            }
        }
    }

    private void handleActionUpdateWidgets() {
        subscribeIoHandleError(Observable.combineLatest(
                repository.getCacheCurrenciesWithRemotes(),
                repository.getCurrencyForNotificationAsync(), Pair::create),
                pair -> {
                    Currency findCurrency = Stream.of(pair.first)
                            .filter(currency -> currency.getId().equals(pair.second))
                            .findFirst()
                            .orElse(new Currency());
                    updateWidget(findCurrency);
                },
                Timber::e);
    }

    private void updateWidget(Currency currency) {
         AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetProvider.class));
        WidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds, currency);
    }

    protected <T> Disposable subscribeIoHandleError(Observable<T> observable,
                                                    final Consumer<T> onNext,
                                                    final Consumer<Throwable> onError) {
        observable = observable.subscribeOn(schedulersProvider.worker());
        return subscribe(observable, onNext, onError);
    }

    protected <T> Disposable subscribe(final Observable<T> observable,
                                       final Consumer<T> onNext,
                                       final Consumer<Throwable> onError) {
        return subscribe(observable,
                new ResourceObserver<T>() {

                    @Override
                    public void onError(Throwable e) {
                        try {
                            onError.accept(e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {
                        // do nothing
                    }

                    @Override
                    public void onNext(T t) {
                        try {
                            onNext.accept(t);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    protected <T> Disposable subscribe(final Observable<T> observable,
                                       final ResourceObserver<T> resourceObserver) {
        return observable.subscribeWith(resourceObserver);
    }
}