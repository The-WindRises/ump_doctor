package it.swiftelink.com.factory.model.help;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class HelpResModel extends BaseResModel {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5dfg4fds4h984jnm0fgjk5894s98rg4
         * name : 问诊视频异常中断解决
         * platform : DoctorSide
         * sort : 10
         * status : Yes
         * no : 23
         * language : zh_CN
         * createdBy : null
         * creationDate : null
         * lastUpdatedBy : null
         * lastUpdatedDate : null
         * helpList : [{"id":"245cbc98b05411e98fe80242c0a82002","helpCategoryId":"5dfg4fds4h984jnm0fgjk5894s98rg4","title":"视频中断","platform":"DoctorSide","content":"http://cms.qingbo.co:8091/web/upload/3da74dc4c9b1453ab87b2663566f88f4/web.png","sort":1,"status":"Yes","no":"26","language":"zh_CN","createdBy":"30","creationDate":1564221104000,"lastUpdatedBy":"30","lastUpdatedDate":1564221104000}]
         */

        private String id;
        private String name;
        private String platform;
        private int sort;
        private String status;
        private String no;
        private String language;
        private Object createdBy;
        private Object creationDate;
        private Object lastUpdatedBy;
        private Object lastUpdatedDate;
        private List<HelpListBean> helpList;

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

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(Object creationDate) {
            this.creationDate = creationDate;
        }

        public Object getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(Object lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public Object getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        public void setLastUpdatedDate(Object lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
        }

        public List<HelpListBean> getHelpList() {
            return helpList;
        }

        public void setHelpList(List<HelpListBean> helpList) {
            this.helpList = helpList;
        }

        public static class HelpListBean {
            /**
             * id : 245cbc98b05411e98fe80242c0a82002
             * helpCategoryId : 5dfg4fds4h984jnm0fgjk5894s98rg4
             * title : 视频中断
             * platform : DoctorSide
             * content : http://cms.qingbo.co:8091/web/upload/3da74dc4c9b1453ab87b2663566f88f4/web.png
             * sort : 1
             * status : Yes
             * no : 26
             * language : zh_CN
             * createdBy : 30
             * creationDate : 1564221104000
             * lastUpdatedBy : 30
             * lastUpdatedDate : 1564221104000
             */

            private String id;
            private String helpCategoryId;
            private String title;
            private String platform;
            private String content;
            private int sort;
            private String status;
            private String no;
            private String language;
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

            public String getHelpCategoryId() {
                return helpCategoryId;
            }

            public void setHelpCategoryId(String helpCategoryId) {
                this.helpCategoryId = helpCategoryId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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
        }
    }
}
