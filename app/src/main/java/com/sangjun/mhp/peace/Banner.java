package com.sangjun.mhp.peace;

/**
 * Created by Sangjun on 2017-10-16.
 */

public class Banner {
    public String imgSrc;
    public String title;
    public String content;
    public String url;

    public Banner() {
        this.imgSrc = null;
        this.title = null;
        this.content = null;
        this.url = null;
    }

    public Banner(String imgSrc, String title, String content, String url) {
        this.imgSrc = imgSrc;
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
