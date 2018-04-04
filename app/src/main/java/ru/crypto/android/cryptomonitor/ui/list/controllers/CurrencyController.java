package ru.crypto.android.cryptomonitor.ui.list.controllers;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.view.BaseCurrencyHolder;
import ru.surfstudio.easyadapter.recycler.controller.BindableItemController;

import static ru.crypto.android.cryptomonitor.ui.view.Utils.getDrawableForCurrency;
import static ru.crypto.android.cryptomonitor.ui.view.Utils.getSpannedCurrencyString;

public class CurrencyController extends BindableItemController<Currency, CurrencyController.Holder> {


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
        TextView currencyPriceTv;
        TextView currency1hChangeTv;
        TextView currency24hChangeTv;
        TextView currency7dChangeTv;
        ImageView currencyIconIv;

        public Holder(ViewGroup parent) {
            super(parent, R.layout.currency_card_view);
            currencyNameTv = itemView.findViewById(R.id.currency_name_tv);
            currencyPriceTv = itemView.findViewById(R.id.currency_price_tv);
            currencyIconIv = itemView.findViewById(R.id.currency_icon_tv);
            currency1hChangeTv = itemView.findViewById(R.id.currency_1_h_changes_tv);
            currency24hChangeTv = itemView.findViewById(R.id.currency_24_h_changes_tv);
            currency7dChangeTv = itemView.findViewById(R.id.currency_7_d_changes_tv);
        }

        @Override
        public void bind(Currency data) {
            currencyIconIv.setImageResource(getDrawableForCurrency(itemView.getContext(), data));
            currencyNameTv.setText(getSpannedCurrencyString(itemView.getContext(), data));
            currencyPriceTv.setText(getCurrencyPrice(data));
            currency1hChangeTv.setText(getCurrency1hChange(data));
            currency24hChangeTv.setText(getCurrency24hChange(data));
            currency7dChangeTv.setText(getCurrency7dChange(data));
        }

    }
}