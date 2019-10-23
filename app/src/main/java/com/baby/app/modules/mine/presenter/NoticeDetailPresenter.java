package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.mine.NotificationDetailBean;
import com.baby.app.modules.mine.view.INoticeDetailView;

public class NoticeDetailPresenter extends BasePresenter {

    private INoticeDetailView iNoticeView;

    public NoticeDetailPresenter(INoticeDetailView iNoticeView) {
        this.iNoticeView = iNoticeView;
    }

    @Override
    protected BaseView getView() {
        return iNoticeView;
    }


    public void fetchNoticData(String id) {
        requestDateNew(NetService.getInstance().getNoticeDetail(id), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                NotificationDetailBean bean = (NotificationDetailBean) obj;
                iNoticeView.refreshNotic(bean);
            }

            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }
}
