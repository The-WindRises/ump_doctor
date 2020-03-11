package it.swiftelink.com.vcs_doctor.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class GuidePage extends SimpleBannerInfo {

    private int resId;

    public GuidePage(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public Object getXBannerUrl() {
        return resId;
    }
}
