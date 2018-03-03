package ru.crypto.android.cryptomonitor.app;

import dagger.Module;
import dagger.Provides;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulersProvider;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;

@Module
public class ViewModelFactoryModule {

    @Provides
    ViewModelFactory provideViewModelFactory(CurrencyRepository repository, SchedulersProvider schedulersProvider) {
        return new ViewModelFactory(repository, schedulersProvider);
    }
}
