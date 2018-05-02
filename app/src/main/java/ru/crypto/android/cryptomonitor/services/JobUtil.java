package ru.crypto.android.cryptomonitor.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import java.util.concurrent.TimeUnit;

public class JobUtil {

    private static final int JOB_ID = 74896;
    private static final String JOB_TAG = "update_currencies_tag";

    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    public static void scheduleJob(Context context) {

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName jobService =  new ComponentName(context.getPackageName(), UpdateJobService.class.getName());
        JobInfo jobInfo =  new JobInfo.Builder(JOB_ID, jobService)
                .setPeriodic(30 * 1000)
                .build();

        jobScheduler.schedule(jobInfo);
    }
}
