package com.baby.app.service;

import android.os.Handler;
import android.os.Message;

import com.hdl.m3u8.M3U8InfoManger;
import com.hdl.m3u8.bean.M3U8;
import com.hdl.m3u8.bean.M3U8Ts;
import com.hdl.m3u8.bean.OnM3U8InfoListener;
import com.hdl.m3u8.utils.MUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyM3U8InfoManger {
    private static MyM3U8InfoManger mM3U8InfoManger;
    private OnM3U8InfoListener onM3U8InfoListener;
    private static final int WHAT_ON_ERROR = 1101;
    private static final int WHAT_ON_SUCCESS = 1102;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_ON_ERROR:
                    onM3U8InfoListener.onError((Throwable) msg.obj);
                    break;
                case WHAT_ON_SUCCESS:
                    onM3U8InfoListener.onSuccess((M3U8) msg.obj);
                    break;
            }
        }
    };

    public MyM3U8InfoManger() {
    }

    public static MyM3U8InfoManger getInstance() {
        synchronized (MyM3U8InfoManger.class) {
            if (mM3U8InfoManger == null) {
                mM3U8InfoManger = new MyM3U8InfoManger();
            }
        }
        return mM3U8InfoManger;
    }


    /**
     * 将Url转换为M3U8对象
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static M3U8 parseIndex(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

        if (conn.getResponseCode() == 200) {
            String realUrl = conn.getURL().toString();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String basepath = realUrl.substring(0, realUrl.lastIndexOf("/") + 1);
            M3U8 ret = new M3U8();
            ret.setBasepath(basepath);

            String line;
            float seconds = 0;
            String resultUrl = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    if (line.startsWith("#EXTINF:")) {
                        line = line.substring(8);
                        if (line.endsWith(",")) {
                            line = line.substring(0, line.length() - 1);
                        }
                        seconds = Float.parseFloat(line);
                    }
                    continue;
                }
                //再次尝试
                String baseHost = basepath.substring(0, realUrl.indexOf("/", 10)+1);
                if (line.endsWith("m3u8")) {

                    M3U8 result = parseIndex(basepath + line);
                    if (result == null){

                        result = parseIndex(baseHost + line);

                        return result;
                    }else{
                        return result;
                    }
                }
                if (!line.startsWith("http")){
                    int startIndex = basepath.indexOf("/", 10)+1;
                    String subPahth = basepath.substring(startIndex, startIndex + 8);
                    if (line.contains(subPahth)) {
                        line = baseHost + line;
                    }else{
                        line = basepath + line;
                    }
                }
                    ret.addTs(new M3U8Ts(line, seconds));
                    seconds = 0;
                }
                reader.close();

                return ret;
        } else {
            return null;
        }
    }



    /**
     * 获取m3u8信息
     *
     * @param url
     * @param onM3U8InfoListener
     */
    public synchronized void getM3U8Info(final String url, OnM3U8InfoListener onM3U8InfoListener) {
        this.onM3U8InfoListener = onM3U8InfoListener;
        onM3U8InfoListener.onStart();
        new Thread() {
            @Override
            public void run() {
                try {
//                    Log.e("hdltag", "run(M3U8InfoManger.java:62):" + url);
                    M3U8 m3u8 =  parseIndex(url);
                    handlerSuccess(m3u8);
                } catch (IOException e) {
//                    e.printStackTrace();
                    handlerError(e);
                }
            }
        }.start();

    }

    /**
     * 通知异常
     *
     * @param e
     */
    private void handlerError(Throwable e) {
        Message msg = mHandler.obtainMessage();
        msg.obj = e;
        msg.what = WHAT_ON_ERROR;
        mHandler.sendMessage(msg);
    }

    /**
     * 通知成功
     *
     * @param m3u8
     */
    private void handlerSuccess(M3U8 m3u8) {
        Message msg = mHandler.obtainMessage();
        msg.obj = m3u8;
        msg.what = WHAT_ON_SUCCESS;
        mHandler.sendMessage(msg);
    }
}
