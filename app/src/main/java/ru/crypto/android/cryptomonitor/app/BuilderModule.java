package ru.crypto.android.cryptomonitor.app;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulerModule;
import ru.crypto.android.cryptomonitor.services.UpdateJobService;
import ru.crypto.android.cryptomonitor.ui.addcurrency.AddCurrencyActivity;
import ru.crypto.android.cryptomonitor.ui.chart.ChartCurrencyFragment;
import ru.crypto.android.cryptomonitor.ui.list.FavoriteCurrencyFragment;
import ru.crypto.android.cryptomonitor.ui.main.MainActivity;
import ru.crypto.android.cryptomonitor.ui.widget.WidgetUpdateService;

@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
    abstract AddCurrencyActivity bindAddCurrencyActivity();

    @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
    abstract FavoriteCurrencyFragment bindFavoriteFragment();

    @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
    abstract ChartCurrencyFragment bindChartFragment();

    @ContributesAndroidInjector(modules = SchedulerModule.class)
    abstract UpdateJobService service();

    @ContributesAndroidInjector(modules = SchedulerModule.class)
    abstract WidgetUpdateService widgetUpdateService();
}