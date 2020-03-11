package it.swiftelink.com.vcs_doctor.event;

/**
 * Created by Administrator on 2018/11/28.
 */

public class MsgEvent {

    private int type;
    private String msg;
    public MsgEvent(int type) {
        this.type = type;
    }

    public MsgEvent(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
