package it.swiftelink.com.factory.model.login;

import it.swiftelink.com.common.factory.BaseResModel;

public class RegisterResModel extends BaseResModel {


    private User data;

    public RegisterResModel() {
    }

    public RegisterResModel(User data) {
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
        private String doctorStatus;
        private String userId;
        private String loginId;

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
