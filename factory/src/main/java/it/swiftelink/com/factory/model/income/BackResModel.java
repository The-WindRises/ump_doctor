package it.swiftelink.com.factory.model.income;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class BackResModel extends BaseResModel {

    private DataModel data;

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public static class DataModel {
        private List<Back> dataList;
        private int totalPages;
        private int dataCount;


        public DataModel(List<Back> dataList, int totalPages, int dataCount) {
            this.dataList = dataList;
            this.totalPages = totalPages;
            this.dataCount = dataCount;
        }

        public List<Back> getDataList() {
            return dataList;
        }

        public void setDataList(List<Back> dataList) {
            this.dataList = dataList;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getDataCount() {
            return dataCount;
        }

        public void setDataCount(int dataCount) {
            this.dataCount = dataCount;
        }

        public DataModel() {
        }
    }


    public static class Back {
        private String id;
        private String userId;
        private String name;
        private String cardNo;
        private String bank;
        private String mobile;
        private String createdBy;
        private String creationDate;
        private String lastUpdatedBy;
        private String lastUpdatedDate;

        public Back() {
        }

        public Back(String id, String userId, String name, String cardNo, String bank, String mobile, String createdBy, String creationDate, String lastUpdatedBy, String lastUpdatedDate) {
            this.id = id;
            this.userId = userId;
            this.name = name;
            this.cardNo = cardNo;
            this.bank = bank;
            this.mobile = mobile;
            this.createdBy = createdBy;
            this.creationDate = creationDate;
            this.lastUpdatedBy = lastUpdatedBy;
            this.lastUpdatedDate = lastUpdatedDate;
        }

        public String getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        public void setLastUpdatedDate(String lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }
    }


}
