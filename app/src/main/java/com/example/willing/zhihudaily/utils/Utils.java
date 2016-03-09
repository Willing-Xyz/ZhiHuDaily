package com.example.willing.zhihudaily.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.Calendar;

/**
 * Created by Willing on 2/28/2016/028.
 */
public class Utils
{
    private static final String[] weekPart= {"日", "一", "二", "三", "四", "五", "六"};

    public static Point getScreenResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    public static String convertDateToString(Calendar cal)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH) + 1;
        if (month < 10)
        {
            builder.append("0" + month);
        }
        else
        {
            builder.append(month);
        }
        int day = cal.get(Calendar.DATE);
        if (day < 10)
        {
            builder.append("0" + day);
        }
        else
        {
            builder.append(day);
        }
        return builder.toString();
    }

    /**
     *
     * @param mDate 格式20160323
     * @return  03月23日 星期X
     */
    public static String formatDateString(String mDate) {

        StringBuilder builder = new StringBuilder();

        int year = Integer.valueOf(mDate.substring(0, 4));
        int month = Integer.valueOf(mDate.substring(4, 6));
        int day = Integer.valueOf(mDate.substring(6, 8));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);

        int week = cal.get(Calendar.DAY_OF_WEEK);



        builder.append(mDate.substring(4, 6)).append("月")
                .append(mDate.substring(6, 8)).append("日 ")
                .append("星期").append(weekPart[week - 1]);


        return builder.toString();
    }
}
