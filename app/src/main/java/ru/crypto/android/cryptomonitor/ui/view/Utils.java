package ru.crypto.android.cryptomonitor.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;

public class Utils {

    public static final int RECOURSE_NOT_FOUND = 0;

    private Utils() {
    }

    public static int getDrawableForCurrency(Context context, Currency data) {
        int currencyDrawable;
        currencyDrawable = context.getResources().getIdentifier(data.getSymbol().toLowerCase(), "drawable", context.getPackageName());
        if (currencyDrawable == RECOURSE_NOT_FOUND)
            currencyDrawable = R.drawable.ic_money;
        return currencyDrawable;
    }

    public static Spannable getSpannedCurrencyString(Context context, Currency data) {
        String formattedCurrency = getString(context, R.string.currency_fmt, new String[]{data.getSymbol(), data.getName()});
        Spannable spannable = new SpannableString(formattedCurrency);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, data.getSymbol().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    @NonNull
    public static String getString(Context context, @StringRes int stringFmt, Object[] args) {
        return String.format(context.getString(stringFmt), args);
    }
}