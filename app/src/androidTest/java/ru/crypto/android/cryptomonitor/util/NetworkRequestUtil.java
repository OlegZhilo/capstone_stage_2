package ru.crypto.android.cryptomonitor.util;


import io.reactivex.Observable;

public class NetworkRequestUtil {

    public static <T> T makeRequest(Observable<T> request) {
        return request.toList()
                .blockingGet()
                .get(0);
    }

}