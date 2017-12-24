package com.oab.skyi.common.network;


import com.oab.skyi.common.domain.DomainUtil;
import com.oab.skyi.common.domain.ServerDomainType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bao on 2017/11/24.
 */

public class RetrofitFatory {
    private static volatile RetrofitFatory instance;
    private Map<ServerDomainType, Object> apis = new HashMap<>();
    private Interceptor[] interceptors;
    private Map<ServerDomainType, Interceptor[]> interceptorMap;
    private Factory converterFactory = GsonConverterFactory.create();
    private boolean enableLog = false;

    public static RetrofitFatory getInstance() {
        if(instance == null) {
            synchronized(RetrofitFatory.class) {
                if(instance == null) {
                    instance = new RetrofitFatory();
                }
            }
        }

        return instance;
    }

    protected RetrofitFatory() {
    }

    public void clearCache() {
        this.apis.clear();
    }

    public void addConverterFactory(Factory factory) {
        this.converterFactory = factory;
    }

    public void setInterceptors(Interceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    public void setInterceptors(ServerDomainType domainType, Interceptor[] interceptors) {
        if(interceptors != null && interceptors.length != 0) {
            if(this.interceptorMap == null) {
                this.interceptorMap = new HashMap<>();
            }

            this.interceptorMap.put(domainType, interceptors);
        }
    }

    public void setEnableLog(boolean enableLog) {
        this.enableLog = enableLog;
    }

    public <T> T create(ServerDomainType serverDomainType, Class<T> tClass, Factory converterFactory) {
        T api = (T) this.apis.get(serverDomainType);
        if(api == null) {
            Retrofit retrofit = (new RetrofitBuilder())
                    .withDomain(DomainUtil.getServerDomain(serverDomainType))
                    .withDebug(this.enableLog)
                    .withInterceptors(this.getInterceptors(serverDomainType))
                    .withConvertFactory(converterFactory)
                    .build();
            api = retrofit.create(tClass);
            this.apis.put(serverDomainType, api);
        }

        return api;
    }

    private List<Interceptor> getInterceptors(ServerDomainType serverDomainType) {
        List<Interceptor> res = new ArrayList<>();
        if(this.interceptors != null && this.interceptors.length != 0) {
            res.addAll(Arrays.asList(this.interceptors));
        }

        if(this.interceptorMap != null) {
            Interceptor[] interceptors = (Interceptor[])this.interceptorMap.get(serverDomainType);
            if(interceptors != null && interceptors.length != 0) {
                res.addAll(Arrays.asList(interceptors));
            }
        }

        return res;
    }

    public <T> T create(ServerDomainType serverDomainType, Class<T> tClass) {
        return this.create(serverDomainType, tClass, this.converterFactory);
    }
}
