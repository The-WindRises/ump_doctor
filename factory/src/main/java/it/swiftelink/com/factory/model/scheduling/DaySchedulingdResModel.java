package it.swiftelink.com.factory.model.scheduling;

import java.io.Serializable;
import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/25 19:56
 * @一句话描述 每天的排班
 */

public class DaySchedulingdResModel extends BaseResModel {

    private List<DaySchedulingd> data;

    public DaySchedulingdResModel() {
    }

    public DaySchedulingdResModel(List<DaySchedulingd> data) {
        this.data = data;
    }

    public List<DaySchedulingd> getData() {
        return data;
    }

    public void setData(List<DaySchedulingd> data) {
        this.data = data;
    }

    public static class DaySchedulingd implements Serializable {
        private String startTime;
        private String endTime;
        private String shiftId;

        public DaySchedulingd(String startTime, String endTime, String shiftId) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.shiftId = shiftId;
        }

        public String getShiftId() {
            return shiftId;
        }

        public void setShiftId(String shiftId) {
            this.shiftId = shiftId;
        }

        public DaySchedulingd() {
        }

        public DaySchedulingd(String startTime, String endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

}
