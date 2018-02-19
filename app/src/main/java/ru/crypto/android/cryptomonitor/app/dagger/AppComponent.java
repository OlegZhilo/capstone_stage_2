package ru.crypto.android.cryptomonitor.app.dagger;

import dagger.Component;
import ru.crypto.android.cryptomonitor.app.dagger.scope.PerApplication;
import ru.crypto.android.cryptomonitor.app.scheduler.SchedulerModule;
import ru.crypto.android.cryptomonitor.repository.common.network.NetworkModule;

@PerApplication
@Component(modules = {
        SchedulerModule.class,
        NetworkModule.class
})
public interface AppComponent {
}
