package ru.crypto.android.cryptomonitor.provider.base;

import java.util.Date;
import java.util.HashMap;

import android.database.Cursor;
import android.database.CursorWrapper;

public abstract class AbstractCursor extends CursorWrapper {

    private final HashMap<String, Integer> columnIndexes;

    public AbstractCursor(Cursor cursor) {
        super(cursor);
        columnIndexes = new HashMap<>(cursor.getColumnCount() * 4 / 3, .75f);
    }

    public abstract long get_Id();

    protected String getStringOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getString(index);
    }

    protected Long getLongOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getLong(index);
    }

    public Integer getIntegerOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getInt(index);
    }

    public Float getFloatOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getFloat(index);
    }

    public Double getDoubleOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getDouble(index);
    }

    public Boolean getBooleanOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getInt(index) != 0;
    }

    public Date getDateOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return new Date(getLong(index));
    }

    public byte[] getBlobOrNull(String colName) {
        int index = getCachedColumnIndexOrThrow(colName);
        if (isNull(index)) return null;
        return getBlob(index);
    }

    private int getCachedColumnIndexOrThrow(String colName) {
        Integer index = columnIndexes.get(colName);
        if (index == null) {
            index = getColumnIndexOrThrow(colName);
            columnIndexes.put(colName, index);
        }
        return index;
    }
}