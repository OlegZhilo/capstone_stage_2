package ru.crypto.android.cryptomonitor.repository;

public enum Period {
    DAY(23),
    WEEK(6),
    MONTH(29),
    YEAR(365);

    private int count;

    Period(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
