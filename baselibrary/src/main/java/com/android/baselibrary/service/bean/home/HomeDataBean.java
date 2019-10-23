package com.android.baselibrary.service.bean.home;

import com.android.baselibrary.service.bean.BaseBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/22.
 */

public class HomeDataBean extends BaseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<HomeBannerBean> bannerList;
        private List<HomeClassBean> classifyList;
        private List<HomeListBean> newVideoList;
        private List<HomeListBean> mostVideoList;
        private List<HomeStarBean> starList;
        private List<HomeClassCollectBean> classifyListCollect;
        private List<BottomAdvBean> bottomAdv;
        private AnhaoMapBean anhaoMap;


        public AnhaoMapBean getAnhaoMap() {
            return anhaoMap;
        }

        public void setAnhaoMap(AnhaoMapBean anhaoMap) {
            this.anhaoMap = anhaoMap;
        }

        public List<BottomAdvBean> getBottomAdv() {
            return bottomAdv;
        }

        public void setBottomAdv(List<BottomAdvBean> bottomAdv) {
            this.bottomAdv = bottomAdv;
        }

        public List<HomeClassCollectBean> getClassifyListCollect() {
            return classifyListCollect;
        }

        public void setClassifyListCollect(List<HomeClassCollectBean> classifyListCollect) {
            this.classifyListCollect = classifyListCollect;
        }

        public List<HomeStarBean> getStarList() {
            return starList;
        }

        public void setStarList(List<HomeStarBean> starList) {
            this.starList = starList;
        }

        public List<HomeListBean> getMostVideoList() {
            return mostVideoList;
        }

        public void setMostVideoList(List<HomeListBean> mostVideoList) {
            this.mostVideoList = mostVideoList;
        }

        public List<HomeListBean> getNewVideoList() {
            return newVideoList;
        }

        public void setNewVideoList(List<HomeListBean> newVideoList) {
            this.newVideoList = newVideoList;
        }

        public List<HomeClassBean> getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(List<HomeClassBean> classifyList) {
            this.classifyList = classifyList;
        }

        public List<HomeBannerBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<HomeBannerBean> bannerList) {
            this.bannerList = bannerList;
        }
    }


    public class AnhaoMapBean {
        /**
         * value : http://www.baidu.com
         * key : 888
         */

        private String value;
        private String key;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
