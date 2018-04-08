package ru.crypto.android.cryptomonitor.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ru.crypto.android.cryptomonitor.app.ViewModelFactory;


public abstract class BaseFragment<M extends BaseViewModel> extends Fragment {

    private M viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        onStartVisibleView();
        subscribeErrorLiveData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    protected abstract void initViews();

    protected abstract void onStartVisibleView();

    protected abstract Class<? extends BaseViewModel> getViewModelClass();

    protected abstract void onError(Throwable throwable);

    protected M getViewModel() {
        return viewModel;
    }

    @SuppressWarnings("unchecked")
    private void initViewModel() {
        viewModel = (M) ViewModelProviders.of(getActivity(), viewModelFactory).get(getViewModelClass());
    }

    private void subscribeErrorLiveData() {
        viewModel.getErrorLiveData().observe(this, this::onError);
    }
}
