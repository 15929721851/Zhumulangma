package com.gykj.zhumulangma.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gykj.zhumulangma.common.mvvm.BaseFragment;
import com.gykj.zhumulangma.home.R;


public class CategoryFragment extends BaseFragment {


    public CategoryFragment() {

    }


    @Override
    protected int onBindLayout() {
        return R.layout.home_fragment_category;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean enableSimplebar() {
        return false;
    }
}