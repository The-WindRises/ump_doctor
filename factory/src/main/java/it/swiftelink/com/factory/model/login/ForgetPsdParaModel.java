package it.swiftelink.com.factory.model.login;

public class ForgetPsdParaModel {
    private String mobile;
    private String type;
    private String smsCode;
    private String password;

    public ForgetPsdParaModel() {
    }

    public ForgetPsdParaModel(String mobile, String type, String smsCode, String password) {
        this.mobile = mobile;
        this.type = type;
        this.smsCode = smsCode;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
