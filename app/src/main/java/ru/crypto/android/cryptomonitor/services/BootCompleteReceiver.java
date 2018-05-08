package ru.crypto.android.cryptomonitor.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.crypto.android.cryptomonitor.repository.utils.SettingsUtil;
import ru.crypto.android.cryptomonitor.ui.settings.SettingsActivity;
import timber.log.Timber;

import static ru.crypto.android.cryptomonitor.repository.CurrencyRepository.DEFAULT_PERIOD;
import static ru.crypto.android.cryptomonitor.repository.utils.SettingsUtil.EMPTY_INT_SETTING;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Timber.d("Boot Completed");
            int period = SettingsUtil.getInt(context, SettingsActivity.SYNC_PERIOD_KEY);
            if(period == EMPTY_INT_SETTING)
                period = DEFAULT_PERIOD;
            JobUtil.scheduleJob(context, period);
        }
    }
}