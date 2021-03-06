package ru.crypto.android.cryptomonitor.ui.list.controllers;

import android.support.v4.view.ViewCompat;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.view.BaseCurrencyHolder;
import ru.surfstudio.easyadapter.recycler.controller.BindableItemController;

import static ru.crypto.android.cryptomonitor.ui.view.Utils.getDrawableForCurrency;
import static ru.crypto.android.cryptomonitor.ui.view.Utils.getSpannedCurrencyString;

public class CurrencyController extends BindableItemController<Currency, CurrencyController.Holder> {

    public interface OnCurrencyClickListener {
        void onCurrencyClick(Currency currency);
    }

    private OnCurrencyClickListener onCurrencyClickListener;

    public CurrencyController(OnCurrencyClickListener onCurrencyClickListener) {
        this.onCurrencyClickListener = onCurrencyClickListener;
    }

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
        ViewGroup notifyMarkerView;

        private boolean show;

        public Holder(ViewGroup parent) {
            super(parent, R.layout.currency_card_view);
            currencyNameTv = itemView.findViewById(R.id.currency_name_tv);
            currencyPriceTv = itemView.findViewById(R.id.currency_price_tv);
            currencyIconIv = itemView.findViewById(R.id.currency_icon_tv);
            currency1hChangeTv = itemView.findViewById(R.id.currency_1_h_changes_tv);
            currency24hChangeTv = itemView.findViewById(R.id.currency_24_h_changes_tv);
            currency7dChangeTv = itemView.findViewById(R.id.currency_7_d_changes_tv);
            notifyMarkerView = itemView.findViewById(R.id.notification_marker_view);
        }

        @Override
        public void bind(Currency data) {
            currencyIconIv.setImageResource(getDrawableForCurrency(itemView.getContext(), data));
            currencyNameTv.setText(getSpannedCurrencyString(itemView.getContext(), data));
            currencyPriceTv.setText(getCurrencyPrice(data));
            currency1hChangeTv.setText(getCurrency1hChange(data));
            currency24hChangeTv.setText(getCurrency24hChange(data));
            currency7dChangeTv.setText(getCurrency7dChange(data));
            showMarkerWithAnim(data.isNotifiable());

            itemView.setOnClickListener(v -> onCurrencyClickListener.onCurrencyClick(data));
        }

        private void showMarkerWithAnim(boolean show) {
            ViewCompat.animate(notifyMarkerView)
                    .scaleX(show ? 1f : 0f)
                    .scaleY(show ? 1f : 0f)
                    .setDuration(show ? 250 : 150)
                    .setInterpolator(show ? new OvershootInterpolator() : new AccelerateInterpolator())
                    .start();
        }
    }
}