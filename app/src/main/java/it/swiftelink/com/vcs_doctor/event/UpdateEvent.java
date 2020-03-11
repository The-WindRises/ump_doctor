package it.swiftelink.com.vcs_doctor.event;

public class UpdateEvent {
    private int type;
    private String mes;

    public UpdateEvent(int type) {
        this.type = type;
    }

    public UpdateEvent(int type, String mes) {
        this.type = type;
        this.mes = mes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
