package com.baby.app.widget.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.util.ScreenUtil;
import com.baby.app.R;
import com.baby.app.modules.home.adapter.StarDetailListAdapter;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-06-21/19:27
 * @Description: 相关影片
 */
public class StarDetailAboutLayout extends LinearLayout {
    View mView;
    Context mContext;
    boolean isShow;
    StarDetailAboutListener mStarDetailAboutListener;
    StarDetailListAdapter mStarDetailListAdapter;

    RecyclerView mRecyclerView;

    public StarDetailAboutLayout(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public StarDetailAboutLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public StarDetailAboutLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    public void setStarDetailAboutListener(StarDetailAboutListener starDetailAboutListener) {
        this.mStarDetailAboutListener = starDetailAboutListener;
    }

    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.star_detail_layout_3, this);
    }


    public void starDetailAboutLoad(final List<DetailListBean.Data> dataList) {
        final TextView star_new_btn_text = mView.findViewById(R.id.star_new_btn_text);
        final RelativeLayout star_more_btn = mView.findViewById(R.id.star_more_btn);
        final TextView star_more_btn_text = mView.findViewById(R.id.star_more_btn_text);

        final RelativeLayout star_new_btn = mView.findViewById(R.id.star_new_btn);
        star_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star_new_btn.setBackgroundResource(R.drawable.star_detail_selected);
                star_more_btn.setBackgroundResource(R.drawable.star_detail_nomal);
                star_new_btn_text.setTextColor(Color.parseColor("#FFFA7334"));
                star_more_btn_text.setTextColor(Color.parseColor("#FF000000"));
                if (mStarDetailAboutListener != null) {
                    mStarDetailAboutListener.setNewVideo();
                }
            }
        });
        star_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star_new_btn.setBackgroundResource(R.drawable.star_detail_nomal);
                star_more_btn.setBackgroundResource(R.drawable.star_detail_selected);
                star_more_btn_text.setTextColor(Color.parseColor("#FFFA7334"));
                star_new_btn_text.setTextColor(Color.parseColor("#FF000000"));
                if (mStarDetailAboutListener != null) {
                    mStarDetailAboutListener.setMoreVideo();
                }
            }
        });

        mRecyclerView = mView.findViewById(R.id.star_detial_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 10);
            }
        });
        mStarDetailListAdapter = new StarDetailListAdapter(R.layout.item_star_detail, dataList);
        mStarDetailListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mStarDetailAboutListener.onItemClick(dataList.get(position));
            }
        });
        mRecyclerView.setAdapter(mStarDetailListAdapter);
        mRecyclerView.setFocusable(false);

        mStarDetailListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //这个是适配器自带的上拉加载功能 很方便一个实现方法搞定
                mStarDetailAboutListener.loadMore();
            }
        }, mRecyclerView);

    }


    public void addDate(List<DetailListBean.Data> dataList) {
        mStarDetailListAdapter.addData(dataList);
    }

    public void loadMoreEnd() {
        mStarDetailListAdapter.loadMoreEnd();
    }

    public void loadMoreComplete() {
        mStarDetailListAdapter.loadMoreComplete();
    }


    public interface StarDetailAboutListener {
        void setNewVideo();

        void setMoreVideo();

        void onItemClick(DetailListBean.Data data);

        void loadMore();
    }
}
