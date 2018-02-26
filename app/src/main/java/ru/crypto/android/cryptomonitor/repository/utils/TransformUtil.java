package ru.crypto.android.cryptomonitor.repository.utils;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.crypto.android.cryptomonitor.repository.common.network.Transformable;


public final class TransformUtil {

    public static <T, E extends Transformable<T>> T transform(@Nullable E object) {
        return object != null ? object.transform() : null;
    }

    public static <T, E extends Transformable<T>> List<T> transformCollection(Collection<E> src) {
        return mapEmptyIfNull(src, Transformable::transform);
    }

    static <T, V> ArrayList<V> mapEmptyIfNull(Collection<T> collection, Mapper<T, V> mapper) {
        return collection != null ? map(collection, mapper) : new ArrayList<>();
    }

    static <T, V> ArrayList<V> map(Collection<T> collection, Mapper<T, V> mapper) {
        ArrayList<V> result = new ArrayList<>(collection.size());
        for (T origin : collection) {
            result.add(mapper.map(origin));
        }
        return result;
    }
}