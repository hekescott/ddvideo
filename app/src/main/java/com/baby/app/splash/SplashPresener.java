package com.baby.app.splash;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.DefaultBean;
import com.android.baselibrary.service.bean.IpBean;
import com.android.baselibrary.service.bean.IpListBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.service.bean.user.UserBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.SPUtils;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/8.
 * 启动页面
 */

public class SplashPresener extends BasePresenter {


    private SplashView mSplashView;

    public SplashPresener(SplashView splashView) {
        this.mSplashView = splashView;
    }

    @Override
    protected BaseView getView() {
        return this.mSplashView;
    }

    public void fetchDeviceInfo() {
        if (UserStorage.getInstance().isLogin() && UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
            requestDateNew(NetService.getInstance().deviceInfo2(UserStorage.getInstance().getToken()),
                    Constants.DIALOG_LOADING, new BaseCallBack() {
                        @Override
                        public void onSuccess(Object obj) {
                            LoginBean bean = (LoginBean) obj;
                            UserStorage.getInstance().touristLogin(bean);
                            mSplashView.fetchDeviceInfo(bean);
                        }

                        @Override
                        public void onFaild(Object obj) {
                            mSplashView.faied();
                        }

                        @Override
                        public void onNetWorkError(String errorMsg) {
                            mSplashView.faied();
                        }
                    });


        } else {
            requestDateNew(NetService.getInstance().deviceInfo(), Constants.DIALOG_LOADING, new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    LoginBean bean = (LoginBean) obj;
                    UserStorage.getInstance().touristLogin(bean);
                    mSplashView.fetchDeviceInfo(bean);
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

    public void fetchOutInfo() {
        requestDateNew(NetService.getInstance().deviceInfo(), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {

                //用户相关的信息分为个：LoginBean（存储token）   和  UserBean（储存缓存次数）
                //退出的逻辑是：
                // 1. 重新用游客的身份登录，token覆盖后身份就发生了变化
                LoginBean bean = (LoginBean) obj;
                UserStorage.getInstance().touristLogin(bean);
                //  2.调用NetService.getInstance().getMemberInfo()这个网络请求，获得用户个人信息数据。缓存次数等。  参考MinePresenter.java 中的方法。
                //  将userBean的信息覆盖即可  UserStorage.getInstance().saveUserInfo(bean);
                fetchUserInfo();
                mSplashView.fetchDeviceInfo(bean);


            }

            @Override
            public void onFaild(Object obj) {
                mSplashView.faied();
            }

            @Override
            public void onNetWorkError(String errorMsg) {
                mSplashView.faied();
            }
        });

    }


    public void getBackupIps() {
        ToastUtil.showToast("正在优化网络...");
        requestDateNew(NetService.getInstance().getBackupIps(), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                if (obj instanceof DefaultBean) {
                    DefaultBean<IpListBean> dataBean = (DefaultBean<IpListBean>) obj;
                    if (dataBean.getData() != null) {
                        List<IpBean> ips = new ArrayList<>();

                        List<IpBean> ips1 = dataBean.getData().getIps();
                        String ipsString = (String) SPUtils.get(Constants.SP_IPLIST_OBJECT, "");
                        List<IpBean> ipBeanList = null;
                        try {
                            Gson gson = new Gson();
                            ipBeanList = gson.fromJson(ipsString, new TypeToken<List<IpBean>>() {
                            }.getType());
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }

                        if (null != ipBeanList && ipBeanList.size() > 0) {
                            IpBean ipBean = ipBeanList.get(0);
                            //对比去掉重复
                            for (IpBean bean : ips1) {
                                if (bean.getHost().equalsIgnoreCase(ipBean.getHost()) && bean.getSchema().equalsIgnoreCase(ipBean.getSchema()) && bean.getPort().equalsIgnoreCase(ipBean.getPort()) && bean.getContent().equalsIgnoreCase(ipBean.getContent()))
                                    ips1.remove(bean);
                            }
                            ips.add(ipBean);
                        }
                        ips.addAll(ips1);
                        SPUtils.putList(Constants.SP_IPLIST_OBJECT, ips);
                        return;
                    }
                }
                onFaild(obj);
            }

            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }


    public void fetchUserInfo() {
        requestDateNew(NetService.getInstance().getMemberInfo(), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                UserBean bean = (UserBean) obj;
                UserStorage.getInstance().saveUserInfo(bean);

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
