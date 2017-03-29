package com.twolight.fetcher.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.R;
import com.twolight.fetcher.contact.presenter.LoadPresenter;
import com.twolight.fetcher.contact.view.LoadView;
import com.twolight.fetcher.interfaces.Route;
import com.twolight.fetcher.model.Data;
import com.twolight.fetcher.model.Entity;
import com.twolight.fetcher.ui.fragment.AlbumFragment;
import com.twolight.fetcher.ui.fragment.ChooseFragment;
import com.twolight.fetcher.ui.fragment.PreviewFragment;

import java.util.ArrayList;

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
        mLoadPresenter.load(this);

//      requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,Constants.PERMISSION_SEARCH_PICTURE);
    }

    @Override
    public void album() {
        replaceFragment(R.id.fragment_container, AlbumFragment.create(this));
    }

    @Override
    public void cancel() {
        finish();
        mLoadPresenter.release();
    }

    @Override
    public void pop() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void preview(String path, int position) {
        replaceFragmentBackStack(R.id.fragment_container, PreviewFragment.previewFolder(this,path,position));
    }

    @Override
    public void preview() {
        replaceFragmentBackStack(R.id.fragment_container, PreviewFragment.previewSelect(this));
    }

    @Override
    public void ok() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("data", (ArrayList<Entity>) Data.getInstance().getSelected());
        setResult(Load.getInstance().resultCode(),intent);
        finish();

        mLoadPresenter.release();
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
