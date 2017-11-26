package com.oab.skyi.common.domain;

import android.content.Context;
import android.support.annotation.NonNull;


/**
 * Created by bao on 2017/11/24.
 */

public class DomainUtil {
    public static boolean isDebug = false;

    private DomainUtil() {
        throw new AssertionError("No instance of " + this.getClass().getSimpleName());
    }

    public static void setIsDebug(boolean isDebugable) {
        isDebug = isDebugable;
        PageDomain.clear();
        ServerDomain.clear();
    }

    public static void initialize(@NonNull Context context, PageDomainFactory pageDomainFactory, ServerDomainFactory serverDomainFactory) {
        PageDomain.initialize(context.getApplicationContext(), pageDomainFactory);
        ServerDomain.initialize(context.getApplicationContext(), serverDomainFactory);
    }

    public static void setServer() {
        PageDomain.setupDomain(isDebug);
        ServerDomain.setupDomain(isDebug);
    }

    public static String getServerDomain(ServerDomainType domainType) {
        return ServerDomain.get(domainType);
    }

    public static String getPageDomain(PageDomainType domainType) {
        return PageDomain.get(domainType);
    }
}
