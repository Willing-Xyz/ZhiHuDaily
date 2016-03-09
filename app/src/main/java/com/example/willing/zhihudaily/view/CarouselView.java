package com.example.willing.zhihudaily.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.example.willing.zhihudaily.adapter.CarouselAdapter;
import com.example.willing.zhihudaily.model.TopStoriesEntity;

import java.util.List;

/**
 * Created by Willing on 2016/3/9.
 */
public class CarouselView extends ViewPager
{
    private static final long DELAY_TIME = 4000;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Context mContext;

    private Runnable mUpdateRunnable = new Runnable() {
        @Override
        public void run() {

            if (getAdapter() != null) {
                setCurrentItem((getCurrentItem() + 1) % getAdapter().getCount(), true);
            }
            mHandler.postDelayed(mUpdateRunnable, DELAY_TIME);
        }
    };

    public CarouselView(Context context) {

        this(context, null);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;


    }

    public void setAdapter(List<TopStoriesEntity> entity)
    {
        setAdapter(new CarouselAdapter(mContext, entity));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mHandler.postDelayed(mUpdateRunnable, DELAY_TIME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mHandler.removeCallbacks(mUpdateRunnable);
    }
}
