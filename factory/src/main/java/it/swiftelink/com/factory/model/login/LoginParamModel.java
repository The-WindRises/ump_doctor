package it.swiftelink.com.factory.model.login;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 Arvin
 * @时间 2019/7/24 19:19
 * @一句话描述 登录入参
 */

public class LoginParamModel implements Parcelable {

    private String mobile;
    private String password;


    public LoginParamModel(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        dest.writeString(this.password);
    }

    protected LoginParamModel(Parcel in) {
        this.mobile = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<LoginParamModel> CREATOR = new Parcelable.Creator<LoginParamModel>() {
        @Override
        public LoginParamModel createFromParcel(Parcel source) {
            return new LoginParamModel(source);
        }

        @Override
        public LoginParamModel[] newArray(int size) {
            return new LoginParamModel[size];
        }
    };
}
