package com.example.p010_recycleviewall.shoucang.iview;


import com.example.p010_recycleviewall.shoucang.domain.FmNewFoodlistModel;
import com.example.p010_recycleviewall.tablayout.fragmentframelayout.mvp.IView;

public interface ILookAllView extends IView {
    void onGetLookAllSuccess(FmNewFoodlistModel data);
    void onGetLookAllNotok(boolean isok);
    void onGetLookAllEmpty(String msg);
    void onGetLookAllFailed(String msg);
}
