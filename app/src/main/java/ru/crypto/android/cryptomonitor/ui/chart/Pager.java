package ru.crypto.android.cryptomonitor.ui.chart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import ru.crypto.android.cryptomonitor.R;

public class Pager  extends PagerAdapter{

    private Context context;

    public Pager(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.day);
            case 1:
                return context.getString(R.string.week);
            case 2:
                return context.getString(R.string.month);
            case 3:
                return context.getString(R.string.year);
        }
        return "";
    }
}
