package it.swiftelink.com.factory.model.evaluation;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class EvaluationResModel  extends BaseResModel {

    /**
     * data : {"dataList":[{"id":"68c49983b4f511e9a23c0242c0a82002","sort":1,"score":5,"name":"非常棒","type":"Doctor","no":"1","language":"zh_CN","status":"1","createdBy":"65","creationDate":1564776972000,"lastUpdatedBy":"65","lastUpdatedDate":1564776972000},{"id":"04cd34a3a38911e98fe80242c0a82002","sort":2,"score":5,"name":"能力突出","type":"Doctor","no":"2","language":"zh_CN","status":"1","createdBy":"66","creationDate":1562861249000,"lastUpdatedBy":null,"lastUpdatedDate":1563577083000}],"totalPages":1,"dataCount":2}
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
         * dataList : [{"id":"68c49983b4f511e9a23c0242c0a82002","sort":1,"score":5,"name":"非常棒","type":"Doctor","no":"1","language":"zh_CN","status":"1","createdBy":"65","creationDate":1564776972000,"lastUpdatedBy":"65","lastUpdatedDate":1564776972000},{"id":"04cd34a3a38911e98fe80242c0a82002","sort":2,"score":5,"name":"能力突出","type":"Doctor","no":"2","language":"zh_CN","status":"1","createdBy":"66","creationDate":1562861249000,"lastUpdatedBy":null,"lastUpdatedDate":1563577083000}]
         * totalPages : 1
         * dataCount : 2
         */

        private int totalPages;
        private int dataCount;
        private List<DataListBean> dataList;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getDataCount() {
            return dataCount;
        }

        public void setDataCount(int dataCount) {
            this.dataCount = dataCount;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * id : 68c49983b4f511e9a23c0242c0a82002
             * sort : 1
             * score : 5
             * name : 非常棒
             * type : Doctor
             * no : 1
             * language : zh_CN
             * status : 1
             * createdBy : 65
             * creationDate : 1564776972000
             * lastUpdatedBy : 65
             * lastUpdatedDate : 1564776972000
             */

            private String id;
            private int sort;
            private int score;
            private String name;
            private String type;
            private String no;
            private String language;
            private String status;
            private String createdBy;
            private long creationDate;
            private String lastUpdatedBy;
            private long lastUpdatedDate;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public long getCreationDate() {
                return creationDate;
            }

            public void setCreationDate(long creationDate) {
                this.creationDate = creationDate;
            }

            public String getLastUpdatedBy() {
                return lastUpdatedBy;
            }

            public void setLastUpdatedBy(String lastUpdatedBy) {
                this.lastUpdatedBy = lastUpdatedBy;
            }

            public long getLastUpdatedDate() {
                return lastUpdatedDate;
            }

            public void setLastUpdatedDate(long lastUpdatedDate) {
                this.lastUpdatedDate = lastUpdatedDate;
            }
        }
    }
}
