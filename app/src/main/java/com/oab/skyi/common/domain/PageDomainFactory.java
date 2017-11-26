package com.oab.skyi.common.domain;

import android.content.Context;

import java.util.Map;

/**
 * Created by bao on 2017/11/24.
 */

public abstract class PageDomainFactory {
    public PageDomainFactory() {
    }

    public abstract Map<PageDomainType, String> getDomain(Context var1, boolean var2);
}
