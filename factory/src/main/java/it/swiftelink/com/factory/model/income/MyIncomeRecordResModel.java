package it.swiftelink.com.factory.model.income;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class MyIncomeRecordResModel extends BaseResModel {

    private List<MyIncomeRecord> data;

    public List<MyIncomeRecord> getData() {
        return data;
    }

    public void setData(List<MyIncomeRecord> data) {
        this.data = data;
    }

    public MyIncomeRecordResModel(List<MyIncomeRecord> data) {
        this.data = data;
    }

    public static class MyIncomeRecord {
        private String id;
        private String doctorId;
        private String type;
        private String no;
        private String name;
        private int amount;
        private String method;
        private String bank;
        private String receiptAccount;
        private long applicationTime;
        private long reviewTime;
        private String status;
        private String rejectReason;
        private String doctorAmount;
        private String createdBy;
        private long creationDate;
        private String lastUpdatedBy;
        private String lastUpdatedDate;

        public MyIncomeRecord(String id, String doctorId, String type, String no, String name, int amount, String method, String bank, String receiptAccount, long applicationTime, long reviewTime, String status, String rejectReason, String doctorAmount, String createdBy, long creationDate, String lastUpdatedBy, String lastUpdatedDate) {
            this.id = id;
            this.doctorId = doctorId;
            this.type = type;
            this.no = no;
            this.name = name;
            this.amount = amount;
            this.method = method;
            this.bank = bank;
            this.receiptAccount = receiptAccount;
            this.applicationTime = applicationTime;
            this.reviewTime = reviewTime;
            this.status = status;
            this.rejectReason = rejectReason;
            this.doctorAmount = doctorAmount;
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

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getReceiptAccount() {
            return receiptAccount;
        }

        public void setReceiptAccount(String receiptAccount) {
            this.receiptAccount = receiptAccount;
        }

        public long getApplicationTime() {
            return applicationTime;
        }

        public void setApplicationTime(long applicationTime) {
            this.applicationTime = applicationTime;
        }

        public long getReviewTime() {
            return reviewTime;
        }

        public void setReviewTime(long reviewTime) {
            this.reviewTime = reviewTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getDoctorAmount() {
            return doctorAmount;
        }

        public void setDoctorAmount(String doctorAmount) {
            this.doctorAmount = doctorAmount;
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

        public String getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        public void setLastUpdatedDate(String lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
        }
    }
}
