package ru.crypto.android.cryptomonitor.provider.currency;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;

import ru.crypto.android.cryptomonitor.provider.base.BaseContentValues;

/**
 * Content values wrapper for the {@code Currency} table.
 */
public class CurrencyContentValues extends BaseContentValues<CurrencyContentValues> {

    @Override
    protected Uri baseUri() {
        return CurrencyContract.CONTENT_URI;
    }

    public int update(ContentResolver contentResolver, @Nullable CurrencySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public int update(Context context, @Nullable CurrencySelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CurrencyContentValues putId(@Nullable String value) {
        contentValues.put(CurrencyContract.ID, value);
        return this;
    }

    public CurrencyContentValues putIdNull() {
        contentValues.putNull(CurrencyContract.ID);
        return this;
    }

    public CurrencyContentValues putName(@Nullable String value) {
        contentValues.put(CurrencyContract.NAME, value);
        return this;
    }

    public CurrencyContentValues putNameNull() {
        contentValues.putNull(CurrencyContract.NAME);
        return this;
    }

    public CurrencyContentValues putVolume24husd(@Nullable String value) {
        contentValues.put(CurrencyContract.VOLUME24HUSD, value);
        return this;
    }

    public CurrencyContentValues putVolume24husdNull() {
        contentValues.putNull(CurrencyContract.VOLUME24HUSD);
        return this;
    }

    public CurrencyContentValues putPercentchange1h(@Nullable String value) {
        contentValues.put(CurrencyContract.PERCENTCHANGE1H, value);
        return this;
    }

    public CurrencyContentValues putPercentchange1hNull() {
        contentValues.putNull(CurrencyContract.PERCENTCHANGE1H);
        return this;
    }

    public CurrencyContentValues putPercentchange24h(@Nullable String value) {
        contentValues.put(CurrencyContract.PERCENTCHANGE24H, value);
        return this;
    }

    public CurrencyContentValues putPercentchange24hNull() {
        contentValues.putNull(CurrencyContract.PERCENTCHANGE24H);
        return this;
    }

    public CurrencyContentValues putPercentchange7d(@Nullable String value) {
        contentValues.put(CurrencyContract.PERCENTCHANGE7D, value);
        return this;
    }

    public CurrencyContentValues putPercentchange7dNull() {
        contentValues.putNull(CurrencyContract.PERCENTCHANGE7D);
        return this;
    }

    public CurrencyContentValues putPriceusd(@Nullable String value) {
        contentValues.put(CurrencyContract.PRICEUSD, value);
        return this;
    }

    public CurrencyContentValues putPriceusdNull() {
        contentValues.putNull(CurrencyContract.PRICEUSD);
        return this;
    }

    public CurrencyContentValues putRank(@Nullable String value) {
        contentValues.put(CurrencyContract.RANK, value);
        return this;
    }

    public CurrencyContentValues putRankNull() {
        contentValues.putNull(CurrencyContract.RANK);
        return this;
    }

    public CurrencyContentValues putSymbol(@Nullable String value) {
        contentValues.put(CurrencyContract.SYMBOL, value);
        return this;
    }

    public CurrencyContentValues putSymbolNull() {
        contentValues.putNull(CurrencyContract.SYMBOL);
        return this;
    }
}