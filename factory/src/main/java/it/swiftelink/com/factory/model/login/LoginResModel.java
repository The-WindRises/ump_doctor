package it.swiftelink.com.factory.model.login;

import it.swiftelink.com.common.factory.BaseResModel;


/**
 * @作者 Arvin
 * @时间 2019/7/24 19:43
 * @一句话描述 登录返回结果
 */

public class LoginResModel extends BaseResModel {

    private User data;

    public LoginResModel() {
    }

    public LoginResModel(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public static class User {
        private String accessToken;
        private String userId;
        private String loginId;
        private String doctorStatus;

        public String getDoctorStatus() {
            return doctorStatus;
        }

        public void setDoctorStatus(String doctorStatus) {
            this.doctorStatus = doctorStatus;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAccessToken() {
            return accessToken;
        }


        public User() {
        }

        public User(String accessToken, String userId) {
            this.accessToken = accessToken;
            this.userId = userId;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
