package com.twolight.fetcherdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.twolight.fetcher.Fetch;
import com.twolight.fetcher.interfaces.Setting;
import com.twolight.fetcher.model.Entity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private boolean image = true;
    private final int RESULT_CODE = 100;
    private final int REQUEST_CODE = 101;
    private CheckBox mSingleCheckBox;
    private RadioGroup mTypeCheckBox;
    private DataAdapter mDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTypeCheckBox = (RadioGroup)findViewById(R.id.type);
        mTypeCheckBox.setOnCheckedChangeListener(this);


        mSingleCheckBox = (CheckBox)findViewById(R.id.single);
        mSingleCheckBox.setChecked(true);


        findViewById(R.id.start).setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        mDataAdapter = new DataAdapter(this);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.selected_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mDataAdapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.type_image:
                image = true;
                break;

            case R.id.type_video:
                image = false;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.start:
                Fetch.start(this, new FetchSetting(image,mSingleCheckBox.isChecked()));
                break;
        }
    }

    public static class FetchSetting implements Setting{
        private boolean mIsImage;
        private boolean mIsSingle;

        public  FetchSetting(boolean image,boolean single){
            mIsImage = image;
            mIsSingle = single;
        }

        @Override
        public boolean isSingle() {
            return mIsSingle;
        }

        @Override
        public void load(Context context, ImageView imageView, String path) {
            Glide.with(context).load(path).into(imageView);
        }

        @Override
        public int type() {
            return mIsImage ? 0 : 1;
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
            mDataAdapter.add(dataList,true);
            mDataAdapter.notifyDataSetChanged();
        }
    }
}
