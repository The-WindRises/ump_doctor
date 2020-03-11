package it.swiftelink.com.vcs_doctor.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class Banner extends SimpleBannerInfo {

    private String imageUrl;
    private String linkAddr;
    private String title;

    public Banner() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Banner(String imageUrl, String linkAddr) {
        this.imageUrl = imageUrl;
        this.linkAddr = linkAddr;
    }

    public Banner(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkAddr() {
        return linkAddr;
    }

    public void setLinkAddr(String linkAddr) {
        this.linkAddr = linkAddr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public Object getXBannerUrl() {
        return null;
    }
}
