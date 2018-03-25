package ru.crypto.android.cryptomonitor.provider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.crypto.android.cryptomonitor.provider.currency.CurrencyContract;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE_NAME = "currencies.db";
    private static final int DATABASE_VERSION = 1;
    private static DbOpenHelper instance;

    private static final String SQL_CREATE_TABLE_CURRENCY = "CREATE TABLE IF NOT EXISTS "
            + CurrencyContract.TABLE_NAME + " ( "
            + CurrencyContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CurrencyContract.ID + " TEXT, "
            + CurrencyContract.NAME + " TEXT, "
            + CurrencyContract.VOLUME24HUSD + " TEXT, "
            + CurrencyContract.PERCENTCHANGE1H + " TEXT, "
            + CurrencyContract.PERCENTCHANGE24H + " TEXT, "
            + CurrencyContract.PERCENTCHANGE7D + " TEXT, "
            + CurrencyContract.PRICEUSD + " TEXT, "
            + CurrencyContract.RANK + " TEXT, "
            + CurrencyContract.SYMBOL + " TEXT "
            + ", CONSTRAINT unique_name UNIQUE (id, name, symbol) ON CONFLICT REPLACE"
            + " );";


    static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = newInstance(context.getApplicationContext());
        }
        return instance;
    }

    private static DbOpenHelper newInstance(Context context) {
        return new DbOpenHelper(context, new DefaultDatabaseErrorHandler());
    }
    
    private DbOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private DbOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CURRENCY);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }
}
