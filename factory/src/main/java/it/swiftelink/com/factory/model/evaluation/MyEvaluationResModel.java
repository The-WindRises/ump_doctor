package it.swiftelink.com.factory.model.evaluation;


import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;


/**
 * @作者 Arvin
 * @时间 2019/7/26 20:25
 * @一句话描述 用户评价返回结果
 */

public class MyEvaluationResModel extends BaseResModel {


    private DataModel data;

    public MyEvaluationResModel(DataModel data) {
        this.data = data;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public static class DataModel {

        private List<MyEvaluation> dataList;
        private int totalPages;
        private int dataCount;

        public DataModel(List<MyEvaluation> dataList, int totalPages, int dataCount) {
            this.dataList = dataList;
            this.totalPages = totalPages;
            this.dataCount = dataCount;
        }

        public List<MyEvaluation> getDataList() {
            return dataList;
        }

        public void setDataList(List<MyEvaluation> dataList) {
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


    public static class MyEvaluation {
        private String id;
        private String diagnosisId;
        private String no;
        private String doctorName;
        private String patientName;
        private long diagnosisStartTime;
        private String initialDiagnosis;
        private String type;
        private String doctorStatus;
        private int doctorScore;
        private String doctorEvaluation;
        private long doctorDate;
        private String patientStatus;
        private int patientScore;
        private String patientEvaluation;
        private long patientDate;
        private String patientHeadImg;

        public MyEvaluation() {
        }

        public MyEvaluation(String id, String diagnosisId, String no, String doctorName, String patientName, long diagnosisStartTime, String initialDiagnosis, String type, String doctorStatus, int doctorScore, String doctorEvaluation, long doctorDate, String patientStatus, int patientScore, String patientEvaluation, long patientDate, String patientHeadImg) {
            this.id = id;
            this.diagnosisId = diagnosisId;
            this.no = no;
            this.doctorName = doctorName;
            this.patientName = patientName;
            this.diagnosisStartTime = diagnosisStartTime;
            this.initialDiagnosis = initialDiagnosis;
            this.type = type;
            this.doctorStatus = doctorStatus;
            this.doctorScore = doctorScore;
            this.doctorEvaluation = doctorEvaluation;
            this.doctorDate = doctorDate;
            this.patientStatus = patientStatus;
            this.patientScore = patientScore;
            this.patientEvaluation = patientEvaluation;
            this.patientDate = patientDate;
            this.patientHeadImg = patientHeadImg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDiagnosisId() {
            return diagnosisId;
        }

        public void setDiagnosisId(String diagnosisId) {
            this.diagnosisId = diagnosisId;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public long getDiagnosisStartTime() {
            return diagnosisStartTime;
        }

        public void setDiagnosisStartTime(long diagnosisStartTime) {
            this.diagnosisStartTime = diagnosisStartTime;
        }

        public String getInitialDiagnosis() {
            return initialDiagnosis;
        }

        public void setInitialDiagnosis(String initialDiagnosis) {
            this.initialDiagnosis = initialDiagnosis;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDoctorStatus() {
            return doctorStatus;
        }

        public void setDoctorStatus(String doctorStatus) {
            this.doctorStatus = doctorStatus;
        }

        public int getDoctorScore() {
            return doctorScore;
        }

        public void setDoctorScore(int doctorScore) {
            this.doctorScore = doctorScore;
        }

        public String getDoctorEvaluation() {
            return doctorEvaluation;
        }

        public void setDoctorEvaluation(String doctorEvaluation) {
            this.doctorEvaluation = doctorEvaluation;
        }

        public long getDoctorDate() {
            return doctorDate;
        }

        public void setDoctorDate(long doctorDate) {
            this.doctorDate = doctorDate;
        }

        public String getPatientStatus() {
            return patientStatus;
        }

        public void setPatientStatus(String patientStatus) {
            this.patientStatus = patientStatus;
        }

        public int getPatientScore() {
            return patientScore;
        }

        public void setPatientScore(int patientScore) {
            this.patientScore = patientScore;
        }

        public String getPatientEvaluation() {
            return patientEvaluation;
        }

        public void setPatientEvaluation(String patientEvaluation) {
            this.patientEvaluation = patientEvaluation;
        }

        public long getPatientDate() {
            return patientDate;
        }

        public void setPatientDate(long patientDate) {
            this.patientDate = patientDate;
        }

        public String getPatientHeadImg() {
            return patientHeadImg;
        }

        public void setPatientHeadImg(String patientHeadImg) {
            this.patientHeadImg = patientHeadImg;
        }
    }

}
