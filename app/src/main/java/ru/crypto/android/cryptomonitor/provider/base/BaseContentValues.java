package ru.crypto.android.cryptomonitor.provider.base;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

public abstract class BaseContentValues<T extends BaseContentValues<?>> {

    protected final ContentValues contentValues = new ContentValues();

    private Boolean notify;

    protected abstract Uri baseUri();

    protected Uri uri() {
        Uri uri = baseUri();
        if (notify != null) uri = BaseContentProvider.notify(uri, notify);
        return uri;
    }

    public T notify(boolean notify) {
        this.notify = notify;
        return (T) this;
    }

    public ContentValues values() {
        return contentValues;
    }

    public Uri insert(ContentResolver contentResolver) {
        return contentResolver.insert(uri(), values());
    }

}