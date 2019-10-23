package com.baby.app.modules.mine.page;


import android.widget.TextView;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.android.baselibrary.service.bean.mine.NotificationDetailBean;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.NotificationAdapter;
import com.baby.app.modules.mine.presenter.NoticeDetailPresenter;
import com.baby.app.modules.mine.view.INoticeDetailView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class NotificationDetailActivity extends IBaseActivity implements INoticeDetailView {

    @BindView(R.id.detail_text)
    TextView textView;

    private NoticeDetailPresenter mNoticeDetailPresenter;

    private NotificationDetailBean.Data notification;

    private String id;

    public static final String NOTIFICATION_DETAIL_TYPE = "NOTIFICATION_DETAIL_TYPE";


    @Override
    protected int getLayoutView() {
        return R.layout.activity_notification_detail;
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
        id = getIntent().getExtras().getString(NOTIFICATION_DETAIL_TYPE);
    }

    @Override
    public void initUiAndListener() {
        mNoticeDetailPresenter = new NoticeDetailPresenter(this);
        mNoticeDetailPresenter.fetchNoticData(id);
    }


    @Override
    public void refreshNotic(NotificationDetailBean notificationBean) {
        this.notification = notificationBean.getData();
        if (notification.getNoticeTitle() != null) {
            mTitleBuilder.setMiddleTitleText(notification.getNoticeTitle())
                    .setLeftDrawable(R.mipmap.icon_title_back_white);
        } else {
            mTitleBuilder.setMiddleTitleText("通知详情")
                    .setLeftDrawable(R.mipmap.icon_title_back_white);
        }
        if (notification.getNoticeContent() != null) {
            textView.setText(notification.getNoticeContent());
        }
    }


}
