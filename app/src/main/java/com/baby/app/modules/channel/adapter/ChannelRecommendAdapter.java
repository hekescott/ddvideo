package com.baby.app.modules.channel.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTypeBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.util.ActivityUtil;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.transformer.CardPageTransformer;
import com.baby.app.R;
import com.baby.app.modules.channel.page.AllTagsActivity;
import com.baby.app.modules.channel.page.TagListActivity;
import com.baby.app.modules.home.page.HomeStarActivity;
import com.baby.app.modules.home.page.StarDetailActivity;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/28.
 */

public class ChannelRecommendAdapter extends BaseMultiItemQuickAdapter<ChannelTypeBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private Context mContext;
    private List<ChannelTypeBean> channelTypeBeans;

    private ChannelDataBean dataBean;
    private ChannelRecommendAdapterLisenter mChannelRecommendAdapterLisenter;

    public void setmChannelRecommendAdapterLisenter(ChannelRecommendAdapter.ChannelRecommendAdapterLisenter mChannelRecommendAdapterLisenter) {
        this.mChannelRecommendAdapterLisenter = mChannelRecommendAdapterLisenter;
    }


    public ChannelRecommendAdapter(List<ChannelTypeBean> dataList, ChannelDataBean dataBean,
                                   Context context) {
        super(dataList);
        this.mContext = context;
        this.channelTypeBeans = dataList;
        this.dataBean = dataBean;

        int size = channelTypeBeans.size();
        for (int i = 0; i < size; i++) {
            switch (channelTypeBeans.get(i).getItemType()) {
                case ChannelTypeBean.LAYOUT_RECOMMEND:
                    addItemType(ChannelTypeBean.LAYOUT_RECOMMEND, R.layout.channel_recommend_layout);
                    break;
                case ChannelTypeBean.LAYOUT_BANNER:
                    addItemType(ChannelTypeBean.LAYOUT_BANNER, R.layout.channel_list_item_banner);
                    break;
                case ChannelTypeBean.LAYOUT_TAG:
                    addItemType(ChannelTypeBean.LAYOUT_TAG, R.layout.channel_tag_layout);
                    break;
                case ChannelTypeBean.LAYOUT_STAR:
                    addItemType(ChannelTypeBean.LAYOUT_STAR, R.layout.channel_tag_layout);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChannelTypeBean item) {
        switch (helper.getItemViewType()) {
            case ChannelTypeBean.LAYOUT_RECOMMEND: {//推荐
                TextView c_tag_text_view = helper.getView(R.id.c_tag_text_view);
                c_tag_text_view.setText("2019必看专题");
                final RecyclerView mRecyclerView = helper.getView(R.id.c_recycler_view);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                ChannelTagRecommendAdapter channelTagAdapter =
                        new ChannelTagRecommendAdapter(R.layout.item_channel_recommend_layout,
                                dataBean.getData().getRecommandSubjectList());
                mRecyclerView.setAdapter(channelTagAdapter);
                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.right = ScreenUtil.dip2px(10f);
                    }
                });
            }
            break;
            case ChannelTypeBean.LAYOUT_BANNER: {//banner
                final List<String> data_banner_string = new ArrayList<>();
                for (int position = 0; position < 2; position++) {
                    data_banner_string.add("11");
                }
                if (dataBean.getData().getBannerList() != null
                        && dataBean.getData().getBannerList().size() > 0) {
                    data_banner_string.clear();
                    for (HomeBannerBean bannerBean : dataBean.getData().getBannerList()) {
                        data_banner_string.add(bannerBean.getPicUrl());
                    }
                }
                final ConvenientBanner convenientBanner = helper.getView(R.id.convenientBanner);
                CardPageTransformer mTransformer = new CardPageTransformer(0.82f, 0.145f);
                convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderViewChannel>() {
                    @Override
                    public NetworkImageHolderViewChannel createHolder() {
                        return new NetworkImageHolderViewChannel();
                    }
                }, data_banner_string);

                convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        HomeBannerBean tempBannerBean = dataBean.getData().getBannerList().get(position);
                        if (mChannelRecommendAdapterLisenter!=null) {
                            mChannelRecommendAdapterLisenter.onBannerClick(tempBannerBean);
                        }
                    }
                });
                convenientBanner.setPageIndicator(new int[]{R.mipmap.icon_home_banner_a,
                        R.mipmap.icon_home_banner_aa});
                convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                convenientBanner.startTurning(5000);
                convenientBanner.setManualPageable(true);
                convenientBanner.setPageTransformer(mTransformer);
                if (convenientBanner.getViewPager() != null) {
                    convenientBanner.getViewPager().setClipToPadding(false);
                    convenientBanner.getViewPager().setClipChildren(false);
                    try {
                        ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    convenientBanner.getViewPager().setOffscreenPageLimit(3);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 此处不能直接notifyDataSetChanged这样的话会有一些缓存的问题，需要重新为adapter设置资源
                        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderViewChannel>() {
                            @Override
                            public NetworkImageHolderViewChannel createHolder() {
                                return new NetworkImageHolderViewChannel();
                            }
                        }, data_banner_string);

                        //
                    }
                }, 10);
            }
            break;
            case ChannelTypeBean.LAYOUT_TAG: {
                ImageView c_tag_img_view = helper.getView(R.id.c_tag_img_view);
                c_tag_img_view.setImageResource(R.mipmap.channel_hot);
                TextView c_tag_text_view = helper.getView(R.id.c_tag_text_view);
                c_tag_text_view.setText("热门专题");
                final RecyclerView mRecyclerView = helper.getView(R.id.c_recycler_view);
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                               RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.bottom = ScreenUtil.dip2px(mContext, 20);
                    }
                });
                ChannelTagAdapter channelTagAdapter =
                        new ChannelTagAdapter(R.layout.item_channel_tag_layout,
                                dataBean.getData().getHotTagList());
                mRecyclerView.setAdapter(channelTagAdapter);
                channelTagAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        TagListActivity.startTagList(dataBean.getData().getHotTagList().get(position));
                    }
                });
                helper.getView(R.id.more_back_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllTagsActivity.startAllTags();
                    }
                });
            }
            break;
            case ChannelTypeBean.LAYOUT_STAR: {
                ImageView c_tag_img_view = helper.getView(R.id.c_tag_img_view);
                c_tag_img_view.setImageResource(R.mipmap.channel_like);
                TextView c_tag_text_view = helper.getView(R.id.c_tag_text_view);
                c_tag_text_view.setText("人气明星");
                final RecyclerView mRecyclerView = helper.getView(R.id.c_recycler_view);
//                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
//                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//                    @Override
//                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                                               RecyclerView.State state) {
//                        super.getItemOffsets(outRect, view, parent, state);
//                        outRect.bottom = ScreenUtil.dip2px(mContext, 20);
//                    }
//                });
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                        LinearLayoutManager.VERTICAL, false));
                ChannelStarAdapter channelStarAdapter =
                        new ChannelStarAdapter(R.layout.item_channel_star_layout,
                                dataBean.getData().getStarBeanList());
                mRecyclerView.setAdapter(channelStarAdapter);
                channelStarAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(StarDetailActivity.STAR_DETAIL_TYPE,
                                dataBean.getData().getStarBeanList().get(position));
                        ActivityUtil.navigateTo(StarDetailActivity.class, bundle);
                    }
                });
                helper.getView(R.id.more_back_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeStarActivity.startHomeStar();
                    }
                });
            }
            break;
        }
    }

    // banner加载图片适配
    class NetworkImageHolderViewChannel implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.channel_banner_item, null);
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

    public interface ChannelRecommendAdapterLisenter {
        void onBannerClick(HomeBannerBean bannerBean);
    }
}
