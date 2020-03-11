package it.swiftelink.com.factory.model.evaluation;

public class EvaluationUpdateParmModel {

    private String id;
    private int doctorScore;
    private String doctorEvaluation;
    private String doctorStatus;

    public EvaluationUpdateParmModel() {
    }

    public EvaluationUpdateParmModel(String id, int doctorScore, String doctorEvaluation, String doctorStatus) {
        this.id = id;
        this.doctorScore = doctorScore;
        this.doctorEvaluation = doctorEvaluation;
        this.doctorStatus = doctorStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDoctorStatus() {
        return doctorStatus;
    }

    public void setDoctorStatus(String doctorStatus) {
        this.doctorStatus = doctorStatus;
    }
}
