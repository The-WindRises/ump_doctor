package it.swiftelink.com.factory.model.income;

import it.swiftelink.com.common.factory.BaseResModel;

public class BalanceResModel extends BaseResModel {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BalanceResModel(String data) {
        this.data = data;
    }
}
