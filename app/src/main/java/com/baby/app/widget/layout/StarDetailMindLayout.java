package com.baby.app.widget.layout;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.baby.app.R;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-06-21/19:21
 * @Description: 详情更多
 */
public class StarDetailMindLayout extends LinearLayout {
    View mView;
    Context mContext;
    private boolean isShow;
    RelativeLayout star_more_btn;

    public StarDetailMindLayout(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public StarDetailMindLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public StarDetailMindLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.star_detail_layout_2, this);
    }


    public void starDetailMindLoad(HomeStarBean homeStarBean) {
        final TextView textView = mView.findViewById(R.id.star_content_view);
        textView.setText(homeStarBean.getBriefContext());
        star_more_btn = mView.findViewById(R.id.star_more_btn);
        star_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setMaxLines(100);
                star_more_btn.setVisibility(View.INVISIBLE);
            }
        });
    }
}
