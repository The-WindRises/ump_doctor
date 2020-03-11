package it.swiftelink.com.factory.model.scheduling;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.factory.model.income.BackResModel;

public class SchedulingResModel extends BaseResModel {

    private List<Scheduling> data;

    public List<Scheduling> getData() {
        return data;
    }

    public void setData(List<Scheduling> data) {
        this.data = data;
    }

    public SchedulingResModel(List<Scheduling> data) {
        this.data = data;
    }

    public static class Scheduling {

        private String date;

        public Scheduling(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Scheduling() {
        }
    }


}
