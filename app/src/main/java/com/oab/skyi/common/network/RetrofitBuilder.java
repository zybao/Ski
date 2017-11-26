package com.oab.skyi.common.network;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ban 2017/11/24.
 */

public class RetrofitBuilder {
    private String domain;
    private boolean debug;
    private Converter.Factory convertFactory;
    private CallAdapter.Factory callFactory;
    private long connectTimeout = 30L;
    private long readTimeout = 20L;
    private long writeTimeout;
    private volatile List<Interceptor> interceptors;

    public RetrofitBuilder() {
    }

    public RetrofitBuilder withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public RetrofitBuilder withDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public RetrofitBuilder withConvertFactory(Converter.Factory convertFactory) {
        this.convertFactory = convertFactory;
        return this;
    }

    public RetrofitBuilder withCallFactory(retrofit2.CallAdapter.Factory callFactory) {
        this.callFactory = callFactory;
        return this;
    }

    public RetrofitBuilder withConnectionTimeout(long connectionTimeout) {
        this.connectTimeout = connectionTimeout;
        return this;
    }

    public RetrofitBuilder withReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public RetrofitBuilder withWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public RetrofitBuilder addInterceptor(Interceptor interceptor) {
        if(this.interceptors == null) {
            synchronized(RetrofitBuilder.class) {
                if(this.interceptors == null) {
                    this.interceptors = new ArrayList<>();
                }
            }
        }

        this.interceptors.add(interceptor);
        return this;
    }

    public RetrofitBuilder withInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public Retrofit build() {
        if(this.convertFactory == null) {
            this.convertFactory = GsonConverterFactory.create();
        }

        if(this.callFactory == null) {
            this.callFactory = RxErrorCallAdapterFactory.createWithScheduler(Schedulers.io());
        }

        return new Retrofit.Builder()
                .baseUrl(this.domain)
                .addConverterFactory(this.convertFactory)
                .addCallAdapterFactory(this.callFactory)
                .client(this.createHttpClient())
                .build();
    }

    private OkHttpClient createHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(this.connectTimeout, TimeUnit.SECONDS)
                .readTimeout(this.readTimeout, TimeUnit.SECONDS);
        if(this.writeTimeout > 0) {
            builder.writeTimeout(this.writeTimeout, TimeUnit.SECONDS);
        }

        if(this.interceptors != null) {
            Iterator var2 = this.interceptors.iterator();

            while(var2.hasNext()) {
                Interceptor interceptor = (Interceptor)var2.next();
                builder.addInterceptor(interceptor);
            }
        }

        if(this.debug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }
}
