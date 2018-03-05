package ru.crypto.android.cryptomonitor.ui.view;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.surfstudio.easyadapter.recycler.holder.BindableViewHolder;

public abstract class BaseCurrencyHolder extends BindableViewHolder<Currency> {

    DecimalFormat priceFormat = new DecimalFormat("#.##");

    public BaseCurrencyHolder(ViewGroup parent, int layoutRes) {
        super(parent, layoutRes);
        DecimalFormatSymbols unusualSymbols =
                new DecimalFormatSymbols(Locale.US);
        unusualSymbols.setDecimalSeparator('.');
        priceFormat.setDecimalFormatSymbols(unusualSymbols);
    }

    protected String getCurrencyPrice(Currency data) {
        double price = Double.parseDouble(data.getPriceUsd());
        String formatedPrice = priceFormat.format(price);
        return getString(R.string.currency_price_fmt, new String[]{formatedPrice});
    }

    protected Spannable getCurrency1hChange(Currency data) {
        return getSpannedCurrencyChange(R.string.currency_1h_change_fmt, data.getPercentChange1H());
    }

    protected Spannable getCurrency24hChange(Currency data) {
        return getSpannedCurrencyChange(R.string.currency_24h_change_fmt, data.getPercentChange24H());
    }

    protected Spannable getCurrency7dChange(Currency data) {
        return getSpannedCurrencyChange(R.string.currency_7d_change_fmt, data.getPercentChange7D());
    }

    protected Spannable getSpannedCurrencyString(Currency data) {
        String formattedCurrency = getString(R.string.currency_fmt, new String[]{data.getSymbol(), data.getName()});
        Spannable spannable = new SpannableString(formattedCurrency);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, data.getSymbol().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    protected int getDrawable(Currency data) {
        return itemView.getContext().getResources().getIdentifier(data.getSymbol().toLowerCase(), "drawable", itemView.getContext().getPackageName());
    }

    private Spannable getSpannedCurrencyChange(@StringRes int stringFmt, String currencyRate) {
        String formattedCurrency = getString(stringFmt, new String[]{currencyRate});
        Spannable spannable = new SpannableString(formattedCurrency);
        int color = currencyRate.contains("-") ? R.color.red_600 : R.color.green_600;
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(itemView.getContext(), color)),
                formattedCurrency.indexOf(currencyRate),
                formattedCurrency.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    @NonNull
    private String getString(@StringRes int stringFmt, Object[] args) {
        return String.format(itemView.getContext().getString(stringFmt), args);
    }

}