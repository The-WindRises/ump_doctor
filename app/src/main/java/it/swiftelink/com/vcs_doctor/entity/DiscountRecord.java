package it.swiftelink.com.vcs_doctor.entity;

public class DiscountRecord {

    private String date;

    private String channel;

    private String order;

    private String amount;

    public DiscountRecord(String date, String channel, String order, String amount) {
        this.date = date;
        this.channel = channel;
        this.order = order;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
