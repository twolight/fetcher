package com.twolight.fetcher.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.R;
import com.twolight.fetcher.adapter.ChooseAdapter;
import com.twolight.fetcher.contact.presenter.ChoosePresenter;
import com.twolight.fetcher.contact.presenter.SelectPresenter;
import com.twolight.fetcher.contact.view.ChooseView;
import com.twolight.fetcher.contact.view.SelectView;
import com.twolight.fetcher.interfaces.Route;
import com.twolight.fetcher.model.Entity;

import java.util.List;

/**
 * Created by twolight on 17/3/15.
 */

public class ChooseFragment extends BaseFragment implements View.OnClickListener,
        ChooseView,SelectView,
        ChooseAdapter.OnChooseItemListener{
    private RecyclerView mRecyclerView;
    private ChooseAdapter mChooseAdapter;
    private ChoosePresenter mChoosePresenter;
    private SelectPresenter mSelectPresenter;

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
        return R.layout.fragment_choose;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHeader();
        initContent();

        mChoosePresenter = new ChoosePresenter(this);
        mChoosePresenter.getEntityByFolder(mFolderName);

        mSelectPresenter = new SelectPresenter(this);
        mSelectPresenter.checkSubmitStatus();
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
        mChooseAdapter = new ChooseAdapter(getContext());
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
        }
    }

    @Override
    public void onSelectComplete(Entity entity,int position) {
        mChooseAdapter.notifyItemChanged(position,entity);
        mSelectPresenter.checkSubmitStatus();
    }

    @Override
    public void preview(int position){
        mRoute.preview(mFolderName,position);
    }

    @Override
    public void showSubmitStatus(boolean show) {
        if(!Load.getInstance().isSingle()){
            chooseImagePreview.setSelected(show);
            chooseImageSubmit.setSelected(show);

            chooseImagePreview.setOnClickListener(show ? this : null);
            chooseImageSubmit.setOnClickListener(show ? this : null);
        }
    }

    @Override
    public void onItemClick(Entity entity,int position) {
        mChoosePresenter.clickItem(entity,position);
    }

    @Override
    public void onItemSelect(Entity entity,int position) {
        mChoosePresenter.selectItem(entity,position);
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
        Log.e(ChooseFragment.class.getSimpleName(),"ChooseFragment onDetach");
        mChoosePresenter.detachView();
        mSelectPresenter.detachView();
    }
}
