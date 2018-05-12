package ru.crypto.android.cryptomonitor.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.List;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;

public class WidgetProvider extends AppWidgetProvider {

    public static void updateRecipeWidgets(Context context,
                                           AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, List<Currency> currencies) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, currencies);
        }
    }

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId,
                                List<Currency> currencies) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_layout);
        views.removeAllViews(R.id.widget_list);

        for (Currency currency : currencies) {
            RemoteViews currencyLayout = new RemoteViews(context.getPackageName(),
                    R.layout.widget_list_item_layout);
            currencyLayout.setTextViewText(R.id.currency_name_tv, currency.getName());
            currencyLayout.setTextViewText(R.id.currency_price_tv, currency.getPriceUsd());
            views.addView(R.id.widget_list, currencyLayout);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
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
}