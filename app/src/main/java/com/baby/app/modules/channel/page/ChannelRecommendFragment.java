package com.baby.app.modules.channel.page;

import android.content.Context;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTypeBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.service.request.DetailListRequest;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.widget.NoScrollRecyclerView;
import com.android.baselibrary.widget.RefreshLayout;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.transformer.CardPageTransformer;
import com.baby.app.R;
import com.baby.app.application.IBaseFragment;
import com.baby.app.modules.channel.HomeBridge;
import com.baby.app.modules.channel.adapter.ChannelRecommendAdapter;
import com.baby.app.modules.channel.presenter.ChannelPresenter;
import com.baby.app.modules.channel.view.ChannelView;
import com.baby.app.modules.home.persenter.DetaiiListPresenter;
import com.baby.app.modules.home.view.DetailListView;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/19.
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class ChannelRecommendFragment extends IBaseFragment implements SwipeRefreshLayout.OnRefreshListener, ChannelView, HomeBridge.Callback, DetailListView {

//    private ConvenientBanner convenientBanner;
    private RefreshLayout swipeLayout;

    private NoScrollRecyclerView rv_home_list;
    private RelativeLayout rl_home_list;

    private CardPageTransformer mTransformer;

    private ChannelRecommendAdapter multipleItemAdapter;

    private View rightBtn;

    private List<ChannelTypeBean> listItems = new ArrayList<>();
    // banner图片数据
    private List<String> data_banner_string = new ArrayList<>();

    private ChannelDataBean mChannelDataBean;


    private ChannelPresenter mChannelPresenter;

    //获取人气明星影片
    private List<HomeStarBean> mStarBeanList;//人气明星
    private DetailListRequest request = new DetailListRequest();
    private DetaiiListPresenter mDetaiiListPresenter;
    private int mStarVideoRequestCount;

    public ChannelRecommendFragment() {
        HomeBridge.setCallback(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_channel_recommend;
    }

    @Override
    public void initUiAndListener(View view) {

        mChannelPresenter = new ChannelPresenter(this);
        mDetaiiListPresenter = new DetaiiListPresenter(this);
//        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.c_swipeLayout);

        rv_home_list = (NoScrollRecyclerView) view.findViewById(R.id.c_rv_home_list);
        rl_home_list = (RelativeLayout) view.findViewById(R.id.c_rl_home_list);

//        rightBtn = view.findViewById(R.id.c_right_btn);
//        rightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openActivity(ChannelTagActivity.class);
//            }
//        });

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);

        initAdapter();
//        initBanner();
        mChannelPresenter.fetchData();

    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rv_home_list.setHasFixedSize(true);
        rv_home_list.setNestedScrollingEnabled(false);
        rv_home_list.setLayoutManager(linearLayoutManager);
        multipleItemAdapter = new ChannelRecommendAdapter(listItems, mChannelDataBean, mContext);
        rv_home_list.setAdapter(multipleItemAdapter);
    }

    /**
     * 初始化banner
     */
    private void initBanner() {

        for (int position = 0; position < 2; position++) {
            data_banner_string.add("11");
        }

        mTransformer = new CardPageTransformer(0.82f, 0.145f);
//        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderViewChannel>() {
////            @Override
////            public NetworkImageHolderViewChannel createHolder() {
////                return new NetworkImageHolderViewChannel();
////            }
////        }, data_banner_string);
////
////        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
////            @Override
////            public void onItemClick(int position) {
////
////            }
////        });
////        convenientBanner.setPageIndicator(new int[]{R.mipmap.icon_home_banner_a,
////                R.mipmap.icon_home_banner_aa});
////        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
////        convenientBanner.startTurning(5000);
////        convenientBanner.setManualPageable(true);
////        convenientBanner.setPageTransformer(mTransformer);
////        if (convenientBanner.getViewPager() != null) {
////            convenientBanner.getViewPager().setClipToPadding(false);
////            convenientBanner.getViewPager().setClipChildren(false);
////            try {
////                ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            convenientBanner.getViewPager().setOffscreenPageLimit(3);
////        }
////
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                showBanner();
////            }
////        }, 10);
    }

//    public void showBanner() {
//
//
//        // 此处不能直接notifyDataSetChanged这样的话会有一些缓存的问题，需要重新为adapter设置资源
//        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderViewChannel>() {
//            @Override
//            public NetworkImageHolderViewChannel createHolder() {
//                return new NetworkImageHolderViewChannel();
//            }
//        }, data_banner_string);
//
//        //
//    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mChannelPresenter.fetchData();
            }
        }, 100);
    }

    @Override
    public void refresh(ChannelDataBean channelDataBean) {
        swipeLayout.setRefreshing(false);
        if (mStarBeanList == null) {
            callback(HomeBridge.getStarList());
        }
        channelDataBean.setStarBeanList(mStarBeanList);
        mChannelDataBean = channelDataBean;
        //TODO:先处理banner
        data_banner_string.clear();
        listItems.clear();
//        for (HomeBannerBean bannerBean : channelDataBean.getData().getBannerList()) {
//            data_banner_string.add(bannerBean.getPicUrl());
//        }
//        showBanner();
        //TODO:处理列表
        if (channelDataBean.getData() != null && channelDataBean.getData().getRecommandSubjectList() != null && channelDataBean.getData().getRecommandSubjectList().size() > 0) {
            listItems.add(new ChannelTypeBean(ChannelTypeBean.LAYOUT_RECOMMEND));
        }
        listItems.add(new ChannelTypeBean(ChannelTypeBean.LAYOUT_BANNER));
        if (channelDataBean.getData() != null && channelDataBean.getData().getHotTagList() != null && channelDataBean.getData().getHotTagList().size() > 0) {
            listItems.add(new ChannelTypeBean(ChannelTypeBean.LAYOUT_TAG));
        }
//        if (channelDataBean.getData() != null && channelDataBean.getData().getCareTagList() != null && channelDataBean.getData().getCareTagList().size() > 0) {
//            listItems.add(new ChannelTypeBean(ChannelTypeBean.LAYOUT_LIKE));
//        }
        if (channelDataBean.getData() != null && channelDataBean.getData().getStarBeanList() != null && channelDataBean.getData().getStarBeanList().size() > 0) {
            listItems.add(new ChannelTypeBean(ChannelTypeBean.LAYOUT_STAR));
        }
        multipleItemAdapter = new ChannelRecommendAdapter(listItems, channelDataBean, mContext);
        rv_home_list.setAdapter(multipleItemAdapter);
        multipleItemAdapter.setmChannelRecommendAdapterLisenter(new ChannelRecommendAdapter.ChannelRecommendAdapterLisenter() {
            @Override
            public void onBannerClick(HomeBannerBean bannerBean) {
                mChannelPresenter.clickAd(String.valueOf(bannerBean.getId()));
                jumpToTagActivity(bannerBean);
            }
        });
    }

    @Override
    public void refreshTagClass(TagClassBean tagClassBean) {

    }

    @Override
    public void refreshChannelTag(int id, ChannelTagDataBean channelTagDataBean) {

    }

    @Override
    public void showNetError() {
        super.showNetError();
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void callback(List<HomeStarBean> starList) {
        this.mStarBeanList = starList;
        if (mStarBeanList != null) {
            mStarVideoRequestCount = mStarBeanList.size();
            for (int i = 0; i < Math.min(mStarVideoRequestCount, 4); i++) {
                if (mDetaiiListPresenter == null) {
                    mDetaiiListPresenter = new DetaiiListPresenter(this);
                }
                if (request == null) {
                    request = new DetailListRequest();
                }
                request.setStarId(String.valueOf(mStarBeanList.get(i).getId()));
                mDetaiiListPresenter.fetchListByClassId(request);
            }
        }
//        if (mChannelDataBean != null) {
//            refresh(mChannelDataBean);
//        }
    }

    @Override
    public void detailListLoad(DetailListBean listBean) {
        mStarVideoRequestCount--;
        for (HomeStarBean homeStarBean : mStarBeanList) {
            if (TextUtils.equals(String.valueOf(homeStarBean.getId()), listBean.getStartId())) {
                homeStarBean.setStarVideoList(listBean.getData());
            }
        }
        if (mStarVideoRequestCount == 0) {//数据全部获取到
            if (mChannelDataBean != null) {
                refresh(mChannelDataBean);
            }
        }
    }

    // banner加载图片适配
    class NetworkImageHolderViewChannel implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_banner_item, null);
            imageView = (ImageView) view.findViewById(R.id.iv_banner_img);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            GlideUtils
                    .getInstance()
                    .LoadContextRoundAndCeterCropBitmapDefault(mContext,
                            data,
                            imageView,
                            R.mipmap.loading,
                            R.mipmap.loading,
                            GlideUtils.LOAD_BITMAP, 8);
        }
    }


}
