package com.android.baselibrary.service.bean.mine;

import com.android.baselibrary.service.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/5.
 */

public class NotificationDetailBean extends BaseBean implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    /**
     * id : 1
     * createBy : null
     * createTime : 1558426360000
     * updateBy : null
     * updateTime : 1570964913000
     * noticeTitle : 公告
     * noticeBrief : {
     * "点我查看教程":"http://www.du.com",
     * "点我下载":"http://www.baidu.com"
     * }
     * noticeContent : 即日起分享邀请好友获得永久ssssvip。
     * <p>
     * 如何分享： 点我查看教程
     * <p>
     * 专注视频app十二年，拥有雄厚实力的技术团队
     * 拥有雄厚实力的技术团队。快速高效的为您定制化服务。值得您的信赖升级地址：点我下载
     * longTime : 528
     */

    public class Data {
        private int id;
        private long createTime;
        private long updateTime;
        private String noticeTitle;
        private String noticeBrief;
        private String noticeContent;
        private int longTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getNoticeBrief() {
            return noticeBrief;
        }

        public void setNoticeBrief(String noticeBrief) {
            this.noticeBrief = noticeBrief;
        }

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public int getLongTime() {
            return longTime;
        }

        public void setLongTime(int longTime) {
            this.longTime = longTime;
        }
    }

}
