package ru.crypto.android.cryptomonitor.ui.list;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.ui.base.BaseAсtivity;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;

public class MainActivity extends BaseAсtivity<MainViewModel> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = findViewById(R.id.view_pager);
//        FavoriteCurrencyFragment favoriteCurrencyFragment = new FavoriteCurrencyFragment();
//
        FragmentManager fragmentManager = getFragmentManager();
//
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);
//
//        fragmentManager.beginTransaction()
//                .add(R.id.fragment_container, favoriteCurrencyFragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit();
    }
}