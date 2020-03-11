package it.swiftelink.com.factory.model.auth;

import java.io.Serializable;
import java.util.Date;

import it.swiftelink.com.common.factory.BaseResModel;

public class AuthInfoPramModel implements Serializable {


    private String name;
    private String headImg;
    private String description;
    private String gender;
    private Long birthday;
    private int age;
    private String hospital;
    private String location;
    private String sectionOfficeId;
    private String practiceYear;
    private String jobTitle;
    private String sectionOfficeTel;
    private String emergencyContact;
    private String emergencyContactTel;
    private String birthplace;
    private String contactAddr;
    private String language;
    private String nature;

    public AuthInfoPramModel() {
    }

    public AuthInfoPramModel(String name, String headImg, String description, String gender, Long birthday, int age, String hospital, String location, String sectionOfficeId, String practiceYear, String jobTitle, String sectionOfficeTel, String emergencyContact, String emergencyContactTel, String birthplace, String contactAddr, String language, String nature) {
        this.name = name;
        this.headImg = headImg;
        this.description = description;
        this.gender = gender;
        this.birthday = birthday;
        this.age = age;
        this.hospital = hospital;
        this.location = location;
        this.sectionOfficeId = sectionOfficeId;
        this.practiceYear = practiceYear;
        this.jobTitle = jobTitle;
        this.sectionOfficeTel = sectionOfficeTel;
        this.emergencyContact = emergencyContact;
        this.emergencyContactTel = emergencyContactTel;
        this.birthplace = birthplace;
        this.contactAddr = contactAddr;
        this.language = language;
        this.nature = nature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSectionOfficeId() {
        return sectionOfficeId;
    }

    public void setSectionOfficeId(String sectionOfficeId) {
        this.sectionOfficeId = sectionOfficeId;
    }

    public String getPracticeYear() {
        return practiceYear;
    }

    public void setPracticeYear(String practiceYear) {
        this.practiceYear = practiceYear;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSectionOfficeTel() {
        return sectionOfficeTel;
    }

    public void setSectionOfficeTel(String sectionOfficeTel) {
        this.sectionOfficeTel = sectionOfficeTel;
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

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
