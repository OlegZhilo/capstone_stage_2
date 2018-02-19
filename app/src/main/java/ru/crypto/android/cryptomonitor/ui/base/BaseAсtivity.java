package ru.crypto.android.cryptomonitor.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseAсtivity<M extends BaseViewModel> extends AppCompatActivity {

    private M viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initViewModel();
    }

    protected abstract int getContentView();

    protected abstract Class<? extends BaseViewModel> getModelView();

    @SuppressWarnings("unchecked")
    private void initViewModel() {
        viewModel = (M) ViewModelProviders.of(this).get(getModelView());
    }

    public M getViewModel() {
        return viewModel;
    }
}