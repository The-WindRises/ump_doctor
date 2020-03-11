package it.swiftelink.com.factory.model.scheduling;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/25 21:02
 * @一句话描述 排版纪纪录
 */


public class SchedulingdRecordsResModel extends BaseResModel {

    public SchedulingdRecordsResModel() {
    }

    private DataModel data;

    public SchedulingdRecordsResModel(DataModel data) {
        this.data = data;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public static class DataModel {
        private List<SchedulingdRecord> dataList;
        private int totalPages;
        private int dataCount;

        public DataModel() {
        }

        public DataModel(List<SchedulingdRecord> dataList, int totalPages, int dataCount) {
            this.dataList = dataList;
            this.totalPages = totalPages;
            this.dataCount = dataCount;
        }

        public List<SchedulingdRecord> getDataList() {
            return dataList;
        }

        public void setDataList(List<SchedulingdRecord> dataList) {
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
    }


    public static class SchedulingdRecord {
        private String originalShiftId;
        private String originalShiftDate;
        private String originalStartTime;
        private String originalEndTime;
        private String rejectReason;
        private String applyShiftDate;
        private String type;
        private String applyStartTime;
        private String applyEndTime;

        private String creationDate;

        private String approvalStatus;

        public SchedulingdRecord() {
        }

        public SchedulingdRecord(String originalShiftId, String originalShiftDate, String originalStartTime, String originalEndTime, String applyShiftDate, String applyStartTime, String applyEndTime, String creationDate, String approvalStatus) {
            this.originalShiftId = originalShiftId;
            this.originalShiftDate = originalShiftDate;
            this.originalStartTime = originalStartTime;
            this.originalEndTime = originalEndTime;
            this.applyShiftDate = applyShiftDate;
            this.applyStartTime = applyStartTime;
            this.applyEndTime = applyEndTime;
            this.creationDate = creationDate;
            this.approvalStatus = approvalStatus;
        }

        public SchedulingdRecord(String originalShiftId, String originalShiftDate, String originalStartTime, String originalEndTime, String rejectReason, String applyShiftDate, String type, String applyStartTime, String applyEndTime, String creationDate, String approvalStatus) {
            this.originalShiftId = originalShiftId;
            this.originalShiftDate = originalShiftDate;
            this.originalStartTime = originalStartTime;
            this.originalEndTime = originalEndTime;
            this.rejectReason = rejectReason;
            this.applyShiftDate = applyShiftDate;
            this.type = type;
            this.applyStartTime = applyStartTime;
            this.applyEndTime = applyEndTime;
            this.creationDate = creationDate;
            this.approvalStatus = approvalStatus;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOriginalShiftId() {
            return originalShiftId;
        }

        public void setOriginalShiftId(String originalShiftId) {
            this.originalShiftId = originalShiftId;
        }

        public String getOriginalShiftDate() {
            return originalShiftDate;
        }

        public void setOriginalShiftDate(String originalShiftDate) {
            this.originalShiftDate = originalShiftDate;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getOriginalStartTime() {
            return originalStartTime;
        }

        public void setOriginalStartTime(String originalStartTime) {
            this.originalStartTime = originalStartTime;
        }

        public String getOriginalEndTime() {
            return originalEndTime;
        }

        public void setOriginalEndTime(String originalEndTime) {
            this.originalEndTime = originalEndTime;
        }

        public String getApplyShiftDate() {
            return applyShiftDate;
        }

        public void setApplyShiftDate(String applyShiftDate) {
            this.applyShiftDate = applyShiftDate;
        }

        public String getApplyStartTime() {
            return applyStartTime;
        }

        public void setApplyStartTime(String applyStartTime) {
            this.applyStartTime = applyStartTime;
        }

        public String getApplyEndTime() {
            return applyEndTime;
        }

        public void setApplyEndTime(String applyEndTime) {
            this.applyEndTime = applyEndTime;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }
    }

}
