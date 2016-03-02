package com.example.willing.zhihudaily;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.IOException;

/**
 * Created by Willing on 2/28/2016/028.
 */
public class ZhiHuApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();
    }

    // 初始化、配置 ImageLoader
    private void initImageLoader()
    {
        ImageLoaderConfiguration configuration = null;
        try {
            configuration = new ImageLoaderConfiguration.Builder(this)
                    .diskCache(new LruDiskCache(StorageUtils.getCacheDirectory(this), new Md5FileNameGenerator(), 1024 * 1024 * 100))
                    .memoryCache(new LRULimitedMemoryCache(0))
                    .memoryCacheSizePercentage(10)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageLoader.getInstance().init(configuration);
    }


}
