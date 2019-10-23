package com.baby.app.modules.mine.adapter;

import androidx.annotation.Nullable;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.baby.app.R;
import com.baby.app.widget.dialog.AnnouncementDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/5.
 * 通知
 */

public class NotificationAdapter extends BaseQuickAdapter<NotificationBean.Data, BaseViewHolder> {


    public NotificationAdapter(int layoutResId, @Nullable List<NotificationBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationBean.Data item) {
        TextView notice_title_view = helper.getView(R.id.notice_title_view);
        TextView notice_time_view = helper.getView(R.id.notice_time_view);
        TextView notice_content_view = helper.getView(R.id.notice_content_view);
        notice_title_view.setText(item.getNoticeTitle());
        notice_content_view.setText(getNoticeBrief(item.getNoticeBrief()));
        notice_time_view.setText(item.getPushTime());
    }


    //设置超链接文字
    public static String getNoticeBrief(String noticeBrief) {
        StringBuffer stringBuffer = new StringBuffer();
        String spanStr = "";
        String url;
        try {
            JSONObject jsonObject = new JSONObject(noticeBrief);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                url = jsonObject.getString(key);
                stringBuffer.append(key + url + "\n");
            }
            spanStr = stringBuffer.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return spanStr;
    }

}
