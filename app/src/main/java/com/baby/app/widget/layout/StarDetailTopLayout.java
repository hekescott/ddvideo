package com.baby.app.widget.layout;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-06-21/19:04
 * @Description: 详情顶部
 */
public class StarDetailTopLayout extends LinearLayout {
    View mView;
    Context mContext;
    StarDetailTopListener mStarDetailTopListener;

    public StarDetailTopLayout(Context context) {
        super(context);
        this.mContext = context;
        iniView();
    }

    public StarDetailTopLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        iniView();
    }

    public StarDetailTopLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        iniView();

    }

    public void setStarDetailTopListener(StarDetailTopListener starDetailTopListener) {
        this.mStarDetailTopListener = starDetailTopListener;
    }

    private void iniView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.star_detail_layout_1, this);
    }


    public void starDetailTopDataLoad(HomeStarBean mHomeStarBean) {
        RelativeLayout back_view = mView.findViewById(R.id.back_view);
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStarDetailTopListener.back();
            }
        });
        ImageView imageView = mView.findViewById(R.id.star_image_view);
        TextView countView = mView.findViewById(R.id.star_count_view);
        TextView nameView = mView.findViewById(R.id.star_name_view);
        TextView dataView = mView.findViewById(R.id.star_data_view);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        mHomeStarBean.getHeadpic(),
                        imageView,
                        R.mipmap.ic_head_l,
                        R.mipmap.ic_head_l);
        countView.setText(mHomeStarBean.getVideoNum() + "部影片");
        nameView.setText(mHomeStarBean.getName());
//                    dataView.setText("身高:"+mHomeStarBean.getHeightNum()+" 三围:"+mHomeStarBean.getBwh()+" 罩杯:"+mHomeStarBean.getCupName());
        dataView.setText("身高:" + mHomeStarBean.getHeightNum());
    }

    public interface StarDetailTopListener {
        void back();
    }
}
