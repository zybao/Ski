package com.oab.skyi.common.api;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by bao on 2017/11/25.
 */

public interface SinaApi {
    /**
     *
     * @return 获取当前登录用户及其所关注（授权）用户的最新微博
     */
    @POST("statuses/home_timeline")
    Observable<Object> get();
}
