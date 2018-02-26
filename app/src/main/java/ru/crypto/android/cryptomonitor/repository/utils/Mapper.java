package ru.crypto.android.cryptomonitor.repository.utils;

public interface Mapper<K, V> {
    V map(K key);
}