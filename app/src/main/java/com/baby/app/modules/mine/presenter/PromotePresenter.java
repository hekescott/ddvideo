package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.mine.PromoteNumBean;
import com.baby.app.modules.mine.view.IPromoteView;


public class PromotePresenter extends BasePresenter {

    private IPromoteView mIPromoteView;

    public PromotePresenter(IPromoteView mIPromoteView) {
        this.mIPromoteView = mIPromoteView;
    }

    @Override
    protected BaseView getView() {
        return mIPromoteView;
    }


    public void fetchData() {
        requestDateNew(NetService.getInstance().getPromoteNum(), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                PromoteNumBean bean = (PromoteNumBean) obj;
                mIPromoteView.refreshData(bean);
            }

            @Override
            public void onFaild(Object obj) {
                mIPromoteView.showNetError();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mIPromoteView.showNetError();

            }
        });
    }


}
