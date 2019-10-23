package com.android.baselibrary.service.bean.channel;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.Objects;

/**
 * Created by yongqianggeng on 2018/9/28.
 */

public class ChannelTagBean extends BaseBean {

    private String name;
    private String picUrl;
    private int id;

    public int getId() {
        return id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelTagBean that = (ChannelTagBean) o;

        if (id != that.id) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(picUrl, that.picUrl);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
