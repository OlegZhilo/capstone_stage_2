package ru.crypto.android.cryptomonitor.ui.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceObserver;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;

public class BaseViewModel extends ViewModel {

    private SchedulersProvider schedulersProvider;
    private MutableLiveData<Throwable> errorLiveData;

    public BaseViewModel(SchedulersProvider schedulersProvider) {
        this.schedulersProvider = schedulersProvider;
        errorLiveData = new MutableLiveData<>();
    }

    protected <T> Disposable subscribeIoHandleError(Observable<T> observable,
                                                    final Consumer<T> onNext,
                                                    final Consumer<Throwable> onError) {
        observable = observable.subscribeOn(schedulersProvider.worker());
        return subscribe(observable, onNext, e -> handleError(e, onError));
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

    public MutableLiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    private void handleError(Throwable e, Consumer<Throwable> onError) {
        //getView().handleError(e);
        errorLiveData.postValue(e);
        if (onError != null) {
            try {
                onError.accept(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
