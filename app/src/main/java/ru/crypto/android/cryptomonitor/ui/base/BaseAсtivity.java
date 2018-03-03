package ru.crypto.android.cryptomonitor.ui.base;

import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import ru.crypto.android.cryptomonitor.app.ViewModelFactory;

public abstract class BaseAсtivity<M extends BaseViewModel> extends AppCompatActivity implements HasFragmentInjector {

    private M viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initViewModel();
        subscribeErrorLiveData();
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void subscribeErrorLiveData() {
        viewModel.getErrorLiveData().observe(this,
                throwable -> Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show());
    }

    protected abstract int getContentView();

    protected abstract Class<? extends BaseViewModel> getViewModelClass();

    protected M getViewModel() {
        return viewModel;
    }

    @SuppressWarnings("unchecked")
    private void initViewModel() {
        viewModel = (M) ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
    }
}