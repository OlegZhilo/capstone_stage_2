package ru.crypto.android.cryptomonitor.app.dagger;


import android.content.Context;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import ru.crypto.android.cryptomonitor.app.App;
import ru.crypto.android.cryptomonitor.app.BuilderModule;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulerModule;
import ru.crypto.android.cryptomonitor.repository.CurrencyModule;
import ru.crypto.android.cryptomonitor.repository.common.network.NetworkModule;
import ru.crypto.android.cryptomonitor.repository.common.network.OkHttpModule;
import ru.crypto.android.cryptomonitor.repository.common.network.ServerUrlModule;

@PerApplication
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        SchedulerModule.class,
        ServerUrlModule.class,
        NetworkModule.class,
        OkHttpModule.class,
        CurrencyModule.class,
        BuilderModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        AppComponent build();
    }
    void inject(App app);
}
