package com.baby.app.modules.channel.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.AllTagsBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 女优
 */

public class AllTagsAdapter extends BaseQuickAdapter <AllTagsBean,BaseViewHolder>{
    public AllTagsAdapter(int layoutResId, @Nullable List<AllTagsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllTagsBean item) {
        ImageView imageView = helper.getView(R.id.imageView);
        TextView textView = helper.getView(R.id.tag);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        item.getPicUrl(),
                        imageView,
                        R.mipmap.loading,
                        R.mipmap.loading);
        textView.setText(item.getName());
    }
}
