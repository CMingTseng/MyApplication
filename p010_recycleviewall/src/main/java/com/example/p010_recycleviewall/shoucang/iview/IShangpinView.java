package com.example.p010_recycleviewall.shoucang.iview;


import com.example.p010_recycleviewall.shoucang.domain.ShoucangShangpinIndexBean;
import com.example.p010_recycleviewall.tablayout.fragmentframelayout.mvp.IView;

public interface IShangpinView extends IView {
    void onGetShangpinSuccess(ShoucangShangpinIndexBean data);

    void onGetShangpinNotok(boolean isok);

    void onGetShangpinEmpty(String msg);

    void onGetShangpinFailed(String msg);
}
