package com.example.willing.zhihudaily.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);

        initView();
        setupListener();
        initData();


    }

    private void setupListener() {

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationIcon(R.drawable.back);
    }


}
