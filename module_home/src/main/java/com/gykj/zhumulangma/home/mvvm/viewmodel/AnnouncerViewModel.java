package com.gykj.zhumulangma.home.mvvm.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.gykj.zhumulangma.common.event.SingleLiveEvent;
import com.gykj.zhumulangma.common.mvvm.model.ZhumulangmaModel;
import com.gykj.zhumulangma.common.mvvm.viewmodel.BaseViewModel;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.Announcer;
import com.ximalaya.ting.android.opensdk.model.announcer.AnnouncerList;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class AnnouncerViewModel extends BaseViewModel<ZhumulangmaModel> {

    private static final String PAGESIZE = "20";
    private SingleLiveEvent<List<BannerV2>> mBannerV2SingleLiveEvent;
    private SingleLiveEvent<List<Announcer>> mAnnouncerSingleLiveEvent;
    private SingleLiveEvent<List<Announcer>> mTopSingleLiveEvent;



    private int curPage = 1;

    public AnnouncerViewModel(@NonNull Application application, ZhumulangmaModel model) {
        super(application, model);
    }

    public void getBannerList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, "0");
        map.put(DTransferConstants.IMAGE_SCALE, "2");
        map.put(DTransferConstants.CONTAINS_PAID,"true");
        mModel.getCategoryBannersV2(map)
                .subscribe(bannerV2List ->
                                getBannerV2SingleLiveEvent().postValue(bannerV2List.getBannerV2s())
                        , throwable -> throwable.printStackTrace());
    }


    public void getTopList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.VCATEGORY_ID, "0");
        map.put(DTransferConstants.CALC_DIMENSION, "1");
        map.put(DTransferConstants.PAGE_SIZE, "3");
        mModel.getAnnouncerList(map).subscribe(announcerList ->
                getTopSingleLiveEvent().postValue(announcerList.getAnnouncerList()), e->e.printStackTrace());
    }

    public void getLiveList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.VCATEGORY_ID, "0");
        map.put(DTransferConstants.CALC_DIMENSION, "1");
        map.put(DTransferConstants.PAGE_SIZE,PAGESIZE);
        map.put(DTransferConstants.PAGE,String.valueOf(curPage));
        mModel.getAnnouncerList(map).subscribe(announcerList -> {
            curPage++;
            getAnnouncerSingleLiveEvent().postValue(announcerList.getAnnouncerList());
        }, e->e.printStackTrace());
    }


    public SingleLiveEvent<List<BannerV2>> getBannerV2SingleLiveEvent() {
        return mBannerV2SingleLiveEvent = createLiveData(mBannerV2SingleLiveEvent);
    }

    public SingleLiveEvent<List<Announcer>> getAnnouncerSingleLiveEvent() {
        return mAnnouncerSingleLiveEvent = createLiveData(mAnnouncerSingleLiveEvent);
    }

    public SingleLiveEvent<List<Announcer>> getTopSingleLiveEvent() {
        return mTopSingleLiveEvent = createLiveData(mTopSingleLiveEvent);
    }


}
