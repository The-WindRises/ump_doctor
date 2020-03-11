package it.swiftelink.com.factory.model.user;

import it.swiftelink.com.common.factory.BaseResModel;

public class AgrreementResModel extends BaseResModel {


    private Agrreement data;

    public AgrreementResModel(Agrreement data) {
        this.data = data;
    }

    public Agrreement getData() {
        return data;
    }

    public void setData(Agrreement data) {
        this.data = data;
    }

    public static class Agrreement {
        private String id;
        private String title;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
