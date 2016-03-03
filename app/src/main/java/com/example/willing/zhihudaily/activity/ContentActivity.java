package com.example.willing.zhihudaily.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.adapter.ContentViewPagerAdapter;
import com.example.willing.zhihudaily.model.StoryEntity;
import com.example.willing.zhihudaily.utils.StoryType;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class ContentActivity extends BaseActivity
{
    public static final String CONTENT_URL = "http://news-at.zhihu.com/api/4/news/";
    public static final String LIST_INDEX = "story_id";
    public static final String STORY_LIST = "story_list";

    private ViewPager mViewPager;
    private ContentViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);

        initView();
        setupListener();
        initData();

//        fetchContent();
    }

    private void setupListener() {

    }

    private void initData() {

        List<StoryEntity> stories = (List<StoryEntity>) getIntent().getSerializableExtra(STORY_LIST);
        int index = getIntent().getIntExtra(LIST_INDEX, -1);
        StoryEntity entity = null;
        int i = 0;
        for (Iterator<StoryEntity> ite = stories.iterator(); ite.hasNext(); i++)
        {
            entity = ite.next();
            if (entity.getItemType() == StoryType.DATE_TYPE)
            {
                if (i <= index)
                {
                    --index;
                }
                ite.remove();
            }

        }
        mViewPagerAdapter = new ContentViewPagerAdapter(this,
                stories
        );
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(index);
    }

    private void initView()
    {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(1);

    }

//    private void fetchContent() {
//
//        OkHttpClient client = HttpUtils.getInstance();
//
//        mContent = (WebView) findViewById(R.id.content);
//        mContent.getSettings().setJavaScriptEnabled(true);
//
//        int id = getIntent().getIntExtra(LIST_INDEX, 0);
//        Request request = new Request.Builder().url(CONTENT_URL + id).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                Gson gson = new Gson();
//                StoryContentEntity content = gson.fromJson(response.body().string(), StoryContentEntity.class);
//
//                String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
//                String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
//                html = html.replace("<div class=\"img-place-holder\">", "");
//
//                final String finalHtml = html;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        mContent.loadDataWithBaseURL("x-data://base", finalHtml, "text/html", "UTF-8", null);
//                    }
//                });
//            }
//        });
//    }
}
