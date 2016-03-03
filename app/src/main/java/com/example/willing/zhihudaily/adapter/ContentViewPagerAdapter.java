package com.example.willing.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.willing.zhihudaily.model.StoryContentEntity;
import com.example.willing.zhihudaily.model.StoryEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Willing on 3/3/2016/003.
 */
public class ContentViewPagerAdapter extends PagerAdapter
{
    public static final String CONTENT_URL = "http://news-at.zhihu.com/api/4/news/";

    private Context mContext;
    private List<StoryEntity> mStories;

    public ContentViewPagerAdapter(Context context, List<StoryEntity> stories)
    {
        mContext = context;
        mStories = stories;


    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {



        final WebView webView = new WebView(mContext);
        webView.getSettings().setJavaScriptEnabled(true);

        OkHttpClient client = HttpUtils.getInstance();
        int id = mStories.get(position).getId();
        Request request = new Request.Builder().url(CONTENT_URL + id).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();
                StoryContentEntity content = gson.fromJson(response.body().string(), StoryContentEntity.class);

                String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
                String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
                html = html.replace("<div class=\"img-place-holder\">", "");

                final String finalHtml = html;
                container.post(new Runnable() {
                    @Override
                    public void run() {

                        webView.loadDataWithBaseURL("x-data://base", finalHtml, "text/html", "UTF-8", null);
                    }
                });
            }
        });


        container.addView(webView);

        return webView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mStories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }


}
