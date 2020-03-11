package it.swiftelink.com.factory.model.room;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class DiagnosisOrderResModel extends BaseResModel {

    private String channel;
    private String diagnosisLanguage;
    private long diagnosisStartTime;
    private String gender;
    private String id;
    private String medicalRecord;
    private String no;
    private String patientHeadImg;
    private String patientId;
    private String patientName;
    private String symptomDescription;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDiagnosisLanguage() {
        return diagnosisLanguage;
    }

    public void setDiagnosisLanguage(String diagnosisLanguage) {
        this.diagnosisLanguage = diagnosisLanguage;
    }

    public long getDiagnosisStartTime() {
        return diagnosisStartTime;
    }

    public void setDiagnosisStartTime(long diagnosisStartTime) {
        this.diagnosisStartTime = diagnosisStartTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPatientHeadImg() {
        return patientHeadImg;
    }

    public void setPatientHeadImg(String patientHeadImg) {
        this.patientHeadImg = patientHeadImg;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSymptomDescription() {
        return symptomDescription;
    }

    public void setSymptomDescription(String symptomDescription) {
        this.symptomDescription = symptomDescription;
    }

}
