package com.oab.skyi.common.domain;

import android.content.Context;

import java.util.Map;

/**
 * Created by bao on 2017/11/24.
 */

public class ServerDomain {
    private static Context context;
    private static ServerDomainFactory domainFactory;
    private static Map<ServerDomainType, String> DOMAINS;

    public ServerDomain() {
    }

    static void initialize(Context c, ServerDomainFactory factory) {
        context = c;
        domainFactory = factory;
    }

    static void setupDomain(boolean isDebug) {
        DOMAINS = domainFactory.getDomain(context, isDebug);
    }

    static String get(ServerDomainType domainType) {
        if(DOMAINS == null) {
            DOMAINS = domainFactory.getDomain(context, DomainUtil.isDebug);
        }

        return (String)DOMAINS.get(domainType);
    }

    static void clear() {
        DOMAINS = null;
    }
}
