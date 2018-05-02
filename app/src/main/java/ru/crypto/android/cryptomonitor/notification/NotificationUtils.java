package ru.crypto.android.cryptomonitor.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.view.Utils;

import static ru.crypto.android.cryptomonitor.ui.view.Utils.getString;

public class NotificationUtils {

    private static final String NOTIFICATION_CHANNEL_ID = NotificationUtils.class.getCanonicalName() + "notification_channel";

    private static final int NOTIFICATION_ID = 181438;
    private static final String CURRENCY_NOTIFICATION_CHANNEL_ID = "notification_currency_id";

    public static void notify(Context context, Currency currency) {
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_layout);

        String currencyPrice = "";
        String currency24Rate = "";
        if (!TextUtils.isEmpty(currency.getPriceUsd())) {
            currencyPrice = getString(context, R.string.currency_price_fmt, new String[]{currency.getPriceUsd()});
        }

        if (!TextUtils.isEmpty(currency.getPercentChange24H())) {
            currency24Rate = getString(context, R.string.currency_24h_change_fmt, new String[]{currency.getPercentChange24H()});
        }

        notificationLayout.setTextViewText(R.id.notification_symbol, currency.getSymbol());
        notificationLayout.setTextViewText(R.id.notification_price, currencyPrice);
        notificationLayout.setTextViewText(R.id.currency_rate_tv, currency24Rate);

        notify(context, notificationLayout, Utils.getDrawableForCurrency(context, currency));
    }

    private static void notify(Context context, RemoteViews remoteViews, int icon) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    CURRENCY_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_bitcoin)
                .setLargeIcon(largeIcon(context, icon))
//                .setContentTitle("Title")
//                .setContentText("Content text")
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteViews)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setContentIntent(contentIntent(context))
//                .addAction(drinkWaterAction(context))
//                .addAction(ignoreReminderAction(context))
                .setOngoing(true)
                .setAutoCancel(false);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private static Bitmap largeIcon(Context context, int icon) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, icon);
    }

}