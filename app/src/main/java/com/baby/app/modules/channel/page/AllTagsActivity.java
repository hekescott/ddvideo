package com.baby.app.modules.channel.page;

import android.graphics.Rect;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.android.baselibrary.base.DefaultView;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.channel.AllTagsBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.util.ActivityUtil;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.RefreshLayout;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.channel.adapter.AllTagsAdapter;
import com.baby.app.modules.channel.presenter.AllTagsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 所有专题
 */

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class AllTagsActivity extends IBaseActivity implements DefaultView<List<AllTagsBean>>,
        SwipeRefreshLayout.OnRefreshListener {

    //列表
    @BindView(R.id.star_swipeLayout)
    RefreshLayout swipeLayout;
    @BindView(R.id.star_list_recycler_view)
    RecyclerView listRecycleView;

    private AllTagsAdapter mAdapter;

    //明星列表
    private List<AllTagsBean> allTagListBean = new ArrayList<>();

    private AllTagsPresenter mAllTagsPresenter;

    public static void startAllTags() {
        ActivityUtil.navigateTo(AllTagsActivity.class);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_all_tags;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("所有专题")
                .setLeftDrawable(R.mipmap.icon_title_back_white);
    }

    @Override
    public void initUiAndListener() {
        mAllTagsPresenter = new AllTagsPresenter(this);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);
        initListRecycleView();

        mAllTagsPresenter.fetchAllTags();
    }

    private void initListRecycleView() {

        listRecycleView.setLayoutManager(new GridLayoutManager(mContext, 3));
        listRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if (pos % 3 == 0) {
                    outRect.left = ScreenUtil.dip2px(mContext, 6);
                    outRect.right = ScreenUtil.dip2px(mContext, 1);
                } else if (pos % 3 == 1) {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 1);
                } else {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 6);
                }
                outRect.bottom = ScreenUtil.dip2px(mContext, 15);
            }
        });
        mAdapter = new AllTagsAdapter(R.layout.item_all_tag_list_layout, allTagListBean);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChannelTagBean channelTagBean = new ChannelTagBean();
                channelTagBean.setId(allTagListBean.get(position).getId());
                channelTagBean.setName(allTagListBean.get(position).getName());
                channelTagBean.setPicUrl(allTagListBean.get(position).getPicUrl());
                TagListActivity.startTagList(channelTagBean);
            }
        });
        listRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        mAllTagsPresenter.fetchAllTags();
    }

    @Override
    public void refresh(List<AllTagsBean> allTagsBean) {
        if (allTagsBean != null && allTagsBean.size() > 0) {
            allTagListBean = allTagsBean;
            mAdapter.setNewData(allTagListBean);
        }
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showNetError() {
        super.showNetError();
        swipeLayout.setRefreshing(false);
    }
}
