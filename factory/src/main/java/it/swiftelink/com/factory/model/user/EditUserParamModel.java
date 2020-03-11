package it.swiftelink.com.factory.model.user;

import java.io.Serializable;
import java.util.List;

import it.swiftelink.com.factory.model.auth.DoctorFile;

/**
 * @作者 Arvin
 * @时间 2019/10/29 17:37
 * @一句话描述 修改 个人信息
 */

public class EditUserParamModel implements Serializable {


    private String headImg;
    private String hospital;
    private String sectionOfficeId;
    private String sectionOfficeTel;
    private String practiceYear;
    private String emergencyContact;
    private String emergencyContactTel;
    private String contactAddr;
    private String language;
    private String description;
    private List<DoctorFile> doctorFileApprovalVO;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getSectionOfficeId() {
        return sectionOfficeId;
    }

    public void setSectionOfficeId(String sectionOfficeId) {
        this.sectionOfficeId = sectionOfficeId;
    }

    public String getSectionOfficeTel() {
        return sectionOfficeTel;
    }

    public void setSectionOfficeTel(String sectionOfficeTel) {
        this.sectionOfficeTel = sectionOfficeTel;
    }

    public String getPracticeYear() {
        return practiceYear;
    }

    public void setPracticeYear(String practiceYear) {
        this.practiceYear = practiceYear;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactTel() {
        return emergencyContactTel;
    }

    public void setEmergencyContactTel(String emergencyContactTel) {
        this.emergencyContactTel = emergencyContactTel;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DoctorFile> getDoctorFileApprovalVO() {
        return doctorFileApprovalVO;
    }

    public void setDoctorFileApprovalVO(List<DoctorFile> doctorFileApprovalVO) {
        this.doctorFileApprovalVO = doctorFileApprovalVO;
    }
}
