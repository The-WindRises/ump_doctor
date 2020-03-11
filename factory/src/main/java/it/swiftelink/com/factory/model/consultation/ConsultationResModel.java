package it.swiftelink.com.factory.model.consultation;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class ConsultationResModel extends BaseResModel {


    private DataModel data;

    public ConsultationResModel(DataModel data) {
        this.data = data;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public static class DataModel {

        private int totalPages;
        private int dataCount;
        private List<ConsultationModel> dataList;

        public DataModel() {
        }

        public DataModel(int totalPages, int dataCount, List<ConsultationModel> dataList) {
            this.totalPages = totalPages;
            this.dataCount = dataCount;
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

        public List<ConsultationModel> getDataList() {
            return dataList;
        }

        public void setDataList(List<ConsultationModel> dataList) {
            this.dataList = dataList;
        }
    }

    public static class ConsultationModel {
        private String no;
        private String patientName;
        private String status;
        private String prescriptionId;
        private String patientId;
        private String doctorId;
        private String preliminaryDiagnosis;
        private long creationDate;
        private String disgnosisReportId;
        private String symptomDescription;
        private String patientHeadImg;
        private long diagnosisStartTime;
        private long disgonsisDuration;

        public ConsultationModel(String no, String patientName, String status, String prescriptionId, String patientId, String doctorId, String preliminaryDiagnosis, long creationDate, String disgnosisReportId, String symptomDescription, String patientHeadImg) {
            this.no = no;
            this.patientName = patientName;
            this.status = status;
            this.prescriptionId = prescriptionId;
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.preliminaryDiagnosis = preliminaryDiagnosis;
            this.creationDate = creationDate;
            this.disgnosisReportId = disgnosisReportId;
            this.symptomDescription = symptomDescription;
            this.patientHeadImg = patientHeadImg;
        }

        public long getDisgonsisDuration() {
            return disgonsisDuration;
        }

        public void setDisgonsisDuration(long disgonsisDuration) {
            this.disgonsisDuration = disgonsisDuration;
        }

        public ConsultationModel() {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPrescriptionId() {
            return prescriptionId;
        }

        public void setPrescriptionId(String prescriptionId) {
            this.prescriptionId = prescriptionId;
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

        public String getPreliminaryDiagnosis() {
            return preliminaryDiagnosis;
        }

        public void setPreliminaryDiagnosis(String preliminaryDiagnosis) {
            this.preliminaryDiagnosis = preliminaryDiagnosis;
        }

        public long getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(long creationDate) {
            this.creationDate = creationDate;
        }

        public String getDisgnosisReportId() {
            return disgnosisReportId;
        }

        public void setDisgnosisReportId(String disgnosisReportId) {
            this.disgnosisReportId = disgnosisReportId;
        }

        public String getSymptomDescription() {
            return symptomDescription;
        }

        public void setSymptomDescription(String symptomDescription) {
            this.symptomDescription = symptomDescription;
        }

        public String getPatientHeadImg() {
            return patientHeadImg;
        }

        public void setPatientHeadImg(String patientHeadImg) {
            this.patientHeadImg = patientHeadImg;
        }
        public long getDiagnosisStartTime() {
            return diagnosisStartTime;
        }
        public void setDiagnosisStartTime(long diagnosisStartTime) {
            this.diagnosisStartTime = diagnosisStartTime;
        }
    }


}
