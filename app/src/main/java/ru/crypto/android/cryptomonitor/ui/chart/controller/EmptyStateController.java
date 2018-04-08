package ru.crypto.android.cryptomonitor.ui.chart.controller;

import android.view.ViewGroup;

import ru.crypto.android.cryptomonitor.R;
import ru.surfstudio.easyadapter.recycler.controller.NoDataItemController;
import ru.surfstudio.easyadapter.recycler.holder.BaseViewHolder;

public class EmptyStateController extends NoDataItemController<EmptyStateController.Holder> {

    @Override
    public Holder createViewHolder(ViewGroup parent) {
        return new Holder(parent);
    }

    class Holder extends BaseViewHolder {

        Holder(ViewGroup parent) {
            super(parent, R.layout.empty_chart_controller);
        }

    }
}