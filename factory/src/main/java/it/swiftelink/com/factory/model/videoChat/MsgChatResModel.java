package it.swiftelink.com.factory.model.videoChat;

public class MsgChatResModel {

    public static final int type_send= 0;
    public static final int type_responds= 1;


    public MsgChatResModel(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
