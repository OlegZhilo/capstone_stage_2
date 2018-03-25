package ru.crypto.android.cryptomonitor.provider.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public abstract class AbstractSelection<T extends AbstractSelection<?>> {

    private static final String EQ = "=?";
    private static final String PAREN_OPEN = "(";
    private static final String PAREN_CLOSE = ")";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String IS_NULL = " IS NULL";
    private static final String IS_NOT_NULL = " IS NOT NULL";
    private static final String IN = " IN (";
    private static final String NOT_IN = " NOT IN (";
    private static final String COMMA = ",";
    private static final String GT = ">?";
    private static final String LT = "<?";
    private static final String GT_EQ = ">=?";
    private static final String LT_EQ = "<=?";
    private static final String NOT_EQ = "<>?";
    private static final String LIKE = " LIKE ?";
    private static final String CONTAINS = " LIKE '%' || ? || '%'";
    private static final String STARTS = " LIKE ? || '%'";
    private static final String ENDS = " LIKE '%' || ?";
    private static final String COUNT = "COUNT(*)";
    public static final String ASC = " ASC";

    private final StringBuilder selection = new StringBuilder();
    private final List<String> selectionArgs = new ArrayList<>(5);
    private final StringBuilder orderBy = new StringBuilder();

    private Boolean notify;
    private String groupBy;
    private String having;
    private Integer limit;

    protected abstract Uri baseUri();

    protected void addEquals(String column, Object[] value) {
        selection.append(column);

        if (value == null) {
            selection.append(IS_NULL);
        } else if (value.length > 1) {
            selection.append(IN);
            for (int i = 0; i < value.length; i++) {
                selection.append("?");
                if (i < value.length - 1) {
                    selection.append(COMMA);
                }
                selectionArgs.add(valueOf(value[i]));
            }
            selection.append(PAREN_CLOSE);
        } else {
            if (value[0] == null) {
                selection.append(IS_NULL);
            } else {
                selection.append(EQ);
                selectionArgs.add(valueOf(value[0]));
            }
        }
    }

    protected void addNotEquals(String column, Object[] value) {
        selection.append(column);

        if (value == null) {
            selection.append(IS_NOT_NULL);
        } else if (value.length > 1) {
            selection.append(NOT_IN);
            for (int i = 0; i < value.length; i++) {
                selection.append("?");
                if (i < value.length - 1) {
                    selection.append(COMMA);
                }
                selectionArgs.add(valueOf(value[i]));
            }
            selection.append(PAREN_CLOSE);
        } else {
            if (value[0] == null) {
                selection.append(IS_NOT_NULL);
            } else {
                selection.append(NOT_EQ);
                selectionArgs.add(valueOf(value[0]));
            }
        }
    }

    protected void addLike(String column, String[] values) {
        selection.append(PAREN_OPEN);
        for (int i = 0; i < values.length; i++) {
            selection.append(column);
            selection.append(LIKE);
            selectionArgs.add(values[i]);
            if (i < values.length - 1) {
                selection.append(OR);
            }
        }
        selection.append(PAREN_CLOSE);
    }

    protected void addContains(String column, String[] values) {
        selection.append(PAREN_OPEN);
        for (int i = 0; i < values.length; i++) {
            selection.append(column);
            selection.append(CONTAINS);
            selectionArgs.add(values[i]);
            if (i < values.length - 1) {
                selection.append(OR);
            }
        }
        selection.append(PAREN_CLOSE);
    }

    protected void addStartsWith(String column, String[] values) {
        selection.append(PAREN_OPEN);
        for (int i = 0; i < values.length; i++) {
            selection.append(column);
            selection.append(STARTS);
            selectionArgs.add(values[i]);
            if (i < values.length - 1) {
                selection.append(OR);
            }
        }
        selection.append(PAREN_CLOSE);
    }

    protected void addEndsWith(String column, String[] values) {
        selection.append(PAREN_OPEN);
        for (int i = 0; i < values.length; i++) {
            selection.append(column);
            selection.append(ENDS);
            selectionArgs.add(values[i]);
            if (i < values.length - 1) {
                selection.append(OR);
            }
        }
        selection.append(PAREN_CLOSE);
    }

    protected void addGreaterThan(String column, Object value) {
        selection.append(column);
        selection.append(GT);
        selectionArgs.add(valueOf(value));
    }

    protected void addGreaterThanOrEquals(String column, Object value) {
        selection.append(column);
        selection.append(GT_EQ);
        selectionArgs.add(valueOf(value));
    }

    protected void addLessThan(String column, Object value) {
        selection.append(column);
        selection.append(LT);
        selectionArgs.add(valueOf(value));
    }

    protected void addLessThanOrEquals(String column, Object value) {
        selection.append(column);
        selection.append(LT_EQ);
        selectionArgs.add(valueOf(value));
    }

    protected Object[] toObjectArray(int... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    protected Object[] toObjectArray(long... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    protected Object[] toObjectArray(float... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    protected Object[] toObjectArray(double... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    protected Object[] toObjectArray(Boolean value) {
        return new Object[] {value};
    }

    protected String order() {
        return orderBy.length() > 0 ? orderBy.toString() : null;
    }

    protected Uri uri() {
        Uri uri = baseUri();
        if (notify != null) uri = BaseContentProvider.notify(uri, notify);
        if (groupBy != null) uri = BaseContentProvider.groupBy(uri, groupBy);
        if (having != null) uri = BaseContentProvider.having(uri, having);
        if (limit != null) uri = BaseContentProvider.limit(uri, String.valueOf(limit));
        return uri;
    }

    protected T orderBy(String order, boolean desc) {
        if (orderBy.length() > 0) orderBy.append(COMMA);
        orderBy.append(order);
        if (desc) orderBy.append(ASC);
        return (T) this;
    }

    public T addRaw(String raw, Object... args) {
        selection.append(" ");
        selection.append(raw);
        selection.append(" ");
        for (Object arg : args) {
            selectionArgs.add(valueOf(arg));
        }
        return (T) this;
    }

    public T openParen() {
        selection.append(PAREN_OPEN);
        return (T) this;
    }

    public T closeParen() {
        selection.append(PAREN_CLOSE);
        return (T) this;
    }

    public T and() {
        selection.append(AND);
        return (T) this;
    }

    public T or() {
        selection.append(OR);
        return (T) this;
    }

    public String sel() {
        return selection.toString();
    }

    public String[] args() {
        int size = selectionArgs.size();
        if (size == 0) return null;
        return selectionArgs.toArray(new String[size]);
    }

    public int delete(ContentResolver contentResolver) {
        return contentResolver.delete(uri(), sel(), args());
    }

    public int delete(Context context) {
        return context.getContentResolver().delete(uri(), sel(), args());
    }

    public T notify(boolean notify) {
        this.notify = notify;
        return (T) this;
    }

    public T groupBy(String groupBy) {
        this.groupBy = groupBy;
        return (T) this;
    }

    public T having(String having) {
        this.having = having;
        return (T) this;
    }

    public T limit(int limit) {
        this.limit = limit;
        return (T) this;
    }

    public T orderBy(String order) {
        return orderBy(order, false);
    }

    public T orderBy(String... orders) {
        for (String order : orders) {
            orderBy(order, false);
        }
        return (T) this;
    }

    public int count(Context context) {
        return count(context.getContentResolver());
    }

    private int count(ContentResolver resolver) {
        Cursor cursor = resolver.query(uri(), new String[] {COUNT}, sel(), args(), null);
        if (cursor == null) return 0;
        try {
            return cursor.moveToFirst() ? cursor.getInt(0) : 0;
        } finally {
            cursor.close();
        }
    }

    private String valueOf(Object obj) {
        if (obj instanceof Date) {
            return String.valueOf(((Date) obj).getTime());
        } else if (obj instanceof Boolean) {
            return (Boolean) obj ? "1" : "0";
        } else if (obj instanceof Enum) {
            return String.valueOf(((Enum<?>) obj).ordinal());
        }
        return String.valueOf(obj);
    }

}