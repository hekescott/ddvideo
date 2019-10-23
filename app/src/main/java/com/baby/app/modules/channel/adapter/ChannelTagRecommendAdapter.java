package com.baby.app.modules.channel.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelRecommendSubBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;
import com.baby.app.modules.channel.page.TagListActivity;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/28.
 */

public class ChannelTagRecommendAdapter extends BaseQuickAdapter <ChannelRecommendSubBean,BaseViewHolder> {
    public ChannelTagRecommendAdapter(int layoutResId, @Nullable List<ChannelRecommendSubBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChannelRecommendSubBean item) {
        TextView username = helper.getView(R.id.user_name_text_view);
        username.setText(item.getName());

        TextView introduce = helper.getView(R.id.introduce);
        introduce.setText("上新时间:"+item.getUpdateTime());
        ImageView imageView = helper.getView(R.id.user_img_view);
        GlideUtils
                .getInstance()
                .LoadNewContextBitmap(mContext,
                        item.getPicUrl(),
                        imageView,
                        R.mipmap.loading,
                        R.mipmap.loading,
                        GlideUtils.LOAD_BITMAP);
        TextView describe = helper.getView(R.id.describe);
        describe.setText(item.getIntroduce());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChannelTagBean channelTagBean = new ChannelTagBean();
                channelTagBean.setId(item.getId());
                channelTagBean.setName(item.getName());
                channelTagBean.setPicUrl(item.getPicUrl());
                TagListActivity.startTagList(channelTagBean);
            }
        });

    }
}
