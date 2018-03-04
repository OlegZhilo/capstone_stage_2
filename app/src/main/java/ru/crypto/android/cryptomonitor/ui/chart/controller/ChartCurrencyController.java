package ru.crypto.android.cryptomonitor.ui.chart.controller;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.surfstudio.easyadapter.recycler.controller.BindableItemController;
import ru.surfstudio.easyadapter.recycler.holder.BindableViewHolder;
import timber.log.Timber;

public class ChartCurrencyController extends BindableItemController<Currency, ChartCurrencyController.Holder> {


    @Override
    public Holder createViewHolder(ViewGroup parent) {
        return new Holder(parent);
    }

    @Override
    protected long getItemId(Currency data) {
        return data.getId().hashCode();
    }

    public class Holder extends BindableViewHolder<Currency> {

        TextView currencyNameTv;
        TextView currencyPriceTv;
        TextView currency1hChangeTv;
        TextView currency24hChangeTv;
        TextView currency7dChangeTv;
        ImageView currencyIconIv;

        DecimalFormat priceFormat = new DecimalFormat("#.##");

        public Holder(ViewGroup parent) {
            super(parent, R.layout.chart_currency_card_view);
            currencyNameTv = itemView.findViewById(R.id.currency_name_tv);
            currencyPriceTv = itemView.findViewById(R.id.currency_price_tv);
            currencyIconIv = itemView.findViewById(R.id.currency_icon_tv);
            currency1hChangeTv = itemView.findViewById(R.id.currency_1_h_changes_tv);
            currency24hChangeTv = itemView.findViewById(R.id.currency_24_h_changes_tv);
            currency7dChangeTv = itemView.findViewById(R.id.currency_7_d_changes_tv);
            DecimalFormatSymbols unusualSymbols =
                    new DecimalFormatSymbols(Locale.US);
            unusualSymbols.setDecimalSeparator('.');
            priceFormat.setDecimalFormatSymbols(unusualSymbols);
        }

        @Override
        public void bind(Currency data) {
            currencyIconIv.setImageResource(getDrawable(data));
            currencyNameTv.setText(getSpannedCurrencyString(data));
            currencyPriceTv.setText(getCurrencyPrice(data));
            currency1hChangeTv.setText(getCurrency1hChange(data));
            currency24hChangeTv.setText(getCurrency24hChange(data));
            currency7dChangeTv.setText(getCurrency7dChange(data));

            Timber.d(data.getSymbol() + " " + Double.parseDouble(data.getPriceUsd()));
        }

        private String getCurrencyPrice(Currency data) {
            double price = Double.parseDouble(data.getPriceUsd());
            String formatedPrice = priceFormat.format(price);
            return getString(R.string.currency_price_fmt, new String[]{formatedPrice});
        }

        private Spannable getCurrency1hChange(Currency data) {
            return getSpannedCurrencyChange(R.string.currency_1h_change_fmt, data.getPercentChange1H());
        }

        private Spannable getCurrency24hChange(Currency data) {
            return getSpannedCurrencyChange(R.string.currency_24h_change_fmt, data.getPercentChange24H());
        }

        private Spannable getCurrency7dChange(Currency data) {
            return getSpannedCurrencyChange(R.string.currency_7d_change_fmt, data.getPercentChange7D());
        }

        @NonNull
        private String getString(@StringRes int stringFmt, Object[] args) {
            return String.format(itemView.getContext().getString(stringFmt), args);
        }

        private Spannable getSpannedCurrencyString(Currency data) {
            String formattedCurrency = getString(R.string.currency_fmt, new String[]{data.getSymbol(), data.getName()});
            Spannable spannable = new SpannableString(formattedCurrency);
            spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, data.getSymbol().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannable;
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

        private int getDrawable(Currency data) {
            return itemView.getContext().getResources().getIdentifier(data.getSymbol().toLowerCase(), "drawable", itemView.getContext().getPackageName());
        }
    }
}