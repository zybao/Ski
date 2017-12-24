package com.oab.skyi.common.imageloader;

/**
 * Created by bao on 2017/12/24.
 */

public final class ImageLoader {
    private static ImageLoader instance;

    public static ImageLoader instance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }

        return instance;
    }
}
