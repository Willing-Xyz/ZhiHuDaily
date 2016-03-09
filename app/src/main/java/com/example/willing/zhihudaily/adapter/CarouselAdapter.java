package com.example.willing.zhihudaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.activity.ContentActivity;
import com.example.willing.zhihudaily.model.StoryEntity;
import com.example.willing.zhihudaily.model.TopStoriesEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Willing on 2016/3/9.
 */
public class CarouselAdapter extends PagerAdapter{

    public static final String CONTENT_URL = "http://news-at.zhihu.com/api/4/news/";

    private final Context mContext;
    private final List<TopStoriesEntity> mEntities;
    private final ArrayList<StoryEntity> mListStory;

    public CarouselAdapter(Context context, List<TopStoriesEntity> entity, List<StoryEntity> listStory)
    {
        mContext = context;
        mEntities = entity;
        mListStory = (ArrayList<StoryEntity>) listStory;
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_carousel, null);
        TextView text = (TextView) view.findViewById(R.id.title);
        text.setText(mEntities.get(position).getTitle());

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(mEntities.get(position).getImage(), imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TopStoriesEntity entity = mEntities.get(position);
                int id = entity.getId();

                StoryEntity story = null;
                int i = 0;
                for (i = 0; i < mListStory.size(); ++i)
                {
                    story = mListStory.get(i);
                    if (story.getId() == id)
                    {
                        break;
                    }
                }

                Intent intent = new Intent(mContext, ContentActivity.class);


                intent.putExtra(ContentActivity.LIST_INDEX, i);
                intent.putExtra(ContentActivity.STORY_LIST, mListStory);
                mContext.startActivity(intent);
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
