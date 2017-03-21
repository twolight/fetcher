package com.twolight.fetcherdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.twolight.fetcher.Fetch;
import com.twolight.fetcher.interfaces.Setting;
import com.twolight.fetcher.model.Entity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean single = false;
    private final int DATA_RESULT_CODE = 100;
    private final int DATA_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.image).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.single).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.single:
                single = !single;
                break;

            case R.id.image:

                break;

            case R.id.start:
                Fetch.start(this, new FetchSetting());
                break;
        }
    }

    public static class FetchSetting implements Setting{
        @Override
        public boolean isSingle() {
            return false;
        }

        @Override
        public void load(Context context, ImageView imageView, String path) {
            Glide.with(context).load(path).into(imageView);
        }

        @Override
        public int type() {
            return 0;
        }

        @Override
        public int resultCode() {
            return 100;
        }

        @Override
        public int requestCode() {
            return 101;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100){
            ArrayList<Entity> dataList = data.getExtras().getParcelableArrayList("data");

            for (Entity entity : dataList){
                Log.e(this.getLocalClassName(),entity.getPath());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("twolight","MainActivity onDestroy");
    }
}
