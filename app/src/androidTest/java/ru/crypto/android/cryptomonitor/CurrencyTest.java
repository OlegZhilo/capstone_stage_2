package ru.crypto.android.cryptomonitor;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import javax.inject.Inject;

import ru.crypto.android.cryptomonitor.base.DaggerTestAppComponent;
import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;
import ru.crypto.android.cryptomonitor.util.NetworkRequestUtil;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CurrencyTest {

    @Inject
    CurrencyRepository repository;

    @Before
    public void init() {
        DaggerTestAppComponent.builder().build().inject(this);
    }

    @Test
    public void loadCurrencies() {
        List<Currency> currencyList = NetworkRequestUtil.makeRequest(repository.getCurrencies());
        Assert.assertEquals(true, !currencyList.isEmpty());
    }

    @Test
    public void loadCurrency() {
        String id = "bitcoin";
        List<Currency> currencyList = NetworkRequestUtil.makeRequest(repository.getCurrency(id));
        Assert.assertEquals(true, !currencyList.isEmpty());
        Assert.assertEquals(true, currencyList.get(0).getId().equals(id));
    }
}