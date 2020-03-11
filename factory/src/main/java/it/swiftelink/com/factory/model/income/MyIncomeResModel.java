package it.swiftelink.com.factory.model.income;

import it.swiftelink.com.common.factory.BaseResModel;

public class MyIncomeResModel extends BaseResModel {

    /**
     * data : {"id":"DR000031","accumulatedIncome":"90","name":"张医生","amount":1000,"cashIncome":"0","createdBy":null,"creationDate":1560562103000,"lastUpdatedBy":null,"lastUpdatedDate":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String accumulatedIncome;
        private String amount;
        private String cashIncome;

        public String getAccumulatedIncome() {
            return accumulatedIncome;
        }

        public void setAccumulatedIncome(String accumulatedIncome) {
            this.accumulatedIncome = accumulatedIncome;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCashIncome() {
            return cashIncome;
        }

        public void setCashIncome(String cashIncome) {
            this.cashIncome = cashIncome;
        }
    }
}
