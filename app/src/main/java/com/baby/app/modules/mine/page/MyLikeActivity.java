package com.baby.app.modules.mine.page;


import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.util.ResourceUtil;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class MyLikeActivity extends IBaseActivity {


    @Override
    protected int getLayoutView() {
        return R.layout.activity_my_like;
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
        mTitleBuilder.setMiddleTitleText("我的喜欢")
                .setLeftImageViewEnabled(true).setRightText("编辑").setRightTextColor(ResourceUtil.getColor(R.color.white));
    }

    @Override
    public void initUiAndListener() {

    }
}
