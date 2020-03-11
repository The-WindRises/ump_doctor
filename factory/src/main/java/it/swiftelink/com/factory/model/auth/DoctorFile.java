package it.swiftelink.com.factory.model.auth;

import java.io.Serializable;
import java.util.Date;


public class DoctorFile implements Serializable {
    private String id;

    private String doctorId;

    private String type;

    private String name;

    private String no;

    private String fileId;

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public DoctorFile(String id, String doctorId, String type, String name, String no, String fileId) {
        this.id = id;
        this.doctorId = doctorId;
        this.type = type;
        this.name = name;
        this.no = no;
        this.fileId = fileId;
    }

    public DoctorFile() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}