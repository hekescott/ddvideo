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
 * Created by yongqianggeng on 2018/10/4.
 */

public class TagListAdapter extends BaseQuickAdapter<DetailListBean.Data, BaseViewHolder> {
    public TagListAdapter(int layoutResId, @Nullable List<DetailListBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailListBean.Data item) {
//        TextView tag1 = helper.getView(R.id.video_tag_1);
//        TextView tag2 = helper.getView(R.id.video_tag_2);
//        TextView tag3 = helper.getView(R.id.video_tag_3);
//        TextView tag4 = helper.getView(R.id.video_tag_4);
//
//        RelativeLayout video_tag_1_back = helper.getView(R.id.video_tag_1_back);
//        RelativeLayout video_tag_2_back = helper.getView(R.id.video_tag_2_back);
//        RelativeLayout video_tag_3_back = helper.getView(R.id.video_tag_3_back);
//        RelativeLayout video_tag_4_back = helper.getView(R.id.video_tag_4_back);
//
////        tag1.setVisibility(View.INVISIBLE);
////        tag2.setVisibility(View.INVISIBLE);
////        tag3.setVisibility(View.INVISIBLE);
////        tag4.setVisibility(View.INVISIBLE);
//
//        video_tag_1_back.setVisibility(View.INVISIBLE);
//        video_tag_2_back.setVisibility(View.INVISIBLE);
//        video_tag_3_back.setVisibility(View.INVISIBLE);
//        video_tag_4_back.setVisibility(View.INVISIBLE);
//
//
//        TextView video_count_view = helper.getView(R.id.video_count_view);

        TextView tv_class_function = helper.getView(R.id.tv_class_function);

        ImageView iv_class_function = helper.getView(R.id.iv_class_function);
        GlideUtils
                .getInstance()
                .LoadContextRoundAndCeterCropBitmapDefault(mContext,
                        item.getVideoCover(),
                        iv_class_function,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP, 5);
        tv_class_function.setText(item.getVideoName());
//        String[] seperates = item.getTags().split(",");
//        for (int i = 0; i <seperates.length ; i++) {
//            if (i ==0) {
//                video_tag_1_back.setVisibility(View.VISIBLE);
//                tag1.setText(seperates[i]);
//            } else if (i ==1) {
//                video_tag_2_back.setVisibility(View.VISIBLE);
//                tag2.setText(seperates[i]);
//            } else if (i ==2) {
//                video_tag_3_back.setVisibility(View.VISIBLE);
//                tag3.setText(seperates[i]);
//            } else if (i ==3) {
//                video_tag_4_back.setVisibility(View.VISIBLE);
//                tag4.setText(seperates[i]);
//            }
//        }
    }
}
