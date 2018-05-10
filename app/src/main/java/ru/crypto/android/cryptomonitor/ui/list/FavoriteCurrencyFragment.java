package ru.crypto.android.cryptomonitor.ui.list;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.provider.currency.CurrencyContract;
import ru.crypto.android.cryptomonitor.provider.currency.CurrencyCursor;
import ru.crypto.android.cryptomonitor.ui.base.BaseFragment;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import ru.crypto.android.cryptomonitor.ui.list.controllers.CurrencyController;
import ru.crypto.android.cryptomonitor.ui.settings.SettingsActivity;
import ru.crypto.android.cryptomonitor.ui.view.HidingScrollListener;
import ru.surfstudio.easyadapter.recycler.EasyAdapter;
import ru.surfstudio.easyadapter.recycler.ItemList;
import timber.log.Timber;


public class FavoriteCurrencyFragment extends BaseFragment<FavoriteCurrencyViewModel> implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 4567;
    @BindView(R.id.swr)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.state_tv)
    TextView stateTv;
    @BindView(R.id.close_btn)
    ImageView closeBtn;
    @BindView(R.id.settings_btn)
    ImageView settingsBtn;

    private EasyAdapter adapter = new EasyAdapter();

    private CurrencyController currencyController = new CurrencyController(currency -> getViewModel().saveNotifyCurrency(getActivity(), currency));

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new HidingScrollListener(true) {
            @Override
            public void onHide() {
                ViewCompat.animate(settingsBtn)
                        .translationX(settingsBtn.getWidth())
                        .rotation(360)
                        .setDuration(250)
                        .start();
                ViewCompat.animate(closeBtn)
                        .translationX(-closeBtn.getWidth())
                        .rotation(-360)
                        .setDuration(250)
                        .start();
            }

            @Override
            public void onShow() {
                ViewCompat.animate(settingsBtn)
                        .translationX(0F)
                        .rotation(0)
                        .setDuration(250)
                        .setInterpolator(new OvershootInterpolator())
                        .start();
                ViewCompat.animate(closeBtn)
                        .translationX(0F)
                        .rotation(0F)
                        .setDuration(250)
                        .setInterpolator(new OvershootInterpolator())
                        .start();
            }
        });
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        swipeRefreshLayout.setOnRefreshListener(getViewModel()::loadCurrencies);

        closeBtn.setOnClickListener(v -> getActivity().finish());
        settingsBtn.setOnClickListener(v -> getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!swipeRefreshLayout.isRefreshing()) {
            initLoadManager();
        }
    }

    @Override
    protected void onStartVisibleView() {
        getViewModel().getCurrencyLiveData().observeForever(this::render);
        initLoadManager();

    }

    private void initLoadManager() {
        getLoaderManager().destroyLoader(LOADER_ID);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return FavoriteCurrencyViewModel.class;
    }

    @Override
    protected void onError(Throwable throwable) {
        stateTv.setText(R.string.error_state);
        stateTv.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_currency_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void render(List<Currency> list) {
        swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 500);
        if (list.isEmpty()) {
            stateTv.setText(R.string.empty_favorite_currency);
        }
        stateTv.setVisibility(list.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        adapter.setItems(ItemList.create().addAll(list, currencyController));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                CurrencyContract.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<Currency> currencies = new ArrayList<>();
        while (data.moveToNext()) {
            CurrencyCursor currencyCursor = new CurrencyCursor(data);
            currencies.add(currencyCursor.getCurrency());
            Timber.d(currencyCursor.getId());
        }
        data.close();
        getViewModel().setCurrency(currencies);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
