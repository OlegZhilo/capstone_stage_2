package ru.crypto.android.cryptomonitor.services;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.text.TextUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceObserver;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.notification.NotificationUtils;

import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import timber.log.Timber;


public class UpdateJobService extends JobService {

    @Inject
    CurrencyRepository repository;

    @Inject
    SchedulersProvider schedulersProvider;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Timber.d("UpdateJobService onStartJob");

        String currencyId = repository.getCurrencyForNotification();

        if(!TextUtils.isEmpty(currencyId)) {
            subscribeIoHandleError(repository.getCurrency(currencyId),
                    list -> {
                        for (Currency currency : list) {
                            Timber.d(currency.toString());
                        }

                        if (!list.isEmpty()) {
                            NotificationUtils.notify(getApplicationContext(), list.get(0));
                        }
                    },
                    e -> {

                    });
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Timber.d("UpdateJobService onStopJob");
        return false;
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
