package com.oab.skyi.common;

/**
 * Created by bao on 2017/11/26.
 */

public abstract class Singleton<T> {
    private T instance;

    protected abstract T create();

    public T getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = create();
                }
            }
        }

        return instance;
    }
}
