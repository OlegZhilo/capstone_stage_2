package ru.crypto.android.cryptomonitor.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.crypto.android.cryptomonitor.provider.base.BaseContentProvider;
import ru.crypto.android.cryptomonitor.provider.base.Params;
import ru.crypto.android.cryptomonitor.provider.currency.CurrencyContract;

public class CryptoContentProvider extends BaseContentProvider {

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "ru.crypto.android.cryptomonitor.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_CURRENCY = 0;
    private static final int URI_TYPE_CURRENCY_ID = 1;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CurrencyContract.TABLE_NAME, URI_TYPE_CURRENCY);
        URI_MATCHER.addURI(AUTHORITY, CurrencyContract.TABLE_NAME + "/#", URI_TYPE_CURRENCY_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return DbOpenHelper.getInstance(getContext());
    }

    @Override
    public String getType(@NonNull Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_CURRENCY:
                return TYPE_CURSOR_DIR + CurrencyContract.TABLE_NAME;
            case URI_TYPE_CURRENCY_ID:
                return TYPE_CURSOR_ITEM + CurrencyContract.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected Params getQueryParams(Uri uri, String selection, String[] projection) {
        Params res = new Params();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_CURRENCY:
            case URI_TYPE_CURRENCY_ID:
                res.table = CurrencyContract.TABLE_NAME;
                res.idColumn = CurrencyContract._ID;
                res.tablesWithJoins = CurrencyContract.TABLE_NAME;
                res.orderBy = CurrencyContract.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this CryptoContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_CURRENCY_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}