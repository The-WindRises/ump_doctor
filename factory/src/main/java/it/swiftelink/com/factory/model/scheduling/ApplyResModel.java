package it.swiftelink.com.factory.model.scheduling;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class ApplyResModel extends BaseResModel {

    private List<Apply> data;

    public ApplyResModel() {
    }

    public ApplyResModel(List<Apply> data) {
        this.data = data;
    }

    public List<Apply> getData() {
        return data;
    }

    public void setData(List<Apply> data) {
        this.data = data;
    }

    public static class Apply {
        private String id;
        private String name;
        private String date;
        private String startTime;
        private String endTime;
        private int planNumber;
        private String createdBy;
        private long creationDate;
        private String lastUpdatedBy;
        private long lastUpdatedDate;

        public Apply() {
        }

        public Apply(String id, String name, String date, String startTime, String endTime, int planNumber, String createdBy, long creationDate, String lastUpdatedBy, long lastUpdatedDate) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            this.planNumber = planNumber;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getPlanNumber() {
            return planNumber;
        }

        public void setPlanNumber(int planNumber) {
            this.planNumber = planNumber;
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
