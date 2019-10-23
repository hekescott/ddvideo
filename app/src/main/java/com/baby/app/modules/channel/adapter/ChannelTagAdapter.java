package com.baby.app.modules.channel.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/28.
 */

public class ChannelTagAdapter extends BaseQuickAdapter <ChannelTagBean,BaseViewHolder> {
    public ChannelTagAdapter(int layoutResId, @Nullable List<ChannelTagBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelTagBean item) {
        TextView textView = helper.getView(R.id.tv_channel_tag_view);
        textView.setText(item.getName());

        View bottomView = helper.getView(R.id.channel_bottom_view);
        bottomView.setVisibility(View.GONE);
        ImageView imageView = helper.getView(R.id.c_channel_tag_img);
        GlideUtils
                .getInstance()
                .LoadNewContextBitmap(mContext,
                        item.getPicUrl(),
                        imageView,
                        R.mipmap.loading,
                        R.mipmap.loading,
                        GlideUtils.LOAD_BITMAP);


    }
}
