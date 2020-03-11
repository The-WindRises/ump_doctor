package it.swiftelink.com.factory.model.home;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class BannerResModel extends BaseResModel {

    private List<BannerModel> data;


    public BannerResModel() {
    }

    public BannerResModel(List<BannerModel> data) {
        this.data = data;
    }

    public List<BannerModel> getData() {
        return data;
    }

    public void setData(List<BannerModel> data) {
        this.data = data;
    }

    public static class BannerModel {

        private String image;

        private String linkAddr;

        private String no;

        private String title;

        private String id;

        private String appId;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLinkAddr() {
            return linkAddr;
        }

        public void setLinkAddr(String linkAddr) {
            this.linkAddr = linkAddr;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }
    }

}
