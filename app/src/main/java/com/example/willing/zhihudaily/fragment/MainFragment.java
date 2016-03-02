package com.example.willing.zhihudaily.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.adapter.StoryAdapter;
import com.example.willing.zhihudaily.model.LastestStoriesEntity;
import com.example.willing.zhihudaily.model.StoryEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.example.willing.zhihudaily.utils.StoryType;
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
public class MainFragment extends BaseFragment {

    private static final String LASTEST_NEWS_URL = "http://news-at.zhihu.com/api/4/news/latest";


    private ListView mNewsListView;
    private StoryAdapter mStoryAdapter;
    private SwipeRefreshLayout mRefresh;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        initView(view);
        setupListener(view);
        initData(view);

        return view;
    }



    private void setupListener(View view) {

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initLastestNews(true);

                mRefresh.setRefreshing(false);
            }
        });


    }

    private void initView(View view) {

        mNewsListView = (ListView) view.findViewById(R.id.news);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

    }

    private void initData(View view) {

        mStoryAdapter = new StoryAdapter(getActivity());
        mNewsListView.setAdapter(mStoryAdapter);

        initLastestNews(true);
    }

    public void initLastestNews(final boolean cleanBefore) {
        OkHttpClient client = HttpUtils.getInstance();

        Request request = new Request.Builder().url(LASTEST_NEWS_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();
                final LastestStoriesEntity news = gson.fromJson(response.body().string(), LastestStoriesEntity.class);

                StoryEntity dateItem = new StoryEntity();
                dateItem.setItemType(StoryType.DATE_TYPE);
                dateItem.setTitle("今日热闻");
                news.getStories().add(0, dateItem);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (cleanBefore) {
                            mStoryAdapter.clear();
                        }
                        mStoryAdapter.addStories(news.getStories());
                    }
                });
            }
        });


    }
}
