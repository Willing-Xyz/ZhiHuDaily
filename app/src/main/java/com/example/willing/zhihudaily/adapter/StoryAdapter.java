package com.example.willing.zhihudaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.model.BeforeStoriesEntity;
import com.example.willing.zhihudaily.model.BeforeSubjectEntity;
import com.example.willing.zhihudaily.model.StoryEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.example.willing.zhihudaily.utils.StoryType;
import com.example.willing.zhihudaily.utils.Utils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Willing on 2/28/2016/028.
 */
public class StoryAdapter extends BaseAdapter{

    private static final String BEFORE_NEWS_URL = "http://news.at.zhihu.com/api/4/news/before/";
    private static final String BEFORE_SUBJECT_URL = "http://news.at.zhihu.com/api/4/theme/";

    private List<StoryEntity> mStories;
    private Context mContext;

    private String mDate;
    private int mSubjectId;

    private DisplayImageOptions mOptions;


    public StoryAdapter(Context context, int id)
    {
        mContext = context;
        mStories = new ArrayList<StoryEntity>();
        mDate = Utils.convertDateToString(Calendar.getInstance());
        mSubjectId = id;
        mOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    public StoryAdapter(Context context)
    {
        this(context, -1);
    }

    public void setSubjectId(int id)
    {
        mSubjectId = id;
    }

    @Override
    public int getCount()
    {
        return mStories.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mStories.get(position);
    }

    @Override
    public int getItemViewType(int position) {

        return mStories.get(position).getItemType() == StoryType.DATE_TYPE ? StoryType.DATE_TYPE : StoryType.STORY_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return mStories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent)
    {
        if (getItemViewType(position) != StoryType.DATE_TYPE)
        {
            StoryViewHolder storyViewHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_story, parent, false);
                storyViewHolder = new StoryViewHolder();

                storyViewHolder.mTitle = (TextView) convertView.findViewById(R.id.news_title);
                storyViewHolder.mImage = (ImageView) convertView.findViewById(R.id.news_image);

                convertView.setTag(storyViewHolder);
            }
            else
            {
                storyViewHolder = (StoryViewHolder) convertView.getTag();
            }
            storyViewHolder.mTitle.setText(mStories.get(position).getTitle());
            if (mStories.get(position).getImages() != null) {

                if (storyViewHolder.mImage.getVisibility() == View.GONE)
                {
                    storyViewHolder.mImage.setVisibility(View.VISIBLE);
                }
                ImageLoader.getInstance().displayImage(mStories.get(position).getImages().get(0), storyViewHolder.mImage, mOptions);
            }
            else
            {
                storyViewHolder.mImage.setVisibility(View.GONE);
            }
        }
        else
        {
            DateViewHolder dateViewHolder = null;
            if (convertView == null)
            {
                dateViewHolder = new DateViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_date, parent, false);

                dateViewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_date);

                convertView.setTag(dateViewHolder);
            }
            else
            {
                dateViewHolder = (DateViewHolder) convertView.getTag();
            }
            dateViewHolder.mDate.setText(mStories.get(position).getTitle());
        }

        if (position == getCount() - 1)
        {
            if (mSubjectId != -1) {
                fetchSubjectBeforeStory(parent);
            }
            else {
                fetchBeforeStory(parent);
            }
        }

        return convertView;
    }

    private void fetchSubjectBeforeStory(final ViewGroup parent) {

        OkHttpClient client = HttpUtils.getInstance();

        String url = BEFORE_SUBJECT_URL + mSubjectId + "/before/" + mStories.get(mStories.size() - 1).getId();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();

                final BeforeSubjectEntity news = gson.fromJson(response.body().string(), BeforeSubjectEntity.class);

                parent.post(new Runnable() {
                    @Override
                    public void run() {

                        addStories(news.getStories());
                    }
                });
            }
        });
    }

    public void addStories(List<StoryEntity> stories)
    {
        mStories.addAll(stories);
        notifyDataSetChanged();
    }

    private void fetchBeforeStory(final View parent) {
        OkHttpClient client = HttpUtils.getInstance();

        String url = BEFORE_NEWS_URL + mDate;

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();

                final BeforeStoriesEntity news = gson.fromJson(response.body().string(), BeforeStoriesEntity.class);

                mDate = news.getDate();
                StoryEntity dateItem = new StoryEntity();
                dateItem.setItemType(StoryType.DATE_TYPE);
                dateItem.setTitle(Utils.formatDateString(mDate));
                news.getStories().add(0, dateItem);

                parent.post(new Runnable() {
                    @Override
                    public void run() {

                        addStories(news.getStories());
                    }
                });
            }
        });
    }

    public void clear() {
        mStories.clear();
    }

    public ArrayList<StoryEntity> getStories() {

        return (ArrayList<StoryEntity>) mStories;
    }

    class StoryViewHolder
    {
        TextView mTitle;
        ImageView mImage;
    }

    class DateViewHolder
    {
        TextView mDate;
    }
}
