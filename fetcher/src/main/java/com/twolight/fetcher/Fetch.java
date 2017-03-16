package com.twolight.fetcher;

import android.content.Context;
import android.content.Intent;

import com.twolight.fetcher.interfaces.Setting;
import com.twolight.fetcher.ui.activity.FetchActivity;

/**
 * Created by twolight on 17/3/16.
 */

public class Fetch {
    public static void start(Context context, Setting setting){
        Load.getInstance().init(setting);

        Intent intent = new Intent(context, FetchActivity.class);
        context.startActivity(intent);
    }
}
