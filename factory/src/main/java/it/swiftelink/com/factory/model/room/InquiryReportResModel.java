package it.swiftelink.com.factory.model.room;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class InquiryReportResModel extends BaseResModel {

    /**
     * data : {"id":"da1421f0c1884115bc845d3e1997f5b2","diagnosisId":"40289fe46c46ecRb016c4702201b0003","description":"处方说明哦哦哦阿萨德","validity":5,"prescriptionDrugs":[{"id":"c43ad29bad1411e98fe80242c0a82002","prescriptionId":"da1421f0c1884115bc845d3e1997f5b2","isOrder":"Y","code":"PGC256003P","name":"测试运营员6.26-1","specification":"2.7g*30袋","onceMetering":1,"onceUnit":"袋","usageMethod":"口服","frequency":"每天1次","daysTaken":1,"quantity":10,"unit":null,"price":10,"type":null},{"id":"c43ad29bad1411e98fe80242c0a82003","prescriptionId":"da1421f0c1884115bc845d3e1997f5b2","isOrder":"Y","code":"CAG199001G","name":"测试运营员6.26-3","specification":"2.7g*30袋","onceMetering":1,"onceUnit":"袋","usageMethod":"口服","frequency":"每天1次","daysTaken":1,"quantity":2,"unit":null,"price":20,"type":null}]}
     */
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : da1421f0c1884115bc845d3e1997f5b2
         * diagnosisId : 40289fe46c46ecRb016c4702201b0003
         * description : 处方说明哦哦哦阿萨德
         * validity : 5
         * prescriptionDrugs : [{"id":"c43ad29bad1411e98fe80242c0a82002","prescriptionId":"da1421f0c1884115bc845d3e1997f5b2","isOrder":"Y","code":"PGC256003P","name":"测试运营员6.26-1","specification":"2.7g*30袋","onceMetering":1,"onceUnit":"袋","usageMethod":"口服","frequency":"每天1次","daysTaken":1,"quantity":10,"unit":null,"price":10,"type":null},{"id":"c43ad29bad1411e98fe80242c0a82003","prescriptionId":"da1421f0c1884115bc845d3e1997f5b2","isOrder":"Y","code":"CAG199001G","name":"测试运营员6.26-3","specification":"2.7g*30袋","onceMetering":1,"onceUnit":"袋","usageMethod":"口服","frequency":"每天1次","daysTaken":1,"quantity":2,"unit":null,"price":20,"type":null}]
         */

        private String id;
        private String diagnosisId;
        private String description;
        private int validity;
        private List<PrescriptionDrugsBean> prescriptionDrugs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDiagnosisId() {
            return diagnosisId;
        }

        public void setDiagnosisId(String diagnosisId) {
            this.diagnosisId = diagnosisId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getValidity() {
            return validity;
        }

        public void setValidity(int validity) {
            this.validity = validity;
        }

        public List<PrescriptionDrugsBean> getPrescriptionDrugs() {
            return prescriptionDrugs;
        }

        public void setPrescriptionDrugs(List<PrescriptionDrugsBean> prescriptionDrugs) {
            this.prescriptionDrugs = prescriptionDrugs;
        }

        public static class PrescriptionDrugsBean {
            /**
             * id : c43ad29bad1411e98fe80242c0a82002
             * prescriptionId : da1421f0c1884115bc845d3e1997f5b2
             * isOrder : Y
             * code : PGC256003P
             * name : 测试运营员6.26-1
             * specification : 2.7g*30袋
             * onceMetering : 1.0
             * onceUnit : 袋
             * usageMethod : 口服
             * frequency : 每天1次
             * daysTaken : 1
             * quantity : 10
             * unit : null
             * price : 10.0
             * type : null
             */

            private String id;
            private String prescriptionId;
            private String isOrder;
            @SerializedName("code")
            private String codeX;
            private String totalAmount;
            private String name;
            private String specification;
            private String onceMetering;
            private String onceUnit;
            private String usageMethod;
            private String frequency;
            private String daysTaken;
            private String quantity;
            private String unit;
            private String price;
            private String type;

            public PrescriptionDrugsBean() {
            }

            public PrescriptionDrugsBean(String id, String prescriptionId, String isOrder, String codeX, String name, String specification, String onceMetering, String onceUnit, String usageMethod, String frequency, String daysTaken, String quantity, String unit, String price, String type) {
                this.id = id;
                this.prescriptionId = prescriptionId;
                this.isOrder = isOrder;
                this.codeX = codeX;
                this.name = name;
                this.specification = specification;
                this.onceMetering = onceMetering;
                this.onceUnit = onceUnit;
                this.usageMethod = usageMethod;
                this.frequency = frequency;
                this.daysTaken = daysTaken;
                this.quantity = quantity;
                this.unit = unit;
                this.price = price;
                this.type = type;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPrescriptionId() {
                return prescriptionId;
            }

            public void setPrescriptionId(String prescriptionId) {
                this.prescriptionId = prescriptionId;
            }

            public String getIsOrder() {
                return isOrder;
            }

            public void setIsOrder(String isOrder) {
                this.isOrder = isOrder;
            }

            public String getCodeX() {
                return codeX;
            }

            public void setCodeX(String codeX) {
                this.codeX = codeX;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public String getOnceMetering() {
                return onceMetering;
            }

            public void setOnceMetering(String onceMetering) {
                this.onceMetering = onceMetering;
            }

            public String getOnceUnit() {
                return onceUnit;
            }

            public void setOnceUnit(String onceUnit) {
                this.onceUnit = onceUnit;
            }

            public String getUsageMethod() {
                return usageMethod;
            }

            public void setUsageMethod(String usageMethod) {
                this.usageMethod = usageMethod;
            }

            public String getFrequency() {
                return frequency;
            }

            public void setFrequency(String frequency) {
                this.frequency = frequency;
            }

            public String getDaysTaken() {
                return daysTaken;
            }

            public void setDaysTaken(String daysTaken) {
                this.daysTaken = daysTaken;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
