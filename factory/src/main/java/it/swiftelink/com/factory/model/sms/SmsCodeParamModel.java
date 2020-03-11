package it.swiftelink.com.factory.model.sms;

import android.os.Parcel;
import android.os.Parcelable;

public class SmsCodeParamModel implements Parcelable {

    private String mobile;

    private String type;

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

    public SmsCodeParamModel(String mobile, String type) {
        this.mobile = mobile;
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobile);
        dest.writeString(this.type);
    }

    protected SmsCodeParamModel(Parcel in) {
        this.mobile = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<SmsCodeParamModel> CREATOR = new Parcelable.Creator<SmsCodeParamModel>() {
        @Override
        public SmsCodeParamModel createFromParcel(Parcel source) {
            return new SmsCodeParamModel(source);
        }

        @Override
        public SmsCodeParamModel[] newArray(int size) {
            return new SmsCodeParamModel[size];
        }
    };
}
