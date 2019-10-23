package com.android.baselibrary.service.http;

import android.util.Log;

import com.android.baselibrary.base.BaseApplication;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.UrlConstants;
import com.android.baselibrary.service.bean.IpBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by yongqianggeng on 2018/6/7.
 */

public class RetrofitManager {

    public static RetrofitManager instance;

    private Retrofit mRetrofit;

    private String versionName;
    private final OkHttpClient okHttpClient;

    public String getBaseUrl() {
        return baseUrl;
    }

    private String baseUrl;

    public static RetrofitManager getInstance() {
        synchronized (RetrofitManager.class) {
            if (instance == null) {
                instance = new RetrofitManager();
            }
        }
        return instance;
    }

    public String getVersionName() {
        if (versionName == null) {
            versionName = BaseApplication.getInstance().getAppVersionName();
        }
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    private RetrofitManager() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (message != null) {
//                    if (message.length() > 200) {
//                        return;
//                    }
                    if (message.contains("-->")) {
                        Log.e("mv", "Request:" + message);
                    } else if (message.contains("<--")) {
                        Log.e("mv", "Request:" + message);
                    } else {
                        Log.e("mv", "Response:" + message);
                    }
                }
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient =
                new OkHttpClient.Builder().connectTimeout(UrlConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(UrlConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS).writeTimeout(UrlConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                if (UserStorage.getInstance().getToken() != null) {
                    Request request = original.newBuilder().header("version",
                            BaseApplication.getInstance().getAppVersionName()).addHeader("token",
                            UserStorage.getInstance().getToken()).method(original.method(), original.body()).build();

                    Log.e("token", UserStorage.getInstance().getToken());
                    return chain.proceed(request);
                } else {
                    Request request = original.newBuilder().header("version",
                            BaseApplication.getInstance().getAppVersionName()).addHeader("token", "").method(original.method(), original.body()).build();

                    return chain.proceed(request);
                }

            }
        }).build();
        if (null == baseUrl) {
            refreshIp();
        } else {
            mRetrofit =
                    new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(JsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }
    }

    public void refreshIp() {
        baseUrl = UrlConstants.NEW_BASE_URL;
//        String ipsString = (String) SPUtils.get(Constants.SP_IPLIST_OBJECT, "");
//        List<IpBean> ipBeanList = null;
//        try {
//            Gson gson = new Gson();
//            ipBeanList = gson.fromJson(ipsString, new TypeToken<List<IpBean>>() {
//            }.getType());
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//
//        if (null != ipBeanList && ipBeanList.size() > 0) {
//            IpBean ipBean = ipBeanList.get(0);
//            baseUrl =
//                    ipBean.getSchema() + "://" + ipBean.getHost() + ":" + ipBean.getPort() + "/";
//            baseUrl = ipBean.getContent() == null ? baseUrl : baseUrl + ipBean.getContent().trim() + "/";
//        }
        mRetrofit =
                new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(JsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

//    private OkHttpClient getOkHttpClient() {
//
//        LogInterceptor loggingInterceptor = new LogInterceptor(getVersionName());
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(UrlConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                    .readTimeout(UrlConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                    .writeTimeout(UrlConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(loggingInterceptor).build();
//
//            return okHttpClient;
//
//    }

//    private OkHttpClient getOkHttpClient() {
//        //日志显示级别
//        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
//        //新建log拦截器
//        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.e("Ishangzu","OkHttp====Message:"+message);
//            }
//        });
//        loggingInterceptor.setLevel(level);
//        //定制OkHttp
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
//                .Builder();
//        //OkHttp进行添加拦截器loggingInterceptor
//        httpClientBuilder.addInterceptor(loggingInterceptor);
//        return httpClientBuilder.build();
//    }


}


