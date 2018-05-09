package ru.crypto.android.cryptomonitor.services.analytic;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class GoogleAnalyticsApi {

    private Tracker tracker;

    public GoogleAnalyticsApi(Context context) {
        init(context);
    }

    private void init(Context context) {
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(context);
        tracker = googleAnalytics.newTracker("id");
    }

    public void trackAddToFavorite(String currencyId) {
        setUpEvent(Category.CURRENCY.name(), Action.ADD.name(), currencyId);
    }

    public void trackDeleteFromFavorite(String currencyId) {
        setUpEvent(Category.CURRENCY.name(), Action.DELETE.name(), currencyId);
    }

    private void setUpEvent(String category, String action, String label) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }
}
