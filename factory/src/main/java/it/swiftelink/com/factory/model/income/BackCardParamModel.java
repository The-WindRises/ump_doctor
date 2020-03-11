package it.swiftelink.com.factory.model.income;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 Arvin
 * @时间 2019/7/25 15:17
 * @一句话描述 绑定银行卡入参
 */

public class BackCardParamModel  {

    private String name;

    private String cardNo;

    private String bank;

    private String mobile;

    public BackCardParamModel() {
    }

    public BackCardParamModel(String name, String cardNo, String bank, String mobile) {
        this.name = name;
        this.cardNo = cardNo;
        this.bank = bank;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
