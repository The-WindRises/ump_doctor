package it.swiftelink.com.factory.model.home;

import it.swiftelink.com.common.factory.BaseResModel;

public class StatisticsResModel extends BaseResModel {

    private Statistic data;

    public StatisticsResModel(Statistic data) {
        this.data = data;
    }

    public Statistic getData() {
        return data;
    }

    public void setData(Statistic data) {
        this.data = data;
    }

    public static class Statistic {

        private String approvalReport;

        private String onLineTime;

        private String numberOfOrders;

        private String income;

        private String totalApprovalReports;

        private String totalOnlineTime;

        private String totalNumberOfOrders;

        private String totalIncome;

        public Statistic() {
        }

        public Statistic(String approvalReport, String onLineTime, String numberOfOrders, String income, String totalApprovalReports, String totalOnlineTime, String totalNumberOfOrders, String totalIncome) {
            this.approvalReport = approvalReport;
            this.onLineTime = onLineTime;
            this.numberOfOrders = numberOfOrders;
            this.income = income;
            this.totalApprovalReports = totalApprovalReports;
            this.totalOnlineTime = totalOnlineTime;
            this.totalNumberOfOrders = totalNumberOfOrders;
            this.totalIncome = totalIncome;
        }

        public String getApprovalReport() {
            return approvalReport;
        }

        public void setApprovalReport(String approvalReport) {
            this.approvalReport = approvalReport;
        }

        public String getOnLineTime() {
            return onLineTime;
        }

        public void setOnLineTime(String onLineTime) {
            this.onLineTime = onLineTime;
        }

        public String getNumberOfOrders() {
            return numberOfOrders;
        }

        public void setNumberOfOrders(String numberOfOrders) {
            this.numberOfOrders = numberOfOrders;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getTotalApprovalReports() {
            return totalApprovalReports;
        }

        public void setTotalApprovalReports(String totalApprovalReports) {
            this.totalApprovalReports = totalApprovalReports;
        }

        public String getTotalOnlineTime() {
            return totalOnlineTime;
        }

        public void setTotalOnlineTime(String totalOnlineTime) {
            this.totalOnlineTime = totalOnlineTime;
        }

        public String getTotalNumberOfOrders() {
            return totalNumberOfOrders;
        }

        public void setTotalNumberOfOrders(String totalNumberOfOrders) {
            this.totalNumberOfOrders = totalNumberOfOrders;
        }

        public String getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome = totalIncome;
        }
    }


}
