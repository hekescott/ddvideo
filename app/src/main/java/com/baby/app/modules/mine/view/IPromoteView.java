package com.baby.app.modules.mine.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.mine.PromoteNumBean;

/**
 * Created by yongqianggeng on 2018/10/19.
 */

public interface IPromoteView extends BaseView {
    void refreshData(PromoteNumBean promoteNumBean);
}
