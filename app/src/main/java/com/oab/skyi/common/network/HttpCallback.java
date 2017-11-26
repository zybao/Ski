package com.oab.skyi.common.network;


import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by bao on 2017/11/24.
 */

public abstract class HttpCallback<T> implements Callback<T> {
    public HttpCallback() {
    }

    public final void onFailure(Call<T> call, Throwable t) {
        try {
            this.onError(call, RetrofitException.from(t));
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    protected void onError(Call<T> call, RetrofitException e) {
        e.printStackTrace();
    }
}
