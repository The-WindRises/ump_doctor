package it.swiftelink.com.factory.model.videoChat;

import java.util.List;

public class TrtcConfigResModel {


    /**
     * sdkappid : 1400218803
     * roomId : 15
     * users : [{"userId":"116","userToken":"eJw1j1FvgjAURv8Lz8soFLAs8QEVFaeSRTRxL01t67zSVcZKxBn-*xDx8Z7z8J17tbL56pUVBQjKDMWlsN4sZL20WNYFlJKyvZFlgx3f912EnhaE1Ab28HBO0OFf*GruRbweJpOtysa5R6bMMJ4mPbIM8s1xkE0*7RBG61lqq8OHHvvvMowgjk6V*tMKztM64pdZlICz49iz53qhjiL42VRpWrs4PpBd3n*OiZy28fcEDyHXIQThThr4lm12gEMcEtLrOOP8VGlDzaWQ7be3f90aTq4_"}]
     * errorMessage :
     */
    private int sdkappid;
    private int roomId;
    private int code;
    private String message;
    private boolean success;
    private String errorMessage;
    private List<UsersBean> users;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getSdkappid() {
        return sdkappid;
    }

    public void setSdkappid(int sdkappid) {
        this.sdkappid = sdkappid;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        /**
         * userId : 116
         * userToken : eJw1j1FvgjAURv8Lz8soFLAs8QEVFaeSRTRxL01t67zSVcZKxBn-*xDx8Z7z8J17tbL56pUVBQjKDMWlsN4sZL20WNYFlJKyvZFlgx3f912EnhaE1Ab28HBO0OFf*GruRbweJpOtysa5R6bMMJ4mPbIM8s1xkE0*7RBG61lqq8OHHvvvMowgjk6V*tMKztM64pdZlICz49iz53qhjiL42VRpWrs4PpBd3n*OiZy28fcEDyHXIQThThr4lm12gEMcEtLrOOP8VGlDzaWQ7be3f90aTq4_
         */

        private String userId;
        private String userToken;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }
    }
}
