package com.twolight.fetcher.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
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

    private List<Entity> mEntities;

    protected TextView previewBack;
    protected TextView previewSubmit;
    private int mCurrentPosition;
    private View previewSelect;
    private Route mRoute;

    private PreviewPresenter mPreviewPresenter;

    public static PreviewFragment create(Route route,
                                         int position){
        PreviewFragment fragment = new PreviewFragment();
        fragment.mRoute = route;
        fragment.mCurrentPosition = position;
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_preview_single_image;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(mEntities,mCurrentPosition);

        mPreviewPresenter = new PreviewPresenter(this);

        mPreviewPresenter.checkSelectStatus(mEntities.get(mCurrentPosition));
        mPreviewPresenter.checkSubmitStatus();
    }


    private void init(List<Entity> entities,int position) {
        View previewCancel =  findViewById(R.id.preview_cancel);
        previewSelect =  findViewById(R.id.preview_select);
        previewBack =  findViewById(R.id.preview_back);
        previewSubmit = findViewById(R.id.preview_submit);


        previewBack.setVisibility(Load.getInstance().isSingle() ? View.INVISIBLE : View.VISIBLE);
        previewCancel.setVisibility(Load.getInstance().isSingle() ? View.VISIBLE : View.INVISIBLE);

        previewCancel.setOnClickListener(this);
        previewSelect.setOnClickListener(this);
        previewBack.setOnClickListener(this);
        previewSubmit.setOnClickListener(this);

        ViewPager previewViewpager = (ViewPager) findViewById(R.id.preview_viewpager);
        previewViewpager.setAdapter(new PreviewAdapter(getActivity(),entities));

        previewViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPreviewPresenter.checkSelectStatus(mEntities.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        previewViewpager.setCurrentItem(position);

        previewBack.setOnClickListener(this);
        previewSelect.setOnClickListener(this);
    }

    @Override
    public void showSubmitStatus(boolean show) {
        if(show){
            previewSubmit.setSelected(true);
            previewSubmit.setOnClickListener(this);
        }else{
            previewSubmit.setSelected(false);
            previewSubmit.setOnClickListener(null);
        }
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
            mRoute.cancel();

        } else if (i == R.id.preview_back) {
            mRoute.cancel();

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
