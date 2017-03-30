package com.twolight.fetcher.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by twolight on 16/6/12.
 */
public abstract class BaseFragment extends Fragment{
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected <E extends View> E findViewById(int id) {
        return findViewById(getView(),id);
    }
    protected <E extends View> E findViewById(View view,int id) {
        try {
            return (E) view.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        return view;
    }
    public abstract int getContentView();
}
