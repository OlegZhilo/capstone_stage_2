package ru.crypto.android.cryptomonitor.ui.addcurrency.controllers;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.view.OnFavoriteClick;
import ru.surfstudio.easyadapter.recycler.controller.BindableItemController;
import ru.surfstudio.easyadapter.recycler.holder.BindableViewHolder;

import static ru.crypto.android.cryptomonitor.ui.view.Utils.getDrawableForCurrency;
import static ru.crypto.android.cryptomonitor.ui.view.Utils.getSpannedCurrencyString;

public class AddCurrencyController extends BindableItemController<Currency, AddCurrencyController.Holder> {

    private OnFavoriteClick onFavoriteClick;

    public AddCurrencyController(OnFavoriteClick onCurrencyClick) {
        this.onFavoriteClick = onCurrencyClick;
    }

    @Override
    protected long getItemId(Currency data) {
        return data.getId().hashCode();
    }

    @Override
    protected long getItemHash(Currency data) {
        return data.hashCode();
    }

    @Override
    public Holder createViewHolder(ViewGroup parent) {
        return new Holder(parent);
    }

    public class Holder extends BindableViewHolder<Currency> {

        ImageView currencyIconIv;
        TextView currencyNameTv;
        ViewGroup favoriteMarker;

        Holder(ViewGroup parent) {
            super(parent, R.layout.currency_add_controller);
            currencyIconIv = itemView.findViewById(R.id.currency_icon_tv);
            currencyNameTv = itemView.findViewById(R.id.currency_name_tv);
            favoriteMarker = itemView.findViewById(R.id.favorite_marker);
        }

        @Override
        public void bind(Currency data) {
            currencyIconIv.setImageResource(getDrawableForCurrency(itemView.getContext(), data));
            currencyNameTv.setText(getSpannedCurrencyString(itemView.getContext(), data));
            favoriteMarker.setSelected(data.isFavorite());
            favoriteMarker.setOnClickListener(v -> onFavoriteClick.onClick(data));
        }
    }
}
