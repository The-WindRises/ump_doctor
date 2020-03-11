package it.swiftelink.com.factory.model.auth;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class DepartmentResModel  extends BaseResModel {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 023cb1d3c59e11e998960242c0a82002
         * name : 神经科
         * parentId : 0
         * no : A1222
         * language : zh_CN
         * createdBy : 30
         * creationDate : 1566561804000
         * lastUpdatedBy : 30
         * lastUpdatedDate : 1566561804000
         * sectionOfficeList : [{"id":"0cc2a0ccc59e11e998960242c0a82002","name":"脑部神经11","parentId":"023cb1d3c59e11e998960242c0a82002","no":null,"language":null,"createdBy":"30","creationDate":1566561822000,"lastUpdatedBy":"30","lastUpdatedDate":1566561822000,"sectionOfficeList":null}]
         */

        private String id;
        private String name;
        private String parentId;
        private String no;
        private String language;
        private String createdBy;
        private long creationDate;
        private String lastUpdatedBy;
        private long lastUpdatedDate;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
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

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 0cc2a0ccc59e11e998960242c0a82002
             * name : 脑部神经11
             * parentId : 023cb1d3c59e11e998960242c0a82002
             * no : null
             * language : null
             * createdBy : 30
             * creationDate : 1566561822000
             * lastUpdatedBy : 30
             * lastUpdatedDate : 1566561822000
             * sectionOfficeList : null
             */

            private String id;
            private String name;
            private String parentId;
            private Object no;
            private Object language;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public Object getNo() {
                return no;
            }

            public void setNo(Object no) {
                this.no = no;
            }

            public Object getLanguage() {
                return language;
            }

            public void setLanguage(Object language) {
                this.language = language;
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
