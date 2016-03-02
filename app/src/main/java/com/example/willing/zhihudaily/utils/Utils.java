package com.example.willing.zhihudaily.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Willing on 2/28/2016/028.
 */
public class Utils
{
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

    public static String formatDateString(String mDate) {

        StringBuilder builder = new StringBuilder();

        builder.append(mDate.substring(0, 4)).append("年").append(mDate.substring(4, 6)).append("月").append(mDate.substring(6, 8));

        return builder.toString();
    }
}
