package com.example.p010_recycleviewall.shoucang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.p010_recycleviewall.R;
import com.example.p010_recycleviewall.shoucang.adapter.ShoucangCaipuAdapter;
import com.example.p010_recycleviewall.shoucang.basefragment.BaseIndexNetFragment;
import com.example.p010_recycleviewall.shoucang.domain.IndexFoodFragmentUpdateIds;
import com.example.p010_recycleviewall.shoucang.domain.ShoucangCaipuIndexBean;
import com.example.p010_recycleviewall.shoucang.domain.ShoucangCaipuIndexModel;
import com.example.p010_recycleviewall.shoucang.iview.ICaipuView;
import com.example.p010_recycleviewall.shoucang.presenter.CaipuPresenter;
import com.example.p010_recycleviewall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by shining on 2017/2/27 0027.
 */

public class CaipuFragment extends BaseIndexNetFragment implements ICaipuView, View.OnClickListener {

    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_lookall)
    TextView tv_lookall;
    @BindView(R.id.fragment_container)
    FrameLayout fragment_container;
    @BindView(R.id.recycler_view1)
    RecyclerView recycler_view1;

    @BindView(R.id.rl_network_loading)
    RelativeLayout rl_network_loading;

    @BindView(R.id.rl_errornet)
    RelativeLayout rl_errornet;
    @BindView(R.id.empty_errnet_notice)
    TextView empty_errnet_notice;
    @BindView(R.id.empty_errnet_btn)
    TextView empty_errnet_btn;

    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;
    @BindView(R.id.tv_notice_caipu)
    TextView tv_notice_caipu;

    private Context mContext;
    private ShoucangCaipuAdapter mAdapter;
    private List<ShoucangCaipuIndexModel> list_data;

    private CaipuPresenter mCaipuPresenter = new CaipuPresenter();

    /**
     * @param value
     */
    @Override
    public void call(Object value) {
        IndexFoodFragmentUpdateIds ids = (IndexFoodFragmentUpdateIds) value;
        ToastUtil.showToastCenter(ids.getFood_name() + "," + ids.getFood_definition_id());
    }

    /**
     * 清除content适配器的数据bufen
     */
    private void getAdapterclear() {
        if (list_data == null) {
            list_data = new ArrayList<>();
        }
        int start = 0;
        int size = list_data.size();
        if (size == 0) return;
        list_data.clear();
        mAdapter.notifyItemRangeRemoved(start, size);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCaipuPresenter.onDestory();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_caipu;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        mContext = CaipuFragment.this.getActivity();
        findView(rootView);
        onclickListener();
        //
        doNetWork();
    }

    private void doNetWork() {
        loadings();
        mCaipuPresenter.onCreate(this);
        mCaipuPresenter.getCaipuPresenter();

    }

    private void findView(View rootView) {

        //数据列表bufen
        mAdapter = new ShoucangCaipuAdapter(mContext);
//        LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(mContext);
//        mLinearLayoutManager1.setOrientation(OrientationHelper.HORIZONTAL);
//        new GridLayoutManager(FoodManagerNewBanActivity.this, 3, LinearLayoutManager.HORIZONTAL, false)
        recycler_view1.setLayoutManager(new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false));
        recycler_view1.setAdapter(mAdapter);
    }

    private void onclickListener() {
        tv_lookall.setOnClickListener(this);
        empty_errnet_notice.setText("获取数据失败\n请检查网络是否通畅");
        empty_errnet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重试
                ToastUtil.showToastCenter("重试");
            }
        });
        //发现更多
        tv_notice_caipu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastCenter("发现更多菜谱 >");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lookall:
                //查看全部

                break;
            default:
                break;
        }
    }

    /**
     * 设置view 显隐 bufen
     *
     * @param recyclerView
     * @param network_loadings
     * @param rl_errornets
     * @param rl_nodatas
     */
    private void list_vis_data(int recyclerView, int network_loadings, int rl_errornets, int rl_nodatas) {
        rl_network_loading.setVisibility(network_loadings);
        rl_errornet.setVisibility(rl_errornets);
        rl_no_data.setVisibility(rl_nodatas);
        fragment_container.setVisibility(recyclerView);
    }

    private void loadings() {
        list_vis_data(View.GONE, View.VISIBLE, View.GONE, View.GONE);
    }

    private void errorNets() {
        list_vis_data(View.GONE, View.GONE, View.VISIBLE, View.GONE);
    }

    private void nodatas() {
        list_vis_data(View.GONE, View.GONE, View.GONE, View.VISIBLE);
    }

    private void successes() {
        list_vis_data(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }


    @Override
    public void onGetCaipuSuccess(ShoucangCaipuIndexBean data) {
        successes();
        tv_num.setText(data.getNum());
        getAdapterclear();
        list_data = data.getListitem();
        mAdapter.addConstacts(list_data);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onGetCaipuNotok(boolean isok) {
//        errorNets();
        nodatas();
    }

    @Override
    public void onGetCaipuEmpty(String msg) {
        nodatas();
    }

    @Override
    public void onGetCaipuFailed(String msg) {
        nodatas();
    }
}
