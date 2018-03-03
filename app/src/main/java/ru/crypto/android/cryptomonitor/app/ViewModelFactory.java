package ru.crypto.android.cryptomonitor.app;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.ui.list.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private CurrencyRepository repository;
    private SchedulersProvider schedulersProvider;

    public ViewModelFactory(CurrencyRepository repository, SchedulersProvider schedulersProvider) {
        this.repository = repository;
        this.schedulersProvider = schedulersProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository, schedulersProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
