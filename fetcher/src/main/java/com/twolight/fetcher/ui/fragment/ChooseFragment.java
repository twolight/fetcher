package com.twolight.fetcher.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.R;
import com.twolight.fetcher.adapter.ChooseAdapter;
import com.twolight.fetcher.contact.presenter.ChoosePresenter;
import com.twolight.fetcher.contact.view.ChooseView;
import com.twolight.fetcher.interfaces.Route;
import com.twolight.fetcher.model.Entity;

import java.util.List;

/**
 * Created by twolight on 17/3/15.
 */

public class ChooseFragment extends BaseFragment implements View.OnClickListener,
        ChooseView,
        ChooseAdapter.OnChooseItemListener{
    private RecyclerView mRecyclerView;
    private ChooseAdapter mChooseAdapter;
    private ChoosePresenter mChoosePresenter;

//    private Setting mSetting;
    private Route mRoute;
    private String mFolderName;

    TextView chooseImagePreview;
    TextView chooseImageSubmit;


    public static ChooseFragment create(Route route,String folderName){
        ChooseFragment fragment = new ChooseFragment();
//        fragment.mSetting = setting;
        fragment.mRoute = route;
        fragment.mFolderName = folderName;
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHeader();
        initContent();

        mChoosePresenter = new ChoosePresenter(this);
        mChoosePresenter.getFolder(mFolderName);
    }

    private void initHeader() {
        final Button backBtn = findViewById(R.id.normal_header_back_btn);
        final Button menuBtn = findViewById(R.id.normal_header_menu_btn);
        final TextView backTextView = findViewById(R.id.normal_header_back_tV);
        final TextView menuTextView = findViewById(R.id.normal_header_menu_tV);
        final TextView title = findViewById(R.id.normal_header_title);
        backBtn.setVisibility(View.GONE);
        menuBtn.setVisibility(View.GONE);
        backTextView.setVisibility(View.VISIBLE);
        backTextView.setText(R.string.album);
        backTextView.setOnClickListener(this);

        menuTextView.setVisibility(View.VISIBLE);
        menuTextView.setText(R.string.app_cancel);
        menuTextView.setOnClickListener(this);
        title.setText(R.string.app_choose_image);
    }

    private void initContent() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        mRecyclerView = findViewById(R.id.recycler_view_choose_picture);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void getFolderComplete(List<Entity> entities) {
        mChooseAdapter = new ChooseAdapter(getContext(),mRoute);
        mChooseAdapter.setOnChooseItemListener(this);
        mChooseAdapter.add(entities,false);
        mRecyclerView.setAdapter(mChooseAdapter);

        if(!Load.getInstance().isSingle()){
            ViewStub viewStub = findViewById(R.id.view_stub);
            viewStub.inflate();

            TextView chooseImageCount = findViewById(R.id.choose_image_count);
            chooseImagePreview =  findViewById(R.id.choose_image_preview);
            chooseImageSubmit =  findViewById(R.id.choose_image_submit);

            chooseImageCount.setText(entities.size()+"张照片");

            chooseImagePreview.setOnClickListener(this);
            chooseImageSubmit.setOnClickListener(this);
        }
    }

    @Override
    public void onSelectComplete(Entity entity) {
        mChooseAdapter.notifyDataSetChanged();
    }

    public void preview(int position){
        mRoute.preview(mFolderName,position);
    }

    @Override
    public void updateSubmitStatus(boolean show) {
        chooseImagePreview.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        chooseImageSubmit.setSelected(show);
    }

    @Override
    public void onItemClick(Entity entity,int position) {
        mChoosePresenter.click(entity,position);
    }

    @Override
    public void onItemSelect(Entity entity) {
        mChoosePresenter.select(entity);
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.normal_header_back_tV) {//to album
            mRoute.album();

        } else if (i == R.id.normal_header_menu_tV) {
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
        mChoosePresenter.detachView();
    }
}
