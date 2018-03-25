package ru.crypto.android.cryptomonitor.provider.currency;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ru.crypto.android.cryptomonitor.provider.base.AbstractSelection;

/**
 * Selection for the {@code currency} table.
 */
public class CurrencySelection extends AbstractSelection<CurrencySelection> {

    @Override
    protected Uri baseUri() {
        return CurrencyContract.CONTENT_URI;
    }

    public CurrencyCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CurrencyCursor(cursor);
    }

    public CurrencyCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    public CurrencySelection id(long... value) {
        addEquals("currency." + CurrencyContract._ID, toObjectArray(value));
        return this;
    }

    public CurrencySelection id(String... value) {
        addEquals(CurrencyContract.ID, value);
        return this;
    }

}