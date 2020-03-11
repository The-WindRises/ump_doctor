package it.swiftelink.com.factory.model.room;

import it.swiftelink.com.common.factory.BaseResModel;

public class PatientInfoResModel extends BaseResModel {

    private PatientInfo data;

    public PatientInfo getData() {
        return data;
    }

    public void setData(PatientInfo data) {
        this.data = data;
    }

    public PatientInfoResModel() {
    }

    public PatientInfoResModel(PatientInfo data) {
        this.data = data;
    }

    public static class PatientInfo {

        private String no;

        private String patientName;

        private String doctorName;

        private String patientId;

        private String doctorId;

        private String doctorHeadImg;

        private String uuid;

        private String patientHeadImg;

        public PatientInfo() {
        }

        public PatientInfo(String no, String patientName, String doctorName, String patientId, String doctorId, String doctorHeadImg, String uuid, String patientHeadImg) {
            this.no = no;
            this.patientName = patientName;
            this.doctorName = doctorName;
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.doctorHeadImg = doctorHeadImg;
            this.uuid = uuid;
            this.patientHeadImg = patientHeadImg;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorHeadImg() {
            return doctorHeadImg;
        }

        public void setDoctorHeadImg(String doctorHeadImg) {
            this.doctorHeadImg = doctorHeadImg;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getPatientHeadImg() {
            return patientHeadImg;
        }

        public void setPatientHeadImg(String patientHeadImg) {
            this.patientHeadImg = patientHeadImg;
        }
    }


}
