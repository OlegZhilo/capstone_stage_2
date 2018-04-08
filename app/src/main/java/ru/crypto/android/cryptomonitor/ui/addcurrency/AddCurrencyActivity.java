package ru.crypto.android.cryptomonitor.ui.addcurrency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.crypto.android.cryptomonitor.R;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.ui.addcurrency.controllers.AddCurrencyController;
import ru.crypto.android.cryptomonitor.ui.base.BaseAсtivity;
import ru.crypto.android.cryptomonitor.ui.base.BaseViewModel;
import ru.surfstudio.easyadapter.recycler.EasyAdapter;
import ru.surfstudio.easyadapter.recycler.ItemList;

public class AddCurrencyActivity extends BaseAсtivity<AddCurrencyViewModel> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.state_tv)
    TextView stateTv;

    private EasyAdapter adapter = new EasyAdapter();
    private AddCurrencyController addCurrencyController = new AddCurrencyController(this::onFavoriteClick);
    private Disposable textChangesDisposable;
    private List<Currency> currencyList;
    private String filterText = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> getViewModel().loadCurrencies());

        swipeRefreshLayout.setRefreshing(true);
        getViewModel().getCurrencyLiveData().observeForever(this::render);
        getViewModel().loadCurrencies();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textChangesDisposable != null && !textChangesDisposable.isDisposed()) {
            textChangesDisposable.dispose();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        textChangesDisposable = RxSearchView.queryTextChangeEvents(searchView)
                .skipInitialValue()
                .map(searchViewQueryTextEvent -> searchViewQueryTextEvent.queryText().toString())
                .distinctUntilChanged()
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showFiltered);

        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_cyrrency;
    }

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return AddCurrencyViewModel.class;
    }

    @Override
    protected void onError(Throwable throwable) {
        stateTv.setText(R.string.error_state);
        stateTv.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void onFavoriteClick(Currency currency) {
        getViewModel().onFavoriteClick(currency);
    }

    private void render(List<Currency> currencyList) {
        stateTv.setVisibility(View.INVISIBLE);
        this.currencyList = currencyList;
        swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 500);
        showFiltered(filterText);
    }

    private void showFiltered(String filterText) {
        this.filterText = filterText;
        List<Currency> filteredList =
                Stream.of(currencyList)
                        .filter(currency ->
                                currency.getName().toLowerCase().startsWith(filterText)
                                        || currency.getSymbol().toLowerCase().startsWith(filterText))
                        .toList();

        adapter.setItems(ItemList.create()
                .addAll(filteredList, addCurrencyController));
    }
}