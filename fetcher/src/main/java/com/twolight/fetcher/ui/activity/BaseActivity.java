package com.twolight.fetcher.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by twolight on 16/5/4.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    public void replaceFragmentBackStack(int containerViewId,Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId,fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragment(int containerViewId,Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(containerViewId,fragment).commit();
    }
}
