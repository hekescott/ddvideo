package com.baby.app.modules.channel.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;
import com.baby.app.modules.video.page.VideoActivity;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/14.
 */
public class ChannelStarAdapter extends BaseQuickAdapter<HomeStarBean, BaseViewHolder> {
    public ChannelStarAdapter(int layoutResId, @Nullable List<HomeStarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeStarBean item) {
        ImageView userImageView = helper.getView(R.id.user_img_view);
        TextView userNameTextView = helper.getView(R.id.user_name_text_view);
        TextView infoTextView = helper.getView(R.id.user_info_text_view);
        TextView view_num_text_view = helper.getView(R.id.view_num_text_view);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        item.getHeadpic(),
                        userImageView,
                        R.mipmap.loading,
                        R.mipmap.loading,
                        GlideUtils.LOAD_BITMAP
                        );
        userNameTextView.setText(item.getName());
        infoTextView.setText(item.getBriefContext());
        view_num_text_view.setText(item.getVideoNum() + "部电影");
        RecyclerView recyclerView = helper.getView(R.id.c_star_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ChannelStarVideoAdapter channelStarVideoAdapter
                = new ChannelStarVideoAdapter(R.layout.item_channel_star_video, item.getStarVideoList());
        channelStarVideoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DetailListBean.Data starBean = item.getStarVideoList().get(position);
                VideoActivity.startVideo(starBean.getId(),starBean.getVideoName(),starBean.getVideoUrl());
            }
        });
        recyclerView.setAdapter(channelStarVideoAdapter);
    }
}
