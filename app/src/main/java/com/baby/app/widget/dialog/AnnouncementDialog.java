package com.baby.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import androidx.annotation.NonNull;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.baby.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 公告弹窗
 */

public class AnnouncementDialog extends Dialog {

    public AnnouncementDialog(@NonNull Context context) {
        super(context);
    }

    public AnnouncementDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(20, 0, 20, 0);
        getWindow().setAttributes(layoutParams);

    }

    public static class Builder {

        private String title;
        private String message;
        private String noticeBrief;
        private boolean mClickHandled = false;

        private View layout;
        private AnnouncementDialog dialog;


        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new AnnouncementDialog(context, com.android.baselibrary.R.style.MyDialog);
            dialog.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.announcement_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNoticeBrief(String share_url) {
            this.noticeBrief = share_url;
            return this;
        }


        //设置超链接文字
        private SpannableString getClickableSpan() {
            SpannableString spanStr = new SpannableString(message);
            try {
                JSONObject jsonObject = new JSONObject(noticeBrief);
                String url;
                if (jsonObject != null&&jsonObject.length()>2&&!jsonObject.equals("")) {
                    Iterator<String> keys = jsonObject.keys();
                    while(keys.hasNext()){
                        String key = keys.next();
                        url = jsonObject.getString(key);
                        int index = message.indexOf(key);
                        spanStr.setSpan(new TextClick(dialog.getContext(), url), index, index + key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return spanStr;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        public AnnouncementDialog create() {

            if (message != null) {
                //设置提示内容
                TextView content = layout.findViewById(R.id.ann_message_view);
                content.setMovementMethod(ScrollingMovementMethod.getInstance());
                content.setMovementMethod(LinkMovementMethod.getInstance());
                content.setText(getClickableSpan());

            }
            if (title != null) {
                ((TextView) layout.findViewById(R.id.ann_title_view)).setText(title);
            }
            layout.findViewById(R.id.ann_ok_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setContentView(layout);
            //用户可以点击手机Back键取消对话框显示
            dialog.setCancelable(false);
            //用户不能通过点击对话框之外的地方取消对话框显示
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }


    public static class TextClick extends ClickableSpan {
        Context context;
        String url;

        public TextClick(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            context.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(url)));
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }
    }
}
