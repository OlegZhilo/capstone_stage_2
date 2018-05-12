package ru.crypto.android.cryptomonitor.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.RemoteViews;

import java.text.DecimalFormat;
import java.util.List;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;

import static ru.crypto.android.cryptomonitor.ui.view.Utils.getSpannedCurrencyString;
import static ru.crypto.android.cryptomonitor.ui.view.Utils.getString;

public class WidgetProvider extends AppWidgetProvider {

    public static void updateRecipeWidgets(Context context,
                                           AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, Currency currency) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, currency);
        }
    }

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId,
                                Currency currency) {
        RemoteViews currencyLayout = new RemoteViews(context.getPackageName(),
                R.layout.widget_list_item_layout);
        currencyLayout.setTextViewText(R.id.currency_name_tv, getSpannedCurrencyString(context, currency));
        currencyLayout.setTextViewText(R.id.currency_price_tv, getCurrencyPrice(context, currency));
        currencyLayout.setTextViewText(R.id.currency_1_h_changes_tv, getCurrency1hChange(context, currency));
        currencyLayout.setTextViewText(R.id.currency_24_h_changes_tv, getCurrency24hChange(context, currency));
        currencyLayout.setTextViewText(R.id.currency_7_d_changes_tv, getCurrency7dChange(context, currency));
        appWidgetManager.updateAppWidget(appWidgetId, currencyLayout);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        WidgetUpdateService.start(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static String getCurrencyPrice(Context context, Currency data) {
        if (TextUtils.isEmpty(data.getPriceUsd())) {
            return getString(context, R.string.currency_price_fmt, new String[]{"-"});
        }
        double price = Double.parseDouble(data.getPriceUsd());
        String formatedPrice = new DecimalFormat("#.####").format(price);
        return getString(context, R.string.currency_price_fmt, new String[]{formatedPrice});
    }

    private static Spannable getCurrency1hChange(Context context, Currency data) {
        return getSpannedCurrencyChange(context, R.string.currency_1h_change_fmt, data.getPercentChange1H());
    }

    private static Spannable getCurrency24hChange(Context context, Currency data) {
        return getSpannedCurrencyChange(context, R.string.currency_24h_change_fmt, data.getPercentChange24H());
    }

    private static Spannable getCurrency7dChange(Context context, Currency data) {
        return getSpannedCurrencyChange(context, R.string.currency_7d_change_fmt, data.getPercentChange7D());
    }

    private static Spannable getSpannedCurrencyChange(Context context, @StringRes int stringFmt, String currencyRate) {
        String formattedCurrency = getString(context, stringFmt, new String[]{currencyRate});
        Spannable spannable = new SpannableString(formattedCurrency);
        if (!TextUtils.isEmpty(currencyRate)) {
            int color = currencyRate.contains("-") ? R.color.red_600 : R.color.green_600;
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)),
                    formattedCurrency.indexOf(currencyRate),
                    formattedCurrency.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }
}