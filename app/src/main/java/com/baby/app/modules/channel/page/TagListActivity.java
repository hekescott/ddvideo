package com.baby.app.modules.channel.page;


import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.request.DetailListRequest;
import com.android.baselibrary.util.ActivityUtil;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.util.StringUtils;
import com.android.baselibrary.widget.RefreshLayout;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.channel.adapter.TagListAdapter;
import com.baby.app.modules.home.persenter.DetaiiListPresenter;
import com.baby.app.modules.home.view.DetailListView;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class TagListActivity extends IBaseActivity implements DetailListView,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG_LIST_KEY = "TAG_LIST_KEY";

//    @BindView(R.id.tag_top_recycle_view)
//    RecyclerView topRecycleView;

    @BindView(R.id.tag_list_recycle_view)
    RecyclerView listRecycleView;

    @BindView(R.id.tag_swipeLayout)
    RefreshLayout mRefreshLayout;

    private DetaiiListPresenter mDetaiiListPresenter;
    private DetailListRequest request = new DetailListRequest();

//    private TagListTopAdapter mTagListTopAdapter;

    private TagListAdapter mTagListAdapter;

    private List<ChannelTagBean> tagBeanList = new ArrayList<>();

    private List<DetailListBean.Data> dataList = new ArrayList<>();

    private final LoadRunnable mLoadRunnable = new LoadRunnable(this);

    public static void startTagList(ChannelTagBean channelTagBean) {
        List<ChannelTagBean> list = new ArrayList<>();
        list.add(channelTagBean);
        startTagList(list);
    }

    public static void startTagList(List<ChannelTagBean> channelTagBeans) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_LIST_KEY, (Serializable) channelTagBeans);
        ActivityUtil.navigateTo(TagListActivity.class, bundle);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_tag_list;
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
        mTitleBuilder.setMiddleTitleText("标签筛选")
                .setLeftDrawable(R.mipmap.icon_title_back_white);
    }

    @Override
    public void initUiAndListener() {
        mDetaiiListPresenter = new DetaiiListPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            tagBeanList =
                    (List<ChannelTagBean>) (getIntent().getExtras().getSerializable(TAG_LIST_KEY));
            ArrayList<String> tagList = new ArrayList<>();
            if (tagBeanList.size() == 1) {
                mTitleBuilder.setMiddleTitleText(tagBeanList.get(0).getName());
            }
            for (int i = 0; i < tagBeanList.size(); i++) {
                ChannelTagBean channelTagBean = tagBeanList.get(i);
                tagList.add(channelTagBean.getId() + "");
            }
            String tags = StringUtils.arrayList2String(tagList, ",");
            request.setTagIds(tags);
            request.setPageNum(1);
        }

        request.setPageNum(1);
//        initTopRecycleView();
        initListRecycleView();
        mDetaiiListPresenter.fetchListByClassId(request);
    }

//    private void initTopRecycleView() {
//
//
//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        manager.setOrientation(OrientationHelper.HORIZONTAL);
//        topRecycleView.setLayoutManager(manager);
//        topRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                                       RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.left = ScreenUtil.dip2px(mContext, 10);
//                outRect.right = ScreenUtil.dip2px(mContext, 1);
//            }
//        });
//        mTagListTopAdapter = new TagListTopAdapter(R.layout.item_tag_top_layout, tagBeanList);
//        topRecycleView.setAdapter(mTagListTopAdapter);
//    }

    private void initListRecycleView() {
        listRecycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
        listRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if (pos%2 ==0) {
                    outRect.left = ScreenUtil.dip2px(mContext, 10);
                    outRect.right = ScreenUtil.dip2px(mContext, 4);
                } else {
                    outRect.left = ScreenUtil.dip2px(mContext, 4);
                    outRect.right = ScreenUtil.dip2px(mContext, 10);
                }
                outRect.bottom = ScreenUtil.dip2px(mContext, 10);
            }
        });
        mTagListAdapter = new TagListAdapter(R.layout.item_class_list_layout, dataList);
        listRecycleView.setAdapter(mTagListAdapter);
        mTagListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DetailListBean.Data data = dataList.get(position);
                jumpToVideo(data.getId(), data.getVideoName(), data.getVideoUrl());
            }
        });

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshing(true);
        mTagListAdapter.openLoadAnimation();
        mTagListAdapter.setOnLoadMoreListener(this);
    }

    //加载更多
    static class LoadRunnable implements Runnable {
        WeakReference<TagListActivity> mFregmentReference;

        public LoadRunnable(TagListActivity tagListActivity) {
            mFregmentReference = new WeakReference<TagListActivity>(tagListActivity);
        }

        @Override
        public void run() {
            if (mFregmentReference != null) {
                final TagListActivity activity = mFregmentReference.get();
                activity.mDetaiiListPresenter.fetchListByClassId(activity.request);
            }
        }
    }

    @Override
    public void onRefresh() {
        request.setPageNum(1);
        mDetaiiListPresenter.fetchListByClassId(request);
    }

    @Override
    public void onLoadMoreRequested() {
        new Handler().postDelayed(mLoadRunnable, 100);
    }

    @Override
    public void detailListLoad(DetailListBean listBean) {

        if (request.getPageNum() == 1) {
            dataList.clear();
        }

        if (listBean.getData().size() > 0) {
            request.morePage();
            request.setPageSize(listBean.getData().size());
            mTagListAdapter.addData(listBean.getData());
        }
        if (listBean.getData().size() == 0 || listBean.getData().size() < request.getPageSize()){
            //Log.d("tag",listBean.getPages() +"     " + request.getPageNum()+"");
            mTagListAdapter.loadMoreEnd();
        } else {
            //加载更多完成
            mTagListAdapter.loadMoreComplete();
        }
        mRefreshLayout.setRefreshing(false);

    }
}
