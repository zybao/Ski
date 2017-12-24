package com.oab.skyi.common.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by bao on 2017/12/24.
 */

public class GlideStrategy implements ImageLoadStrategy {
    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, int placeHolder, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, int placeHolder, int errorView, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }

    @Override
    public void clearDiskCache(Context context) {

    }

    @Override
    public void clearMemoryCache(Context context) {

    }

    @Override
    public void trimMemory(Context context, int level) {

    }

    @Override
    public String getCacheSize(Context context) {
        return null;
    }
}
