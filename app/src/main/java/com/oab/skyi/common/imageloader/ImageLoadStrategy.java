package com.oab.skyi.common.imageloader;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

/**
 * Created by bao on 2017/12/24.
 */

public interface ImageLoadStrategy {
    void load(ImageView imageView, String url);

    void load(ImageView imageView, @IdRes int placeHolder, String url);

    void load(ImageView imageView, @IdRes int placeHolder, @IdRes int errorView, String url);

    void clearDiskCache(Context context);

    void clearMemoryCache(Context context);

    void trimMemory(Context context, int level);

    String getCacheSize(Context context);
}
