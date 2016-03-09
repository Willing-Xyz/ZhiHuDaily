package com.example.willing.zhihudaily.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.activity.ContentActivity;
import com.example.willing.zhihudaily.adapter.StoryAdapter;
import com.example.willing.zhihudaily.model.LastestStoriesEntity;
import com.example.willing.zhihudaily.model.StoryEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.example.willing.zhihudaily.utils.StoryType;
import com.example.willing.zhihudaily.view.CarouselView;
import com.google.gson.Gson;
import com.viewpagerindicator.PageIndicator;

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
    private OnPageChangedListener mPageChangedListener;
    private CarouselView mCarouselView;
    private PageIndicator mIndicator;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mPageChangedListener = (OnPageChangedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getSimpleName() + " must implement OnPageChangedListener");
        }

    }

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

        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainFragment.this.getActivity(), ContentActivity.class);

                StoryEntity entity = (StoryEntity) parent.getItemAtPosition(position);
                if (entity.getItemType() == StoryType.DATE_TYPE) {
                    return;
                }
                intent.putExtra(ContentActivity.LIST_INDEX, position - 1);
                intent.putExtra(ContentActivity.STORY_LIST, mStoryAdapter.getStories());
                startActivity(intent);
            }
        });

        mNewsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (visibleItemCount <= 1 || firstVisibleItem == 0) {
                    return;
                }

                StoryEntity entity = (StoryEntity) view.getItemAtPosition(firstVisibleItem);
                if (entity.getItemType() == StoryType.DATE_TYPE) {
                    if (firstVisibleItem == 1) {
                        mPageChangedListener.onPageChanged(entity.getTitle());
                    } else {
                        mPageChangedListener.onPageChanged(entity.getTitle());
                    }
                } else {
                    int i = 0;
                    for (i = firstVisibleItem - 1; i >= 1; --i) {
                        entity = (StoryEntity) view.getItemAtPosition(i);
                        if (entity.getItemType() == StoryType.DATE_TYPE) {
                            break;
                        }

                    }
                    if (i == 1) {
                        mPageChangedListener.onPageChanged(entity.getTitle());
                        return;
                    }


                    mPageChangedListener.onPageChanged(entity.getTitle());
                }
            }
        });

        mCarouselView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_DRAGGING || state == ViewPager.SCROLL_STATE_SETTLING)
                {
                    mRefresh.setEnabled(false);
                    mCarouselView.setInScroll(true);
                }
                else
                {
                    mRefresh.setEnabled(true);
                    mCarouselView.setInScroll(false);
                }
            }
        });


    }

    private void initView(View view) {

        mNewsListView = (ListView) view.findViewById(R.id.news);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.main_headview, mNewsListView, false);
        mCarouselView = (CarouselView) layout.findViewById(R.id.carousel);
        mIndicator = (PageIndicator) layout.findViewById(R.id.indicator);


        mNewsListView.addHeaderView(layout);

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



                        mCarouselView.setAdapter(news.getTop_stories(), mStoryAdapter.getStories());
                        mIndicator.setViewPager(mCarouselView);

                    }
                });
            }
        });


    }

    public static interface OnPageChangedListener
    {
        void onPageChanged(String title);
    }
}
