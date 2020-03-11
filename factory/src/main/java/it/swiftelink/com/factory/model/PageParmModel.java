package it.swiftelink.com.factory.model;

public class PageParmModel {

    private int page;

    private int rows;

    private String loginId;

    private String language;


    public PageParmModel(int page, int rows, String loginId, String language) {
        this.page = page;
        this.rows = rows;
        this.loginId = loginId;
        this.language = language;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
