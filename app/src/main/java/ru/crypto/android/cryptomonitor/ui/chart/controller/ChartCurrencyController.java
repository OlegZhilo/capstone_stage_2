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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.view.BaseCurrencyHolder;
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

    public class Holder extends BaseCurrencyHolder {

        TextView currencyNameTv;
        TextView currencySymbolTv;
        TextView currencyPriceTv;
        TextView currency1hChangeTv;
        ImageView currencyIconIv;

        public Holder(ViewGroup parent) {
            super(parent, R.layout.chart_currency_card_view);
            currencyNameTv = itemView.findViewById(R.id.currency_name_tv);
            currencySymbolTv = itemView.findViewById(R.id.currency_symbol_tv);
            currencyPriceTv = itemView.findViewById(R.id.currency_price_tv);
            currencyIconIv = itemView.findViewById(R.id.currency_icon_tv);
//            currency1hChangeTv = itemView.findViewById(R.id.currency_1_h_changes_tv);
        }

        @Override
        public void bind(Currency data) {
            itemView.setOnClickListener(v -> {

            });
            currencyIconIv.setImageResource(getDrawable(data));
            currencyNameTv.setText(data.getName());
            currencySymbolTv.setText(data.getSymbol());
            currencyPriceTv.setText(getCurrencyPrice(data));
//            currency1hChangeTv.setText(getCurrency1hChange(data));
        }

    }
}