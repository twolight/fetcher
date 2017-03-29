package com.twolight.fetcher.util;

/**
 * Created by twolight on 17/3/29.
 */

public class DuringUtil {
    public static String converthhmmss(long time,String seperate){
        long hour  = time / 3600;
        long minute = (time - hour*3600) / 60;
        long second = (time - hour*3600 - minute*60);
        StringBuilder builder = new StringBuilder("");

        if(hour > 0){
            builder.append(String.format("%02d", hour));
            builder.append(seperate);
        }
        if(minute >= 0){
            builder.append(String.format("%02d", minute));
            builder.append(seperate);
        }
        if(minute >= 0){
            builder.append(String.format("%02d", second));
            builder.append(seperate);
        }
        return builder.toString();
    }
}
