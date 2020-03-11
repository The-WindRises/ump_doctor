package it.swiftelink.com.factory.model.message;

import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class MessageResModel extends BaseResModel {

    /**
     * data : {"dataList":[{"id":"d7ef3ec4ad1a11e98fe80242c0a82002","userId":32,"title":"系统公告通知","content":"【联合医务】[驱蚊器翁奥所多]. [UMP] [驱蚊器翁奥所多].","status":"Unread","createdBy":"33","creationDate":1563913441000,"lastUpdatedBy":"33","lastUpdatedDate":1563913441000}],"totalPages":1,"dataCount":1}
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
         * dataList : [{"id":"d7ef3ec4ad1a11e98fe80242c0a82002","userId":32,"title":"系统公告通知","content":"【联合医务】[驱蚊器翁奥所多]. [UMP] [驱蚊器翁奥所多].","status":"Unread","createdBy":"33","creationDate":1563913441000,"lastUpdatedBy":"33","lastUpdatedDate":1563913441000}]
         * totalPages : 1
         * dataCount : 1
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
             * id : d7ef3ec4ad1a11e98fe80242c0a82002
             * userId : 32
             * title : 系统公告通知
             * content : 【联合医务】[驱蚊器翁奥所多]. [UMP] [驱蚊器翁奥所多].
             * status : Unread
             * createdBy : 33
             * creationDate : 1563913441000
             * lastUpdatedBy : 33
             * lastUpdatedDate : 1563913441000
             */

            private String id;
            private String userId;
            private String title;
            private String content;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
