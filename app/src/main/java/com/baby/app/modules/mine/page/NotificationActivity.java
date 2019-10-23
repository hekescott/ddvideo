package com.baby.app.modules.mine.page;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.NotificationAdapter;
import com.baby.app.modules.mine.presenter.NoticePresenter;
import com.baby.app.modules.mine.view.INoticeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class NotificationActivity extends IBaseActivity implements INoticeView {

    @BindView(R.id.n_recycler_view)
    RecyclerView mRecyclerView;

    private NoticePresenter mNoticePresenter;

    private NotificationAdapter mNotificationAdapter;

    private List<NotificationBean.Data>notificationBeanList = new ArrayList<>();

    @Override
    protected int getLayoutView() {
        return R.layout.activity_notification;
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
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("通知")
                .setLeftDrawable(R.mipmap.icon_title_back_white);
    }

    @Override
    public void initUiAndListener() {
        mNoticePresenter = new NoticePresenter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mNotificationAdapter = new NotificationAdapter(R.layout.item_notification_layout, notificationBeanList);
        mRecyclerView.setAdapter(mNotificationAdapter);
        mNotificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(NotificationDetailActivity.NOTIFICATION_DETAIL_TYPE,  notificationBeanList.get(position).getId());
                openActivity(NotificationDetailActivity.class, bundle);
            }
        });

        mNoticePresenter.fetchData();
    }


    @Override
    public void refresh(NotificationBean notificationBean) {
        mNotificationAdapter.addData(notificationBean.getData());
    }
}
