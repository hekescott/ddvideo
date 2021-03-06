package com.baby.app.application;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.baselibrary.AppCommon;
import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.service.bean.video.VideoInComeBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.BuildConfig;
import com.baby.app.login.LoginActivity;
import com.baby.app.modules.channel.page.TagListActivity;
import com.baby.app.modules.home.MainActivity;
import com.baby.app.modules.mine.page.ErActivity;
import com.baby.app.modules.mine.page.VipActivity;
import com.baby.app.modules.video.page.VideoActivity;
import com.baby.app.util.DownloadAppUtils;
import com.google.gson.Gson;

/**
 * Created by yongqianggeng on 2018/10/9.
 */

public class IBaseActivity extends BaseActivity {

    private BasePresenter mBasePresenter;

    @Override
    protected int getLayoutView() {
        return 0;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {

    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {

    }

    @Override
    public void initUiAndListener() {
        mBasePresenter = new BasePresenter() {
            @Override
            protected BaseView getView() {
                return null;
            }
        };
    }

    public void reStartNamalLogin() {

    }

    public void jumpToVideo(int id, String name, String url) {
        VideoInComeBean videoInComeBean = new VideoInComeBean();
        videoInComeBean.setId(id);
        videoInComeBean.setVideoName(name);
        videoInComeBean.setVideoUrl(url);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VideoActivity.VIDEO_KEY, videoInComeBean);
        openActivity(VideoActivity.class, bundle);
    }

    private int intentFlag = 0;

    public void jumpToUserTagActivity(LoginBean.BannerBean homeBannerBean) {
        if (homeBannerBean.getLinkType().equals("1")) {
            String linkurl = homeBannerBean.getLinkUrl();
            if (!TextUtils.isEmpty(linkurl) && linkurl.startsWith("http")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(homeBannerBean.getLinkUrl())));
            } else {
                LoginBean.LinkUrlBean linkUrlBean = new Gson().fromJson(linkurl, LoginBean.LinkUrlBean.class);
                jumpToAppOrDownloadApp(linkUrlBean.androidSchame, linkUrlBean.androidUrl);
            }
        } else {
            switch (Integer.parseInt(homeBannerBean.getLinkType())) {
                case 2: //内部影片
                    jumpToVideo(Integer.parseInt(homeBannerBean.getLinkUrl()), "", null);
                    break;
                case 3:
                    openActivity(VipActivity.class);
                    break;
                case 4:
                    openActivity(ErActivity.class);
                    break;
                case 5: {
                    if (homeBannerBean.getTagName() != null && homeBannerBean.getTagName().length() > 0) {
                        ChannelTagBean channelTagBean = new ChannelTagBean();
                        channelTagBean.setId(Integer.parseInt(homeBannerBean.getTagId()));
                        channelTagBean.setName(homeBannerBean.getTagName());
                        TagListActivity.startTagList(channelTagBean);
                    } else {
                        openActivity(TagListActivity.class);
                    }

                }
                break;
            }
        }
    }


    public void jumpToAppOrDownloadApp(String schame, String downloadUrl) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(schame));
        PackageManager pm = this.getPackageManager();
        ComponentName cn = intent.resolveActivity(pm);
        if (cn == null) {
            String appName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.length());
            DownloadAppUtils.downloadForAutoInstall(this, downloadUrl, appName, appName + "正在下载中");
            ToastUtil.showToast("下载任务已执行,请在下拉列表中查看");
        } else {
            startActivity(intent);
        }
    }

    public void jumpToTagActivity(HomeBannerBean homeBannerBean) {
        if (homeBannerBean.getLinkType() == 1) {
            //TODO:这里需要判断类型
            Bundle bundle = new Bundle();
            bundle.putString("url", homeBannerBean.getLinkUrl());
            openActivity(CommonWebViewActivity.class, bundle);
        } else {
            switch (homeBannerBean.getLinkType()) {
                case 2: //内部影片
                    jumpToVideo(Integer.parseInt(homeBannerBean.getLinkUrl()), "", null);
                    break;
                case 3:
                    openActivity(VipActivity.class);
                    break;
                case 4:
                    openActivity(ErActivity.class);
                    break;
                case 5: {
//                                    ChannelTagBean channelTagBean = new ChannelTagBean();
//                                    channelTagBean.setId(Integer.parseInt(homeBannerBean
//                                    .getLinkUrl()));
////                                    channelTagBean.setName(homeBannerBean.getLinkTypeName());
//                                    Bundle bundle = new Bundle();
//                                    List<ChannelTagBean> list = new ArrayList<>();
//                                    list.add(channelTagBean);
//                                    bundle.putSerializable(TagListActivity.TAG_LIST_KEY,
//                                    (Serializable) list);
                    openActivity(TagListActivity.class);
                }
                break;
            }
        }
    }

    @Override
    public void gotoLogin() {
        super.gotoLogin();

        //TODO:登录过期需要重新登录
        AppCommon.showDialog(mContext, "提示", "您的登录已过期，是否重新登录", "确定", "取消",
                new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        jumpToLogin();

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //TODO:登录过期需要重新登录
                        showDialogLoading();
                        if (mBasePresenter == null) {
                            mBasePresenter = new BasePresenter() {
                                @Override
                                protected BaseView getView() {
                                    return null;
                                }
                            };
                        }
                        mBasePresenter.requestDateNew(NetService.getInstance().deviceInfo(), "",
                                new BaseCallBack() {
                                    @Override
                                    public void onSuccess(Object obj) {
                                        hideDialogLoading();
                                        LoginBean bean = (LoginBean) obj;
                                        UserStorage.getInstance().touristLogin(bean);
                                        reStartNamalLogin();
                                    }

                                    @Override
                                    public void onFaild(Object obj) {
                                        hideDialogLoading();
                                    }

                                    @Override
                                    public void onNetWorkError(String errorMsg) {
                                        hideDialogLoading();
                                    }
                                });
                    }
                });
    }

    public void jumpToLogin(boolean isSplash) {
        if (BuildConfig.SERVER_DEGUB) {
            openActivity(MainActivity.class);
        } else {
            if (isSplash) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(LoginActivity.SPLASH_KEY, true);
                openActivity(LoginActivity.class, bundle);
            } else {
                openActivity(LoginActivity.class);
            }
        }

    }

    public void jumpToLogin() {
        openActivity(LoginActivity.class);
    }
}
