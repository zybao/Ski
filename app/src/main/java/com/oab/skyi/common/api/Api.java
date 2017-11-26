package com.oab.skyi.common.api;

import com.oab.skyi.common.domain.ServerDomainType;
import com.oab.skyi.common.network.ApiFactory;

/**
 * Created by bao on 2017/11/26.
 */

public class Api extends ApiFactory {
    SinaApi sinaApi;

    public SinaApi getSinaApi() {
        if (sinaApi == null) {
            synchronized (Api.class) {
                if (sinaApi == null) {
                    sinaApi = create(ServerDomainType.SINA, SinaApi.class);
                }
            }
        }

        return sinaApi;
    }

    public void clear() {
        sinaApi = null;
    }
}
