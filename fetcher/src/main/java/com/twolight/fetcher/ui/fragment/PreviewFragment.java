package com.twolight.fetcher.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.R;
import com.twolight.fetcher.adapter.PreviewAdapter;
import com.twolight.fetcher.contact.presenter.PreviewPresenter;
import com.twolight.fetcher.contact.view.PreviewView;
import com.twolight.fetcher.interfaces.Route;
import com.twolight.fetcher.model.Entity;

import java.util.List;

/**
 * Created by twolight on 16/11/16.
 */

public class PreviewFragment extends BaseFragment implements
        View.OnClickListener,PreviewView{

    protected ImageView previewBack;
    protected TextView previewSubmit;
    private int mInitPosition;
    private View previewSelect;
    private Route mRoute;
    private String mFolderName;
    private List<Entity> mEntities;
    private int mType;
    private PreviewAdapter mPreviewAdapter;

    private PreviewPresenter mPreviewPresenter;



    public static PreviewFragment previewFolder(Route route,
                                                String folderName,
                                         int position){
        PreviewFragment fragment = new PreviewFragment();
        fragment.mRoute = route;
        fragment.mFolderName = folderName;
        fragment.mInitPosition = position;
        fragment.mType = 0;
        return fragment;
    }

    public static PreviewFragment previewSelect(Route route){
        PreviewFragment fragment = new PreviewFragment();
        fragment.mRoute = route;
        fragment.mType = 1;
        return fragment;
    }



    @Override
    public int getContentView() {
        return R.layout.fragment_preview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPreviewPresenter = new PreviewPresenter(this);
        mPreviewPresenter.loadData(mType,mFolderName);
    }


    @Override
    public void loadDataComplete(List<Entity> entities) {
        mEntities = entities;
        init(entities, mInitPosition);

        mPreviewPresenter.checkSelectStatus(entities.get(mInitPosition));
        mPreviewPresenter.checkSubmitStatus();
    }

    private void init(List<Entity> entities, int position) {
        View previewCancel =  findViewById(R.id.preview_cancel);
        previewSelect =  findViewById(R.id.preview_select);
        previewBack =  findViewById(R.id.preview_back);
        previewSubmit = findViewById(R.id.preview_submit);


        previewBack.setVisibility(Load.getInstance().isSingle() ? View.INVISIBLE : View.VISIBLE);
        previewCancel.setVisibility(Load.getInstance().isSingle() ? View.VISIBLE : View.INVISIBLE);
        previewSelect.setVisibility(Load.getInstance().isSingle() ? View.INVISIBLE : View.VISIBLE);


        previewCancel.setOnClickListener(this);
        previewSelect.setOnClickListener(this);
        previewBack.setOnClickListener(this);
        previewSubmit.setOnClickListener(this);


        mPreviewAdapter = new PreviewAdapter(getActivity(),entities);
        ViewPager previewViewpager = findViewById(R.id.preview_viewpager);
        previewViewpager.setAdapter(new PreviewAdapter(getActivity(),entities));

        previewViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mInitPosition = position;
                mPreviewPresenter.checkSelectStatus(mEntities.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        previewViewpager.setCurrentItem(position);
    }

    @Override
    public void selectComplete() {
        mPreviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSubmitStatus(boolean show) {
        previewSubmit.setSelected(show);
        previewSubmit.setOnClickListener(show ? this : null);
    }

    @Override
    public void setSelectStatus(boolean selected) {
        previewSelect.setSelected(selected);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.preview_cancel) {
            mRoute.cancel();
        } else if (i == R.id.preview_select) {
            mPreviewPresenter.select(mEntities.get(mInitPosition));
        } else if (i == R.id.preview_back) {
            mRoute.pop();
        } else if (i == R.id.preview_submit) {
            mRoute.ok();

        } else {
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mPreviewPresenter.detachView();
    }
}
