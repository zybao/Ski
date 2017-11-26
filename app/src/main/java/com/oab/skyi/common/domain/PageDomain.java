package com.oab.skyi.common.domain;

import android.content.Context;


import java.util.Map;

/**
 * Created by bao on 2017/11/24.
 */

public class PageDomain {
    private static Context context;
    private static PageDomainFactory domainFactory;
    private static Map<PageDomainType, String> DOMAINS;

    public PageDomain() {
    }

    static void initialize(Context c, PageDomainFactory factory) {
        context = c;
        domainFactory = factory;
    }

    static void setupDomain(boolean isDebug) {
        DOMAINS = domainFactory.getDomain(context, isDebug);
    }

    static String get(PageDomainType domainType) {
        if(DOMAINS == null) {
            DOMAINS = domainFactory.getDomain(context, DomainUtil.isDebug);
        }

        return (String)DOMAINS.get(domainType);
    }

    static void clear() {
        DOMAINS = null;
    }
}
