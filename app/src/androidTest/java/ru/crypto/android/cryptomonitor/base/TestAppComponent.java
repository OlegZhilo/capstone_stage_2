package ru.crypto.android.cryptomonitor.base;

import android.content.Context;

import dagger.BindsInstance;
import dagger.Component;
import ru.crypto.android.cryptomonitor.CurrencyTest;
import ru.crypto.android.cryptomonitor.app.App;
import ru.crypto.android.cryptomonitor.app.dagger.AppComponent;
import ru.crypto.android.cryptomonitor.app.dagger.AppModule;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulerModule;
import ru.crypto.android.cryptomonitor.repository.CurrencyModule;
import ru.crypto.android.cryptomonitor.repository.common.network.NetworkModule;
import ru.crypto.android.cryptomonitor.repository.common.network.OkHttpModule;
import ru.crypto.android.cryptomonitor.repository.common.network.ServerUrlModule;

@PerApplication
@Component(modules = {
        AppModule.class,
        SchedulerModule.class,
        ServerUrlModule.class,
        NetworkModule.class,
        OkHttpModule.class,
        CurrencyModule.class
})
public interface TestAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        TestAppComponent build();
    }
    void inject(CurrencyTest test);
}