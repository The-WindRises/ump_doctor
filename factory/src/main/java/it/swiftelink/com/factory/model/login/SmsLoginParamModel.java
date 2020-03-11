package it.swiftelink.com.factory.model.login;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 Arvin
 * @时间 2019/7/24 19:19
 * @一句话描述 登录入参
 */

public class SmsLoginParamModel implements Parcelable {

    private String mobile;
    private String smsCode;
    private String type = "Internal";

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SmsLoginParamModel(String mobile, String smsCode) {
        this.mobile = mobile;
        this.smsCode = smsCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobile);
        dest.writeString(this.smsCode);
        dest.writeString(this.type);
    }

    public SmsLoginParamModel() {
    }

    protected SmsLoginParamModel(Parcel in) {
        this.mobile = in.readString();
        this.smsCode = in.readString();
        this.type = in.readString();
    }

    public static final Creator<SmsLoginParamModel> CREATOR = new Creator<SmsLoginParamModel>() {
        @Override
        public SmsLoginParamModel createFromParcel(Parcel source) {
            return new SmsLoginParamModel(source);
        }

        @Override
        public SmsLoginParamModel[] newArray(int size) {
            return new SmsLoginParamModel[size];
        }
    };
}


