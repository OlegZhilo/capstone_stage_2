package ru.crypto.android.cryptomonitor.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.base.BaseFragment;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import ru.crypto.android.cryptomonitor.ui.list.controllers.CurrencyController;
import ru.crypto.android.cryptomonitor.ui.main.MainViewModel;
import ru.surfstudio.easyadapter.recycler.EasyAdapter;
import ru.surfstudio.easyadapter.recycler.ItemList;


public class FavoriteCurrencyFragment extends BaseFragment<MainViewModel> {

    @BindView(R.id.swr)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private EasyAdapter adapter = new EasyAdapter();

    private CurrencyController currencyController = new CurrencyController();

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> getViewModel().loadCurrencies());
    }

    @Override
    protected void onStartVisibleView() {
        getViewModel().getCurrencyLiveData().observeForever(this::render);
        getViewModel().loadCurrencies();

    }
    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_currency_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void render(List<Currency> list) {
//        swipeRefreshLayout.setRefreshing(false);
        adapter.setItems(ItemList.create().addAll(list, currencyController));
    }
}
