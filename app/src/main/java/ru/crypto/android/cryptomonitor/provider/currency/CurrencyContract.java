package ru.crypto.android.cryptomonitor.provider.currency;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.crypto.android.cryptomonitor.provider.CryptoContentProvider;
import ru.crypto.android.cryptomonitor.provider.base.AbstractSelection;

/**
 * Currency contract
 */
public class CurrencyContract implements BaseColumns {

    public static final String TABLE_NAME = "currency";

    public static final Uri CONTENT_URI = Uri.parse(CryptoContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String VOLUME24HUSD = "volume24hUsd";

    public static final String PERCENTCHANGE1H = "percentChange1H";

    public static final String PERCENTCHANGE24H = "percentChange24H";

    public static final String PERCENTCHANGE7D = "percentChange7D";

    public static final String PRICEUSD = "priceUsd";

    public static final String RANK = "rank";

    public static final String SYMBOL = "symbol";

    public static final String DEFAULT_ORDER = TABLE_NAME + "." + RANK + AbstractSelection.ASC;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ID,
            NAME,
            VOLUME24HUSD,
            PERCENTCHANGE1H,
            PERCENTCHANGE24H,
            PERCENTCHANGE7D,
            PRICEUSD,
            RANK,
            SYMBOL
    };
}