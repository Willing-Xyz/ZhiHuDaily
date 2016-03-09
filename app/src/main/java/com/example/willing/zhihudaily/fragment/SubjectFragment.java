package com.example.willing.zhihudaily.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.activity.ContentActivity;
import com.example.willing.zhihudaily.adapter.StoryAdapter;
import com.example.willing.zhihudaily.model.StoryEntity;
import com.example.willing.zhihudaily.model.SubjectStoriesEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class SubjectFragment extends BaseFragment
{
    private static final String LASTEST_NEWS_URL = "http://news-at.zhihu.com/api/4/news/latest";
    private static final String SUBJECT_CONTENT_URL = "http://news-at.zhihu.com/api/4/theme/";

    private static final String SUBJECT_ID = "id";


    private ListView mNewsListView;
    private StoryAdapter mStoryAdapter;
    private SwipeRefreshLayout mRefresh;
    private int mSubjectId;
    private ImageView mHeadImage;
    private TextView mHeadTitle;


    public static SubjectFragment newInstance(int id)
    {
        Bundle args = new Bundle();
        args.putInt(SUBJECT_ID, id);

        SubjectFragment frag = new SubjectFragment();
        frag.setArguments(args);

        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        mSubjectId = getArguments().getInt(SUBJECT_ID);

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

                Intent intent = new Intent(SubjectFragment.this.getActivity(), ContentActivity.class);

                StoryEntity entity = (StoryEntity) parent.getItemAtPosition(position);
                intent.putExtra(ContentActivity.LIST_INDEX, position - 1);
                intent.putExtra(ContentActivity.STORY_LIST, mStoryAdapter.getStories());
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {

        mNewsListView = (ListView) view.findViewById(R.id.news);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.subject_headview, mNewsListView, false);

        mHeadImage = (ImageView) layout.findViewById(R.id.image);
        mHeadTitle = (TextView) layout.findViewById(R.id.title);


        mNewsListView.addHeaderView(layout);
    }

    private void initData(View view) {

        mStoryAdapter = new StoryAdapter(getActivity(), mSubjectId);
        mNewsListView.setAdapter(mStoryAdapter);

        initLastestNews(true);
    }

    public void initLastestNews(final boolean cleanBefore) {
        OkHttpClient client = HttpUtils.getInstance();

        String url = SUBJECT_CONTENT_URL + mSubjectId;

        Log.i("main_headview", url);

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();
                final SubjectStoriesEntity news = gson.fromJson(response.body().string(), SubjectStoriesEntity.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (cleanBefore) {
                            mStoryAdapter.clear();
                        }
                        if (news.getStories() != null) {
                            mStoryAdapter.addStories(news.getStories());
                        }
                        ImageLoader.getInstance().displayImage(news.getBackground(), mHeadImage);
                        mHeadTitle.setText(news.getDescription());

                    }
                });
            }
        });


    }

}
