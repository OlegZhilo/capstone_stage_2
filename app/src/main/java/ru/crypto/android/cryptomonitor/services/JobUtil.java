package ru.crypto.android.cryptomonitor.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import ru.crypto.android.cryptomonitor.repository.utils.SettingsUtil;
import ru.crypto.android.cryptomonitor.ui.settings.SettingsActivity;

import static android.app.job.JobInfo.NETWORK_TYPE_ANY;
import static ru.crypto.android.cryptomonitor.repository.CurrencyRepository.DEFAULT_PERIOD;
import static ru.crypto.android.cryptomonitor.repository.utils.SettingsUtil.EMPTY_INT_SETTING;

public class JobUtil {

    private static final int JOB_ID = 72496;

    public static void scheduleJob(Context context, int period) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName jobService =  new ComponentName(context.getPackageName(), UpdateJobService.class.getName());
        JobInfo jobInfo =  new JobInfo.Builder(JOB_ID, jobService)
                .setRequiredNetworkType(NETWORK_TYPE_ANY)
                .setPeriodic(period * 60 * 1000)
                .build();
        jobScheduler.schedule(jobInfo);
    }
}
