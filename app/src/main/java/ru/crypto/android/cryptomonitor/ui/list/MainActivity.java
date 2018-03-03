package ru.crypto.android.cryptomonitor.ui.list;


import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.ui.base.BaseAсtivity;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;

public class MainActivity extends BaseAсtivity<MainViewModel> {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return MainViewModel.class;
    }
}