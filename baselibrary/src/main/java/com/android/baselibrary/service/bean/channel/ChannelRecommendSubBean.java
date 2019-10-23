package com.android.baselibrary.service.bean.channel;

import com.android.baselibrary.service.bean.BaseBean;

public class ChannelRecommendSubBean extends BaseBean {
    /**
     * name : 剧情
     * picUrl : http://103.196.21.135:8888/group1/M00/00/00/Z8QVh1zY6BqAZ3hnAABTQ_8vdwU026.jpg
     * id : 138
     * picType : 1
     * introduce : 这是一个介绍，测试数据。去便利店买了24块钱的东西——招商银行app一条信息，您消费24元；短信一条信息，您消费24元；微信支付一条信息，您消费24元。给我整一愣：我买啥了就花了70多?
     */

    private String name;
    private String picUrl;
    private int id;
    private int picType;
    private String introduce;
    private String updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPicType() {
        return picType;
    }

    public void setPicType(int picType) {
        this.picType = picType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
