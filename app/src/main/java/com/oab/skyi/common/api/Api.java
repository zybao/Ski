package com.oab.skyi.common.api;

import com.oab.skyi.BuildConfig;
import com.oab.skyi.common.Singleton;
import com.oab.skyi.common.domain.ServerDomainType;
import com.oab.skyi.common.network.RetrofitFatory;

/**
 * Created by bao on 2017/11/26.
 */

public class Api {
    private static RetrofitFatory factory = RetrofitFatory.getInstance();

    static {
        // TODO
        factory.setEnableLog(BuildConfig.DEBUG);
    }

    private SinaApi sinaApi;

    public SinaApi getSinaApi() {
        sinaApi = new Singleton<SinaApi>() {
            @Override
            protected SinaApi create() {
                return factory.create(ServerDomainType.SINA, SinaApi.class);
            }
        }.getInstance();

        return sinaApi;
    }

    public void clear() {
        factory.clearCache();
        sinaApi = null;
    }
}
