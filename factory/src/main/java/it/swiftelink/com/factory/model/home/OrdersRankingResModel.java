package it.swiftelink.com.factory.model.home;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class OrdersRankingResModel extends BaseResModel {

    private List<OrdersRanking> data;

    public List<OrdersRanking> getData() {
        return data;
    }

    public void setData(List<OrdersRanking> data) {
        this.data = data;
    }

    public OrdersRankingResModel() {
    }

    public OrdersRankingResModel(List<OrdersRanking> data) {
        this.data = data;
    }

    public static class OrdersRanking {
        private String id;
        private String appId;
        private String vedioClinicImg;
        private String reservationClinicImg;
        private String trainingImg;
        private String orderLeaderboardImg;
        private String introductionImg;
        private String hireCertificateImg;
        private String language;
        private String createdBy;
        private long creationDate;
        private String lastUpdatedBy;
        private long lastUpdatedDate;

        public OrdersRanking() {
        }

        public OrdersRanking(String id, String appId, String vedioClinicImg, String reservationClinicImg, String trainingImg, String orderLeaderboardImg, String introductionImg, String hireCertificateImg, String language, String createdBy, long creationDate, String lastUpdatedBy, long lastUpdatedDate) {
            this.id = id;
            this.appId = appId;
            this.vedioClinicImg = vedioClinicImg;
            this.reservationClinicImg = reservationClinicImg;
            this.trainingImg = trainingImg;
            this.orderLeaderboardImg = orderLeaderboardImg;
            this.introductionImg = introductionImg;
            this.hireCertificateImg = hireCertificateImg;
            this.language = language;
            this.createdBy = createdBy;
            this.creationDate = creationDate;
            this.lastUpdatedBy = lastUpdatedBy;
            this.lastUpdatedDate = lastUpdatedDate;
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

        public String getVedioClinicImg() {
            return vedioClinicImg;
        }

        public void setVedioClinicImg(String vedioClinicImg) {
            this.vedioClinicImg = vedioClinicImg;
        }

        public String getReservationClinicImg() {
            return reservationClinicImg;
        }

        public void setReservationClinicImg(String reservationClinicImg) {
            this.reservationClinicImg = reservationClinicImg;
        }

        public String getTrainingImg() {
            return trainingImg;
        }

        public void setTrainingImg(String trainingImg) {
            this.trainingImg = trainingImg;
        }

        public String getOrderLeaderboardImg() {
            return orderLeaderboardImg;
        }

        public void setOrderLeaderboardImg(String orderLeaderboardImg) {
            this.orderLeaderboardImg = orderLeaderboardImg;
        }

        public String getIntroductionImg() {
            return introductionImg;
        }

        public void setIntroductionImg(String introductionImg) {
            this.introductionImg = introductionImg;
        }

        public String getHireCertificateImg() {
            return hireCertificateImg;
        }

        public void setHireCertificateImg(String hireCertificateImg) {
            this.hireCertificateImg = hireCertificateImg;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public long getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(long creationDate) {
            this.creationDate = creationDate;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public long getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        public void setLastUpdatedDate(long lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
        }
    }

}
