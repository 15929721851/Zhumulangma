package com.gykj.zhumulangma.listen.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gykj.zhumulangma.common.Constants;
import com.gykj.zhumulangma.common.bean.FavoriteBean;
import com.gykj.zhumulangma.common.mvvm.view.BaseRefreshMvvmFragment;
import com.gykj.zhumulangma.listen.R;
import com.gykj.zhumulangma.listen.adapter.FavoriteAdapter;
import com.gykj.zhumulangma.listen.mvvm.ViewModelFactory;
import com.gykj.zhumulangma.listen.mvvm.viewmodel.FavoriteViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * Author: Thomas.
 * <br/>Date: 2019/8/16 8:45
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:我的喜欢
 */
@Route(path = Constants.Router.Listen.F_FAVORITE)
public class FavoriteFragment extends BaseRefreshMvvmFragment<FavoriteViewModel, FavoriteBean> implements
        BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    private SmartRefreshLayout refreshLayout;
    private FavoriteAdapter mFavoriteAdapter;

    @Override
    protected int onBindLayout() {
        return R.layout.common_layout_refresh_loadmore;
    }

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = fd(R.id.recyclerview);
        refreshLayout = fd(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);
        mFavoriteAdapter = new FavoriteAdapter(R.layout.listen_item_favorite);
        mFavoriteAdapter.bindToRecyclerView(recyclerView);
    }

    @Override
    public void initListener() {
        super.initListener();
        mFavoriteAdapter.setOnItemChildClickListener(this);
        mFavoriteAdapter.setOnItemClickListener(this);
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(refreshLayout,mFavoriteAdapter);
    }


    @Override
    public void initData() {
        mViewModel.init();
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitFavoritesEvent().observe(this, favoriteBeans -> mFavoriteAdapter.setNewData(favoriteBeans));
    }


    @Override
    public String[] onBindBarTitleText() {
        return new String[]{"我喜欢的声音"};
    }
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mViewModel.unlike(mFavoriteAdapter.getItem(position).getTrack());
        mFavoriteAdapter.remove(position);
        if(mFavoriteAdapter.getData().size()==0){
            showEmptyView();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Track track = mFavoriteAdapter.getItem(position).getTrack();
        mViewModel.play(track.getAlbum().getAlbumId(),track.getDataId());
    }

    @Override
    public Class<FavoriteViewModel> onBindViewModel() {
        return FavoriteViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

}
