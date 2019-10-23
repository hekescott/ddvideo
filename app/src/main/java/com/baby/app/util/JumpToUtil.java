package com.baby.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.video.VideoInComeBean;
import com.baby.app.modules.channel.page.TagListActivity;
import com.baby.app.modules.mine.page.ErActivity;
import com.baby.app.modules.mine.page.VipActivity;
import com.baby.app.modules.video.page.VideoActivity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-06-17/21:55
 * @Description: 跳转工具
 */
public class JumpToUtil {
    private static JumpToUtil mInstance;
    private Context mContext;


    public JumpToUtil(Context context) {
        this.mContext = context;
    }

    public static JumpToUtil getInstance(Context context) {
        if (mInstance == null)
            mInstance = new JumpToUtil(context);
        return mInstance;
    }

    public void jumpToTagActivity(HomeBannerBean homeBannerBean) {

        if (homeBannerBean.getLinkType() == 1) {
            //TODO:这里需要判断类型
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(homeBannerBean.getLinkUrl());
            intent.setData(content_url);
            mContext.startActivity(intent);
        } else {
            switch (homeBannerBean.getLinkType()) {
                case 2: //内部影片
                    int id = 0;
                    try {
                        id = Integer.parseInt(homeBannerBean.getLinkUrl());
                    } catch (Exception ignored) {

                    }
                    jumpToVideo(id, "", null);
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


    public void jumpToVideo(int id, String name, String url) {
        VideoInComeBean videoInComeBean = new VideoInComeBean();
        videoInComeBean.setId(id);
        videoInComeBean.setVideoName(name);
        videoInComeBean.setVideoUrl(url);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VideoActivity.VIDEO_KEY, (Serializable) videoInComeBean);
        openActivity(VideoActivity.class, bundle);
    }

    /**
     * 通过类名启动Activity add
     */
    public void openActivity(Class pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     */
    public void openActivity(Class pClass, Bundle pBundle) {
        Intent intent = new Intent(mContext, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        mContext.startActivity(intent);
    }
}
