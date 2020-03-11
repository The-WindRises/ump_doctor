package it.swiftelink.com.factory.model.room;

import android.os.Parcel;
import android.os.Parcelable;

import it.swiftelink.com.common.factory.BaseResModel;

public class ClinicinfoResModel extends BaseResModel {


    private ClinicinfoModel data;


    public ClinicinfoModel getData() {
        return data;
    }

    public void setData(ClinicinfoModel data) {
        this.data = data;
    }

    public ClinicinfoResModel(ClinicinfoModel data) {
        this.data = data;
    }

    public ClinicinfoResModel() {
    }


}
