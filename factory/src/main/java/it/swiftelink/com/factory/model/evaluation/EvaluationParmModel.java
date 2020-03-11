package it.swiftelink.com.factory.model.evaluation;

public class EvaluationParmModel {
    private String type;
    private String language;
    private int score;

    public EvaluationParmModel() {
    }

    public EvaluationParmModel(String type, String language, int score) {
        this.type = type;
        this.language = language;
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public float getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
