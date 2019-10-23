package com.baby.app.modules.channel.adapter;

import androidx.annotation.Nullable;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/4.
 * 标签筛选页面
 */
@Deprecated
public class TagListTopAdapter extends BaseQuickAdapter<ChannelTagBean,BaseViewHolder> {
    public TagListTopAdapter(int layoutResId, @Nullable List<ChannelTagBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelTagBean item) {
//        TextView textView = helper.getView(R.id.tag_txt_view);
//        textView.setText(item.getName()+"*");
    }
}
