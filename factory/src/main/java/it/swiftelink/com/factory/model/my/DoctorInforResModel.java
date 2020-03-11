package it.swiftelink.com.factory.model.my;

import it.swiftelink.com.common.factory.BaseResModel;

public class DoctorInforResModel extends BaseResModel {

    private DoctorInfor data;

    public DoctorInforResModel(DoctorInfor data) {
        this.data = data;
    }

    public DoctorInfor getData() {
        return data;
    }

    public void setData(DoctorInfor data) {
        this.data = data;
    }

    public static class DoctorInfor {


        private String id;
        private String userId;
        private String name;
        private String headImg;
        private String hospital;
        private String gender;
        private String sectionOfficeId;
        private String sectionOfficeName;
        private String sectionOfficeTel;
        private String jobTitle;
        private int level;
        private String doctorStatus;
        private String onlineTime;
        private String todayOnlineTime;
        private String scheduleTime;
        private String lateEarlyTime;
        private float amount;

        private String language;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getDoctorStatus() {
            return doctorStatus;
        }

        public void setDoctorStatus(String doctorStatus) {
            this.doctorStatus = doctorStatus;
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

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getSectionOfficeId() {
            return sectionOfficeId;
        }

        public void setSectionOfficeId(String sectionOfficeId) {
            this.sectionOfficeId = sectionOfficeId;
        }

        public String getSectionOfficeName() {
            return sectionOfficeName;
        }

        public void setSectionOfficeName(String sectionOfficeName) {
            this.sectionOfficeName = sectionOfficeName;
        }

        public String getSectionOfficeTel() {
            return sectionOfficeTel;
        }

        public void setSectionOfficeTel(String sectionOfficeTel) {
            this.sectionOfficeTel = sectionOfficeTel;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getOnlineTime() {
            return onlineTime;
        }

        public void setOnlineTime(String onlineTime) {
            this.onlineTime = onlineTime;
        }

        public String getTodayOnlineTime() {
            return todayOnlineTime;
        }

        public void setTodayOnlineTime(String todayOnlineTime) {
            this.todayOnlineTime = todayOnlineTime;
        }

        public String getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(String scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

        public String getLateEarlyTime() {
            return lateEarlyTime;
        }

        public void setLateEarlyTime(String lateEarlyTime) {
            this.lateEarlyTime = lateEarlyTime;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public DoctorInfor(String id, String userId, String name, String headImg, String hospital, String gender, String sectionOfficeId, String sectionOfficeName, String sectionOfficeTel, String jobTitle, int level, String onlineTime, String todayOnlineTime, String scheduleTime, String lateEarlyTime, float amount) {
            this.id = id;
            this.userId = userId;
            this.name = name;
            this.headImg = headImg;
            this.hospital = hospital;
            this.gender = gender;
            this.sectionOfficeId = sectionOfficeId;
            this.sectionOfficeName = sectionOfficeName;
            this.sectionOfficeTel = sectionOfficeTel;
            this.jobTitle = jobTitle;
            this.level = level;
            this.onlineTime = onlineTime;
            this.todayOnlineTime = todayOnlineTime;
            this.scheduleTime = scheduleTime;
            this.lateEarlyTime = lateEarlyTime;
            this.amount = amount;
        }
    }


}
