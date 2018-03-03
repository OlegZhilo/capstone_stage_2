package ru.crypto.android.cryptomonitor.app;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.crypto.android.cryptomonitor.ui.list.FavoriteCurrencyFragment;
import ru.crypto.android.cryptomonitor.ui.list.MainActivity;

@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
    abstract FavoriteCurrencyFragment bindFavoriteFragment();
}
