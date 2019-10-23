package com.baby.app.modules.mine.page;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RelativeLayout;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.mine.PromoteNumBean;
import com.android.baselibrary.util.ResourceUtil;
import com.android.baselibrary.widget.NoScrollRecyclerView;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.PromoteTypeAdapter;
import com.baby.app.modules.mine.bean.PromoteBean;
import com.baby.app.modules.mine.presenter.PromotePresenter;
import com.baby.app.modules.mine.view.IPromoteView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class PromoteActivity extends IBaseActivity implements IPromoteView {


    private PromotePresenter mPromotePresenter;
    @BindView(R.id.promot_list)
    NoScrollRecyclerView mRecyclerView;

    @BindView(R.id.action_back_view)
    RelativeLayout bottomButton;

    private PromoteTypeAdapter mPromoteTypeAdapter;

    private List<PromoteBean> promoteBeanList = new ArrayList<>();
    private PromoteNumBean data;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_promote;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                openActivity(MyPromoteActivity.class);
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("推广").setLeftDrawable(R.mipmap.icon_title_back_white).setRightText("我的推广")
                .setRightTextColor(ResourceUtil.getColor(R.color.white));
        findViewById(com.android.baselibrary.R.id.title_line).setVisibility(View.GONE);
    }

    @Override
    public void initUiAndListener() {
        mPromotePresenter = new PromotePresenter(this);
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ErActivity.class);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        promoteBeanList.add(new PromoteBean(PromoteBean.PROMOT_ROW1));
        promoteBeanList.add(new PromoteBean(PromoteBean.PROMOT_ROW2));
        promoteBeanList.add(new PromoteBean(PromoteBean.PROMOT_ROW3));
        promoteBeanList.add(new PromoteBean(PromoteBean.PROMOT_ROW4));
        promoteBeanList.add(new PromoteBean(PromoteBean.PROMOT_ROW5));
        mPromoteTypeAdapter = new PromoteTypeAdapter(promoteBeanList, mContext, data);
        mRecyclerView.setAdapter(mPromoteTypeAdapter);
//        mPromoteTypeAdapter.setmPromoteTypeAdapterLisenter(new PromoteTypeAdapter.PromoteTypeAdapterLisenter() {
//            @Override
//            public void gotoEr() {
//                openActivity(ErActivity.class);
//            }
//        });
        mPromotePresenter.fetchData();
    }

    @Override
    public void refreshData(PromoteNumBean promoteNumBean) {
        data = promoteNumBean;
        mPromoteTypeAdapter = new PromoteTypeAdapter(promoteBeanList, mContext, data);
        mRecyclerView.setAdapter(mPromoteTypeAdapter);
        mPromoteTypeAdapter.setmPromoteTypeAdapterLisenter(new PromoteTypeAdapter.PromoteTypeAdapterLisenter() {
            @Override
            public void gotoEr() {
                openActivity(ErActivity.class);
            }
        });
    }
}
