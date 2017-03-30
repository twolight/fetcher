package com.twolight.fetcher;

import android.app.Activity;
import android.content.Intent;

import com.twolight.fetcher.interfaces.Setting;
import com.twolight.fetcher.ui.activity.FetchActivity;

/**
 * Created by twolight on 17/3/16.
 */

public class Fetch {
    public static void start(Activity activity, Setting setting){
        SettingRepertory.getInstance().init(setting);

        Intent intent = new Intent(activity, FetchActivity.class);
        activity.startActivityForResult(intent,setting.requestCode());
    }
}
