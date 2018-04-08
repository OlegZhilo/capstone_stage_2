package ru.crypto.android.cryptomonitor.ui.chart.controller;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.view.BaseCurrencyHolder;
import ru.crypto.android.cryptomonitor.ui.view.OnCurrencyClick;
import ru.surfstudio.easyadapter.recycler.controller.BindableItemController;

import static ru.crypto.android.cryptomonitor.ui.view.Utils.getDrawableForCurrency;

public class ChartCurrencyController extends BindableItemController<Currency, ChartCurrencyController.Holder> {

    private OnCurrencyClick onCurrencyClick;

    public ChartCurrencyController(OnCurrencyClick onCurrencyClick) {
        this.onCurrencyClick = onCurrencyClick;
    }

    @Override
    public Holder createViewHolder(ViewGroup parent) {
        return new Holder(parent, onCurrencyClick);
    }

    @Override
    protected long getItemId(Currency data) {
        return data.getId().hashCode();
    }

    public class Holder extends BaseCurrencyHolder {

        private final OnCurrencyClick onCurrencyClick;
        TextView currencyNameTv;
        TextView currencySymbolTv;
        TextView currencyPriceTv;
        TextView currency1hChangeTv;
        ImageView currencyIconIv;

        Holder(ViewGroup parent, OnCurrencyClick onCurrencyClick) {
            super(parent, R.layout.chart_currency_card_view);
            this.onCurrencyClick = onCurrencyClick;
            currencyNameTv = itemView.findViewById(R.id.currency_name_tv);
            currencySymbolTv = itemView.findViewById(R.id.currency_symbol_tv);
            currencyPriceTv = itemView.findViewById(R.id.currency_price_tv);
            currencyIconIv = itemView.findViewById(R.id.currency_icon_tv);
            currency1hChangeTv = itemView.findViewById(R.id.currency_1_h_changes_tv);
        }

        @Override
        public void bind(Currency data) {
            itemView.setOnClickListener(v -> onCurrencyClick.onClick(data));
            currencyIconIv.setImageResource(getDrawableForCurrency(itemView.getContext(),data));
            currencyNameTv.setText(data.getName());
            currencySymbolTv.setText(data.getSymbol());
            currencyPriceTv.setText(getCurrencyPrice(data));
            currency1hChangeTv.setText(getCurrency1hChange(data));
        }

    }
}