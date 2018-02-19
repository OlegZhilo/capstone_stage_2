package ru.crypto.android.cryptomonitor.ui.test;


import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.ui.base.BaseAсtivity;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;

public class TestActivity extends BaseAсtivity<TestViewModel> {

    private static final String TAG = TestActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<? extends BaseViewModel> getModelView() {
        return TestViewModel.class;
    }
}