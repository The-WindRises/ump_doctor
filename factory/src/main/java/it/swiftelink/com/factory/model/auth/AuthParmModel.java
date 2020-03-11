package it.swiftelink.com.factory.model.auth;

import java.util.List;

public class AuthParmModel {

    private List<DoctorFile> doctorFileList;

    public List<DoctorFile> getDoctorFileList() {
        return doctorFileList;
    }

    public void setDoctorFileList(List<DoctorFile> doctorFileList) {
        this.doctorFileList = doctorFileList;
    }

    public AuthParmModel() {
    }

    public AuthParmModel(List<DoctorFile> doctorFileList) {
        this.doctorFileList = doctorFileList;
    }
}
