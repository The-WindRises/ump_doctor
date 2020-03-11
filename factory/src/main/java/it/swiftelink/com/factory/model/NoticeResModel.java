package it.swiftelink.com.factory.model;

import it.swiftelink.com.common.factory.BaseResModel;

public class NoticeResModel extends BaseResModel {

    private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public NoticeResModel() {
    }

    public NoticeResModel(boolean data) {
        this.data = data;
    }
}
