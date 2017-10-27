package com.oab.skyi.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TypeAdapters;

/**
 * @author zhiyong.bao
 * @date 2017/10/27
 */

public class GsonUtils {
    private static Gson instance;

    public static Gson gson() {
        if (instance == null) {
            synchronized (GsonUtils.class) {
                if (instance == null) {
                    instance = new GsonBuilder()
                            .registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, new IntTypeAdapter()))
                            .registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, new LongTypeAdapter()))
                            .registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, new FloatTypeAdapter()))
                            .registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, new DoubleTypeAdapter()))
                            .registerTypeAdapterFactory(TypeAdapters.newFactory(String.class, new StringTypeAdapter()))
                            .create();
                }
            }
        }

        return instance;
    }
}
