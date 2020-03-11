package it.swiftelink.com.factory.model.login;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @作者 Arvin
 * @时间 2019/7/24 16:34
 * @一句话描述 注册入参
 */
public class RegisterParamModel implements Parcelable {
    private String mobile;
    private String smsCode;
    private String password;
    private String type;
    private String agrreementId;

    public String getAgrreementId() {
        return agrreementId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAgrreementId(String agrreementId) {
        this.agrreementId = agrreementId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobile);
        dest.writeString(this.smsCode);
        dest.writeString(this.password);
    }

    public RegisterParamModel() {
    }

    protected RegisterParamModel(Parcel in) {
        this.mobile = in.readString();
        this.smsCode = in.readString();
        this.password = in.readString();
    }

    public static final Creator<RegisterParamModel> CREATOR = new Creator<RegisterParamModel>() {
        @Override
        public RegisterParamModel createFromParcel(Parcel source) {
            return new RegisterParamModel(source);
        }

        @Override
        public RegisterParamModel[] newArray(int size) {
            return new RegisterParamModel[size];
        }
    };
}
