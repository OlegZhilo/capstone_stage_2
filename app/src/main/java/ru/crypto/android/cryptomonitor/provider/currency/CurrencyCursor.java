package ru.crypto.android.cryptomonitor.provider.currency;

import android.database.Cursor;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.crypto.android.cryptomonitor.domain.Currency;
import ru.crypto.android.cryptomonitor.provider.base.AbstractCursor;
import ru.crypto.android.cryptomonitor.repository.CurrencyRepository;

/**
 * Cursor wrapper for the {@code currency} table.
 */
public class CurrencyCursor extends AbstractCursor {

    CurrencyCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public long get_Id() {
        Long res = getLongOrNull(CurrencyContract._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    public Currency getCurrency() {
        Currency currency =  new Currency(
                getId(),
                getName(),
                getPercentchange24h(),
                getPercentchange1h(),
                getPercentchange24h(),
                getPercentchange7d(),
                getPriceusd(),
                getRank(),
                getSymbol());
        currency.setFavorite(true);
        return currency;
    }

    public List<Currency> getCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        while (getWrappedCursor().moveToNext()) {
            currencies.add(getCurrency());
        }
        return currencies;
    }

    @Nullable
    public String getId() {
        return getStringOrNull(CurrencyContract.ID);
    }

    @Nullable
    public String getName() {
        return getStringOrNull(CurrencyContract.NAME);
    }

    @Nullable
    public String getVolume24husd() {
        return getStringOrNull(CurrencyContract.VOLUME24HUSD);
    }

    @Nullable
    public String getPercentchange1h() {
        return getStringOrNull(CurrencyContract.PERCENTCHANGE1H);
    }

    @Nullable
    public String getPercentchange24h() {
        return getStringOrNull(CurrencyContract.PERCENTCHANGE24H);
    }

    @Nullable
    public String getPercentchange7d() {
        return getStringOrNull(CurrencyContract.PERCENTCHANGE7D);
    }

    @Nullable
    public String getPriceusd() {
        return getStringOrNull(CurrencyContract.PRICEUSD);
    }

    @Nullable
    public String getRank() {
        return getStringOrNull(CurrencyContract.RANK);
    }

    @Nullable
    public String getSymbol() {
        return getStringOrNull(CurrencyContract.SYMBOL);
    }
}
