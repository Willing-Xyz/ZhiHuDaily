package com.example.willing.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.model.TopStoriesEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Willing on 2016/3/9.
 */
public class CarouselAdapter extends PagerAdapter{

    private final Context mContext;
    private final List<TopStoriesEntity> mEntities;

    public CarouselAdapter(Context context, List<TopStoriesEntity> entity)
    {
        mContext = context;
        mEntities = entity;
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
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_carousel, null);
        TextView text = (TextView) view.findViewById(R.id.title);
        text.setText(mEntities.get(position).getTitle());

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(mEntities.get(position).getImage(), imageView);



        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
