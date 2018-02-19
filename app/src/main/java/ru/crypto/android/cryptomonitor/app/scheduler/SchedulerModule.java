package ru.crypto.android.cryptomonitor.app.scheduler;


import dagger.Module;
import dagger.Provides;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;

@Module
public class SchedulerModule {

    @Provides
    @PerApplication
    public SchedulersProvider provideSchedulerProvider(){
        return new SchedulersProviderImpl();
    }
}
