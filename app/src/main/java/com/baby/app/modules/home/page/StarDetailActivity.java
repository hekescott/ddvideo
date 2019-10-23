package com.baby.app.modules.home.page;


import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.service.request.DetailListRequest;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.home.bean.StartDetailTypeBean;
import com.baby.app.modules.home.persenter.DetaiiListPresenter;
import com.baby.app.modules.home.view.DetailListView;
import com.baby.app.widget.layout.StarDetailAboutLayout;
import com.baby.app.widget.layout.StarDetailMindLayout;
import com.baby.app.widget.layout.StarDetailTopLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class StarDetailActivity extends IBaseActivity
        implements DetailListView {

    public static final String STAR_DETAIL_TYPE = "STAR_DETAIL_TYPE";

    //  @BindView(R.id.star_detail_list)
    //  NoScrollRecyclerView mlistView;

    private final LoadRunnable mLoadRunnable = new LoadRunnable(this);


    private List<DetailListBean.Data> dataList = new ArrayList<>();
    private HomeStarBean homeStarBean;
    // private StarDetailAdapter mStarDetailAdapter;
    private List<StartDetailTypeBean> typeBeanList = new ArrayList<>();
    private DetaiiListPresenter mDetaiiListPresenter;
    private DetailListRequest request = new DetailListRequest();

//    private boolean first1 = true;
//    private boolean first2 = true;
//    private boolean first3 = true;

    //=========================================
    @BindView(R.id.star_top_layout)
    StarDetailTopLayout mStarTopLayout;
    @BindView(R.id.star_mind_layout)
    StarDetailMindLayout mStarMindLayout;
    @BindView(R.id.star_about_layout)
    StarDetailAboutLayout mStarAboutLayout;
    boolean isLoadMore;

    @Override
    protected int getLayoutView() {
        //  return R.layout.activity_star_detail;
        return R.layout.activity_star_detail_new;
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

        setToolBarVisible(View.GONE);


    }

    @Override
    public void initUiAndListener() {

        homeStarBean = (HomeStarBean) (getIntent().getExtras().getSerializable(STAR_DETAIL_TYPE));
        request.setStarId(String.valueOf(homeStarBean.getId()));
        mDetaiiListPresenter = new DetaiiListPresenter(this);

        mStarTopLayout.starDetailTopDataLoad(homeStarBean);
        mStarMindLayout.starDetailMindLoad(homeStarBean);
        mStarTopLayout.setStarDetailTopListener(new StarDetailTopLayout.StarDetailTopListener() {
            @Override
            public void back() {
                finish();
            }
        });

        mStarAboutLayout.setStarDetailAboutListener(new StarDetailAboutLayout.StarDetailAboutListener() {
            @Override
            public void setNewVideo() {
                request.setNewVideo(1);
                request.setMostPlay(0);
                dataList.clear();
                request.setPageNum(1);
                isLoadMore = false;
                mDetaiiListPresenter.fetchListByClassId(request);
            }

            @Override
            public void setMoreVideo() {

                request.setNewVideo(0);
                request.setMostPlay(1);
                dataList.clear();
                request.setPageNum(1);
                isLoadMore = false;
                mDetaiiListPresenter.fetchListByClassId(request);
            }

            @Override
            public void onItemClick(DetailListBean.Data data) {
                jumpToVideo(data.getId(), data.getVideoName(), data.getVideoUrl());
            }

            @Override
            public void loadMore() {
                isLoadMore = true;
                new Handler().postDelayed(mLoadRunnable, 100);
            }
        });

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        linearLayoutManager.setSmoothScrollbarEnabled(true);
//        linearLayoutManager.setAutoMeasureEnabled(true);
//        mlistView.setHasFixedSize(true);
//        mlistView.setNestedScrollingEnabled(false);
//        mlistView.setLayoutManager(linearLayoutManager);
//        typeBeanList.add(new StartDetailTypeBean(StartDetailTypeBean.LAYOUT_STAR_DEAIL_1));
//        typeBeanList.add(new StartDetailTypeBean(StartDetailTypeBean.LAYOUT_STAR_DEAIL_2));
//        typeBeanList.add(new StartDetailTypeBean(StartDetailTypeBean.LAYOUT_STAR_DEAIL_3));
//        mStarDetailAdapter = new StarDetailAdapter(mContext, dataList, homeStarBean, typeBeanList, first1, first2, first3);
//        mlistView.setAdapter(mStarDetailAdapter);
//
//        mStarDetailAdapter.setmStarDetailAdapterLisenter(new StarDetailAdapter.StarDetailAdapterLisenter() {
//            @Override
//            public void setNewVideo() {
//                request.setNewVideo(1);
//                request.setMostPlay(0);
//                mDetaiiListPresenter.fetchListByClassId(request);
//            }
//
//            @Override
//            public void setMoreVideo() {
//                request.setNewVideo(0);
//                request.setMostPlay(1);
//                mDetaiiListPresenter.fetchListByClassId(request);
//            }
//
//            @Override
//            public void onItemClick(DetailListBean.Data starBean) {
//                jumpToVideo(starBean.getId(), starBean.getVideoName(), starBean.getVideoUrl());
//            }
//
//            @Override
//            public void back() {
//                finish();
//            }
//        });
//
//

        isLoadMore = false;
        dataList.clear();
        mDetaiiListPresenter.fetchListByClassId(request);


//        mStarDetailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                //这个是适配器自带的上拉加载功能 很方便一个实现方法搞定
//                isLoadMore = true;
//                new Handler().postDelayed(mLoadRunnable, 100);
//            }
//        }, mlistView);
    }


    //加载更多
    static class LoadRunnable implements Runnable {
        WeakReference<StarDetailActivity> mFregmentReference;


        public LoadRunnable(StarDetailActivity starDetailActivity) {
            mFregmentReference = new WeakReference<StarDetailActivity>(starDetailActivity);
        }

        @Override
        public void run() {
            if (mFregmentReference != null) {
                final StarDetailActivity activity = mFregmentReference.get();
                activity.mDetaiiListPresenter.fetchListByClassId(activity.request);
                Log.d("tag", " load Runnable");
            }
        }
    }

    @Override
    public void detailListLoad(DetailListBean listBean) {

        if (isLoadMore) {
            Log.d("tag", "  go  more   -------");
//            if (request.getPageNum() == 1) {
//                dataList.clear();
//            }

            if (listBean.getData().size() > 0) {
                mStarAboutLayout.addDate(listBean.getData());
                request.morePage();
                Log.d("tag", " go more");

            }
           // if (listBean.getData().size() == 0 || page < request.getPageNum()) {
            if (listBean.getData().size() == 0){
                Log.d("tag", listBean.getPages() + "     " + request.getPageNum() + "");
                mStarAboutLayout.loadMoreEnd();
            } else {
                mStarAboutLayout.loadMoreComplete();
            }

        } else {
            request.morePage();
            mStarAboutLayout.starDetailAboutLoad(listBean.getData());
//            first1 = false;
//            first2 = false;
        }
    }


}
