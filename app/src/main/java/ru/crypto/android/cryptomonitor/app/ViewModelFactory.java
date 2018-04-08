package ru.crypto.android.cryptomonitor.app;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.ui.addcurrency.AddCurrencyViewModel;
import ru.crypto.android.cryptomonitor.ui.list.FavoriteCurrencyViewModel;
import ru.crypto.android.cryptomonitor.ui.main.MainViewModel;

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
            return (T) new MainViewModel(schedulersProvider);
        } else if (modelClass.isAssignableFrom(AddCurrencyViewModel.class)) {
            return (T) new AddCurrencyViewModel(repository, schedulersProvider);
        } else if (modelClass.isAssignableFrom(FavoriteCurrencyViewModel.class)) {
            return (T) new FavoriteCurrencyViewModel(repository, schedulersProvider);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}