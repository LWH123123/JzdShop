package com.jzd.jzshop.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.io.Serializable;

public class BannEntity extends SimpleBannerInfo implements Serializable {
    private String imgurl;
    private String url;

    public BannEntity(String imgurl, String url) {
        this.imgurl = imgurl;
        this.url = url;
    }

    public BannEntity(String imgurl) {
        this.imgurl=imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Object getXBannerUrl() {
        return imgurl;
    }
}
