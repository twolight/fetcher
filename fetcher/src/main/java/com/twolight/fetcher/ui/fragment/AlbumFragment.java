package com.twolight.fetcher.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.R;
import com.twolight.fetcher.adapter.AlbumAdapter;
import com.twolight.fetcher.contact.presenter.AlbumPresenter;
import com.twolight.fetcher.contact.presenter.SelectPresenter;
import com.twolight.fetcher.contact.view.AlbumView;
import com.twolight.fetcher.contact.view.SelectView;
import com.twolight.fetcher.interfaces.Route;
import com.twolight.fetcher.model.Album;

import java.util.List;

/**
 * Created by twolight on 16/7/13.
 */
public class AlbumFragment extends BaseFragment implements
        View.OnClickListener,AlbumView,SelectView,AlbumAdapter.OnAlbumItemClickListener {
    private RecyclerView mRecyclerView;
    private AlbumAdapter adapter;

    protected TextView chooseImagePreview;
    protected TextView chooseImageSubmit;

    private AlbumPresenter mAlbumPresenter;
    private SelectPresenter mSelectPresenter;
    private Route mRoute;


    public static AlbumFragment create(Route route){
        AlbumFragment fragment = new AlbumFragment();
        fragment.mRoute = route;
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_album;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHeader();
        initBottom();

        mAlbumPresenter = new AlbumPresenter(this);
        mAlbumPresenter.loadAlbumData();

        mSelectPresenter = new SelectPresenter(this);
        mSelectPresenter.checkSubmitStatus();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initHeader() {
        final Button backBtn = findViewById(R.id.normal_header_back_btn);
        final Button menuBtn = findViewById(R.id.normal_header_menu_btn);
        final TextView backTextView = findViewById(R.id.normal_header_back_tV);
        final TextView menuTextView = findViewById(R.id.normal_header_menu_tV);
        final TextView title = findViewById(R.id.normal_header_title);
        backBtn.setVisibility(View.GONE);
        menuBtn.setVisibility(View.GONE);
        backTextView.setVisibility(View.GONE);

        menuTextView.setVisibility(View.VISIBLE);
        menuTextView.setText(R.string.app_cancel);
        menuTextView.setOnClickListener(this);
        title.setText(R.string.album);
    }

    public void initBottom(){
        if(!Load.getInstance().isSingle()){
            ViewStub viewStub = findViewById(R.id.ablum_view_stub);
            viewStub.inflate();

            chooseImagePreview =  findViewById(R.id.choose_image_preview);
            chooseImageSubmit = findViewById(R.id.choose_image_submit);
        }
    }

    @Override
    public void initContent(List<Album> albums) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = findViewById(R.id.recycler_view_choose_folder);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new AlbumAdapter(getContext());
        adapter.add(albums,true);
        adapter.setOnAlbumItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onAlbumItemClick(Album album) {
        mRoute.select(album.getPath());
    }



    @Override
    public void showSubmitStatus(boolean show) {
        chooseImageSubmit.setSelected(show);
        chooseImageSubmit.setOnClickListener(show ? this : null);

        chooseImagePreview.setSelected(show);
        chooseImagePreview.setOnClickListener(show ? this : null);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.normal_header_menu_tV) {
            mRoute.cancel();
        } else if (i == R.id.choose_image_preview) {
            mRoute.preview();
        } else if (i == R.id.choose_image_submit) {
            mRoute.ok();

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAlbumPresenter.detachView();
        mSelectPresenter.detachView();
    }
}
