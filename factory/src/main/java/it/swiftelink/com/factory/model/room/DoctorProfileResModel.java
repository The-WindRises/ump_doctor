package it.swiftelink.com.factory.model.room;

import it.swiftelink.com.common.factory.BaseResModel;

public class DoctorProfileResModel extends BaseResModel {

    private DataModel data;

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public static class DataModel {


        private String synopsis;

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public DataModel(String synopsis) {
            this.synopsis = synopsis;
        }


    }

}
