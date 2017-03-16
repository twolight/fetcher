package com.twolight.fetcher.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.twolight.fetcher.R;
import com.twolight.fetcher.contact.presenter.LoadPresenter;
import com.twolight.fetcher.contact.view.LoadView;
import com.twolight.fetcher.contact.view.PreviewView;
import com.twolight.fetcher.interfaces.Route;
import com.twolight.fetcher.ui.fragment.AlbumFragment;
import com.twolight.fetcher.ui.fragment.ChooseFragment;
import com.twolight.fetcher.ui.fragment.PreviewFragment;

/**
 * Created by twolight on 17/3/15.
 */

public class FetchActivity extends BaseActivity implements LoadView,Route{
    private ProgressBar mSearchProgressBar;
    private LoadPresenter mLoadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);

        mSearchProgressBar = (ProgressBar) findViewById(R.id.search_progress_bar);

        mLoadPresenter = new LoadPresenter(this);
        mLoadPresenter.load(this,1);

//      requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,Constants.PERMISSION_SEARCH_PICTURE);
    }

    @Override
    public void album() {
        replaceFragment(R.id.fragment_container, AlbumFragment.create(this));
    }

    @Override
    public void cancel() {
        finish();
    }

    @Override
    public void preview(String path) {
        replaceFragment(R.id.fragment_container, PreviewFragment.create(this));
    }

    @Override
    public void preview() {

    }

    @Override
    public void ok() {

    }

    @Override
    public void select(String folderName) {
        replaceFragment(R.id.fragment_container, ChooseFragment.create(this,folderName));
    }

    @Override
    public void loadComplete(boolean success) {
        if(success){
            mSearchProgressBar.setVisibility(View.INVISIBLE);
            replaceFragment(R.id.fragment_container, ChooseFragment.create(this,null));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadPresenter.detachView();
    }
}
