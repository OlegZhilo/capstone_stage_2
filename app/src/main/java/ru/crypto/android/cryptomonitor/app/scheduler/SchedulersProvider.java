package ru.crypto.android.cryptomonitor.app.scheduler;


import io.reactivex.Scheduler;

public interface SchedulersProvider {
    Scheduler main();

    Scheduler worker();
}
