package ru.crypto.android.cryptomonitor.ui.list;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


public class PagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = new Fragment[]{
            new FavoriteCurrencyFragment(),
            new FavoriteCurrencyFragment()
    };

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragments[0];
            case 1:
                return fragments[1];
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
