package com.dm.news.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */
public class NewsGson {

    private int code;
    private String msg;
    /**
     * ctime : 2016-10-11 10:53
     * title : 南京六合区：继续组织搜捕外逃眼镜蛇，幼蛇毒性不大过
     * description : 网易社会
     * picUrl :
     * url : http://news.163.com/16/1011/10/C33ET2G000014SEH.html#f=slist
     */

    private List<NewslistBean> newslist;

    public static NewsGson objectFromData(String str) {

        return new com.google.gson.Gson().fromJson(str, NewsGson.class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public static NewslistBean objectFromData(String str) {

            return new com.google.gson.Gson().fromJson(str, NewslistBean.class);
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "NewslistBean{" +
                    "ctime='" + ctime + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
