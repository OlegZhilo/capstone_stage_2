package ru.crypto.android.cryptomonitor;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import javax.inject.Inject;

import ru.crypto.android.cryptomonitor.base.DaggerTestAppComponent;
import ru.crypto.android.cryptomonitor.domain.ChartData;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.repository.Period;
import ru.crypto.android.cryptomonitor.util.NetworkRequestUtil;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CurrencyTest {

    @Inject
    CurrencyRepository repository;

    @Before
    public void init() {
        DaggerTestAppComponent.builder()
                .context(InstrumentationRegistry.getContext())
                .build()
                .inject(this);
    }

    @Test
    public void loadCurrenciesTest() {
        List<Currency> currencyList = NetworkRequestUtil.makeRequest(repository.getCurrencies());
        Assert.assertEquals(true, !currencyList.isEmpty());
    }

    @Test
    public void loadSingleCurrencyTest() {
        String id = "bitcoin";
        List<Currency> currencyList = NetworkRequestUtil.makeRequest(repository.getCurrency(id));
        Assert.assertEquals(true, !currencyList.isEmpty());
        Assert.assertEquals(true, currencyList.get(0).getId().equals(id));
    }

    @Test
    public void saveToDbCurrenciesTest() {
        repository.clearCurrencyTable();
        List<Currency> currenciesNetwork = NetworkRequestUtil.makeRequest(repository.getCurrencies());
        int createdCount = repository.saveCurrencies(currenciesNetwork);
        List<Currency> currenciesCached = repository.getCachedCurrencies();

        Assert.assertEquals(currenciesNetwork.size(), createdCount);
        Assert.assertEquals(currenciesNetwork.size(), currenciesCached.size());
    }

    @Test
    public void deleteRowsTest() {
        repository.clearCurrencyTable();
        List<Currency> currenciesNetwork = NetworkRequestUtil.makeRequest(repository.getCurrencies());
        repository.saveCurrencies(currenciesNetwork);

        Currency currencyForDelete = currenciesNetwork.get(0);
        int deletedCount = repository.deleteCurrency(currencyForDelete);
        List<Currency> currenciesCached = repository.getCachedCurrencies();

        Assert.assertEquals(deletedCount, currenciesNetwork.size() - currenciesCached.size());
        Assert.assertEquals(false, currenciesCached.contains(currencyForDelete));

        deletedCount = repository.clearCurrencyTable();
        Assert.assertEquals(currenciesCached.size(), deletedCount);
    }

    @Test
    public void loadChart() {
        String fromSymbol = "BTC";
        List<ChartData> chartDataList = NetworkRequestUtil.makeRequest(repository.getCurrencyHistory(fromSymbol, Period.DAY));

        Assert.assertEquals(true, chartDataList.size() >= Period.DAY.getCount());

        chartDataList = NetworkRequestUtil.makeRequest(repository.getCurrencyHistory(fromSymbol, Period.WEEK));
        Assert.assertEquals(true, chartDataList.size() >= Period.WEEK.getCount());

        chartDataList = NetworkRequestUtil.makeRequest(repository.getCurrencyHistory(fromSymbol, Period.MONTH));
        Assert.assertEquals(true, chartDataList.size() >= Period.MONTH.getCount());

        chartDataList = NetworkRequestUtil.makeRequest(repository.getCurrencyHistory(fromSymbol, Period.YEAR));
        Assert.assertEquals(true, chartDataList.size() >= Period.YEAR.getCount());
    }
}