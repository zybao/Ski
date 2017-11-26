package com.oab.skyi.common.network;

import android.util.Log;


import io.reactivex.exceptions.Exceptions;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by bao on 2017/11/24.
 */

public abstract class HttpObserver<T> extends DisposableObserver<T> {
    private static final String TAG = "OabSubscriber";

    public HttpObserver() {
    }

    public final void onNext(T t) {
        if (!this.isDisposed()) {
            try {
                this.onSuccess(t);
            } catch (Throwable var11) {
                Exceptions.throwIfFatal(var11);
                this.onFailed(RetrofitException.from(var11));
            } finally {
                try {
                    this.dispose();
                } catch (Throwable var10) {
                    Exceptions.throwIfFatal(var10);
                    this.onFailed(RetrofitException.from(var10));
                }

            }

        }
    }

    protected abstract void onSuccess(T var1);

    public void onComplete() {
    }

    public final void onError(Throwable e) {
        if (!this.isDisposed()) {
            this.isDisposed();
            Log.e(TAG, "===onError ", e);

            try {
                RetrofitException exception;
                if (e instanceof RetrofitException) {
                    exception = (RetrofitException) e;
                } else {
                    exception = RetrofitException.from(e);
                }

                this.onFailed(exception);
            } catch (Exception var6) {
                var6.printStackTrace();
            } finally {
                this.onComplete();
            }

        }
    }

    protected void onFailed(RetrofitException e) {
    }
}
