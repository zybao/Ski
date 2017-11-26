package com.oab.skyi.common.domain;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bao on 2017/11/25.
 */

public class ServerDomainFactoryImpl extends ServerDomainFactory {
    private static Map<ServerDomainType, String> domain = new HashMap<ServerDomainType, String>() {
        {
            put(ServerDomainType.SINA, "https://api.weibo.com/2/");
        }
    };

    @Override
    public Map<ServerDomainType, String> getDomain(Context var1, boolean isDebug) {
        return domain;
    }
}
