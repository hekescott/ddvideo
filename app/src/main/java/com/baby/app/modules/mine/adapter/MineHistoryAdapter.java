package com.baby.app.modules.mine.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/29.
 */

public class MineHistoryAdapter extends BaseQuickAdapter<HistoryBean,BaseViewHolder> {
    public MineHistoryAdapter(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {
        ImageView histroy_img_view = helper.getView(R.id.histroy_img_view);
        TextView histroy_tex_view = helper.getView(R.id.histroy_tex_view);
        GlideUtils
                .getInstance()
                .LoadContextRoundAndCeterCropBitmapDefault(mContext,
                        item.getVideoCover(),
                        histroy_img_view,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP,
                        5);
        histroy_tex_view.setText(item.getVideoName());
    }
}
