package it.swiftelink.com.factory.model.main;

import it.swiftelink.com.common.factory.BaseResModel;

public class VersionResModel extends BaseResModel {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String appDownloadUrl;
        private int appVersionNumber;
        private String appVersionName;
        private int isForce;
        private String updateLog;

        public String getAppDownloadUrl() {
            return appDownloadUrl;
        }

        public void setAppDownloadUrl(String appDownloadUrl) {
            this.appDownloadUrl = appDownloadUrl;
        }

        public int getAppVersionNumber() {
            return appVersionNumber;
        }

        public void setAppVersionNumber(int appVersionNumber) {
            this.appVersionNumber = appVersionNumber;
        }

        public String getAppVersionName() {
            return appVersionName;
        }

        public void setAppVersionName(String appVersionName) {
            this.appVersionName = appVersionName;
        }

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }

        public String getUpdateLog() {
            return updateLog;
        }

        public void setUpdateLog(String updateLog) {
            this.updateLog = updateLog;
        }
    }

}
