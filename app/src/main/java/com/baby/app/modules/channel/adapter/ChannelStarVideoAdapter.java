package com.baby.app.modules.channel.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/14.
 */
public class ChannelStarVideoAdapter extends BaseQuickAdapter<DetailListBean.Data, BaseViewHolder> {
    public ChannelStarVideoAdapter(int layoutResId, @Nullable List<DetailListBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailListBean.Data item) {
        ImageView imageView = helper.getView(R.id.star_video_img_view);
        TextView star_detail_text = helper.getView(R.id.star_video_text);
//        TextView star_detail_count = helper.getView(R.id.star_video_count);
        star_detail_text.setText(item.getVideoName());
//        star_detail_count.setText(item.getPlayNum() + "次播放");
        GlideUtils
                .getInstance()
                .LoadContextRoundAndCeterCropBitmapDefault(mContext,
                        item.getVideoCover(),
                        imageView,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP,
                        5);
    }
}
