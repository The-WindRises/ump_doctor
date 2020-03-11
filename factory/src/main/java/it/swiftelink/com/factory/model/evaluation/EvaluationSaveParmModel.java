package it.swiftelink.com.factory.model.evaluation;

public class EvaluationSaveParmModel {

    private String diagnosisId;
    private String type;
    private String doctorStatus;
    private String patientStatus;
    private String doctorName;
    private int doctorScore;
    private String doctorEvaluation;


    public EvaluationSaveParmModel() {
    }

    public EvaluationSaveParmModel(String diagnosisId, String type, String doctorStatus, String patientStatus, String doctorName, int doctorScore, String doctorEvaluation) {
        this.diagnosisId = diagnosisId;
        this.type = type;
        this.doctorStatus = doctorStatus;
        this.patientStatus = patientStatus;
        this.doctorName = doctorName;
        this.doctorScore = doctorScore;
        this.doctorEvaluation = doctorEvaluation;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
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

    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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
}
