package com.example.willing.zhihudaily.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.model.StoryContentEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class ContentActivity extends BaseActivity
{
    public static final String CONTENT_URL = "http://news-at.zhihu.com/api/4/news/";
    public static final String STORY_ID = "story_id";

    private WebView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);

        initView();

        fetchContent();
    }

    private void initView() {
        mContent = (WebView) findViewById(R.id.content);
    }

    private void fetchContent() {

        OkHttpClient client = HttpUtils.getInstance();

        mContent = (WebView) findViewById(R.id.content);
        mContent.getSettings().setJavaScriptEnabled(true);

        int id = getIntent().getIntExtra(STORY_ID, 0);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mContent.loadDataWithBaseURL("x-data://base", finalHtml, "text/html", "UTF-8", null);
                    }
                });
            }
        });
    }
}
