package com.example.willing.zhihudaily.utils;

import okhttp3.OkHttpClient;

/**
 * Created by Willing on 2/28/2016/028.
 */
public class HttpUtils
{
    private static OkHttpClient client;

    public static OkHttpClient getInstance()
    {
        if (client == null)
        {
            client = new OkHttpClient();
        }
        return client;
    }

}
