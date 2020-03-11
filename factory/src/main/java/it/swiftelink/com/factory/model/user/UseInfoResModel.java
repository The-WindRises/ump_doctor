package it.swiftelink.com.factory.model.user;

import java.io.Serializable;
import java.util.List;

import it.swiftelink.com.common.factory.BaseResModel;

public class UseInfoResModel extends BaseResModel {

    /**
     * data : {"id":"2c9280856c23ee7e016c23f6af9f0001","userId":32,"name":"塮","headImg":"http://cms.qingbo.co:8091/web/upload/9e6e1de47cf64bdc8bfc1599e2eb002e/touxiang.jpg","hospital":"火月竹竹土","birthday":1561392000000,"gender":"","age":23,"sectionOfficeId":"66ebc007-9897-11e9-bb93-0242ac150003","sectionOfficeName":"肝胆外科","sectionOfficeTel":"13154879564","jobTitle":"土土竹竹竹","level":5,"countryCode":null,"tel":"+86 13379413275","emergencyContact":"23455677","emergencyContactTel":"13128851436","contactAddr":"火土竹竹","birthplace":"广东","onlineTime":180,"todayOnlineTime":30,"scheduleTime":5,"lateEarlyTime":10,"amount":2300,"doctorStatus":"ToAssess","onlineStatus":"Online","accountStatus":"WriteOff","allowOrder":"Yes","nature":"FullTime","practiceYear":5,"location":"广东","language":"Mandarin","description":"蒐","createdBy":"32","creationDate":1565081425000,"lastUpdatedBy":"33","lastUpdatedDate":1565229599000,"doctorFiles":[{"id":"2c9380826c6ee3aa016c6ef3bd9e0002","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"IdCard_Front","name":null,"no":"456777","fileId":"2c9680826b9c3a7f016c6ef1c7730599","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/e28def80916846b5b6946880f6e82133/magazine-unlock-01-2.3.1512-B67BABC107F34DBBBAAC5B5DFE28E36D.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3bdbb0003","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"IdCard_Reverse","name":null,"no":"456777","fileId":"2c9680826b9c3a7f016c6ef1ce06059a","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/19393a235e124b18b88c779255c90eb0/magazine-unlock-04-2.3.1512-8B30003B020B4D43B66B5780AEAFD9F1.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3bdd40004","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Practice_Front","name":null,"no":"45788","fileId":"2c9680826b9c3a7f016c6ef27ac6059b","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/3fb8930f7a1d4d3388fbf80af556b42b/magazine-unlock-01-2.3.1512-B67BABC107F34DBBBAAC5B5DFE28E36D.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3bdee0005","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Practice_Reverse","name":null,"no":"45788","fileId":"2c9680826b9c3a7f016c6ef27ffa059c","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/ddebd92a3bd24b0187468b1eff1f8cdd/magazine-unlock-04-2.3.1512-8B30003B020B4D43B66B5780AEAFD9F1.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be090006","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Job_Certificate_Front","name":null,"no":"245667","fileId":"2c9680826b9c3a7f016c6ef2938b059d","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/ee843b9d009b461ab4f3ac09aeeb2f17/magazine-unlock-01-2.3.1512-8081E421D6E63C685381BFAECB00C78A.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be2d0007","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Job_Certificate_Reverse","name":null,"no":"245667","fileId":"2c9680826b9c3a7f016c6ef2938b059d","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/ee843b9d009b461ab4f3ac09aeeb2f17/magazine-unlock-01-2.3.1512-8081E421D6E63C685381BFAECB00C78A.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be470008","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Qualification_Front","name":null,"no":"456788","fileId":"2c9680826b9c3a7f016c6ef385bf059f","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/c26ad4183487489fa1db7c9a695a31af/magazine-unlock-05-2.3.1512-88AE78EA76804C3593129FE7F81B03F3.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be610009","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Qualification_Reverse","name":null,"no":"456788","fileId":"2c9680826b9c3a7f016c6ef38c7c05a0","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/0e8be68cf2bc43b1980ede6a4a4af246/magazine-unlock-05-2.3.1512-9D597D0E2065C52BF11F9C1805C9F214.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be7b000a","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Other_Certificate_One","name":null,"no":"5678","fileId":"2c9680826b9c3a7f016c6ef3a31f05a1","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/f6dbbb8b640e4c6fbff1cfb207440eb5/magazine-unlock-01-2.3.1512-AE4D85AD8F4D261191CD93167D0BE0B3.jpg","createdBy":null,"creationDate":1565229564000,"lastUpdatedBy":null,"lastUpdatedDate":1565229564000},{"id":"2c9380826c6ee3aa016c6ef3be94000b","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Other_Certificate_Two","name":null,"no":"5678","fileId":"2c9680826b9c3a7f016c6ef3a9e805a2","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/eca169ac259548eba7baba3af5209a50/magazine-unlock-05-2.3.1512-9D597D0E2065C52BF11F9C1805C9F214.jpg","createdBy":null,"creationDate":1565229564000,"lastUpdatedBy":null,"lastUpdatedDate":1565229564000}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 2c9280856c23ee7e016c23f6af9f0001
         * userId : 32
         * name : 塮
         * headImg : http://cms.qingbo.co:8091/web/upload/9e6e1de47cf64bdc8bfc1599e2eb002e/touxiang.jpg
         * hospital : 火月竹竹土
         * birthday : 1561392000000
         * gender :
         * age : 23
         * sectionOfficeId : 66ebc007-9897-11e9-bb93-0242ac150003
         * sectionOfficeName : 肝胆外科
         * sectionOfficeTel : 13154879564
         * jobTitle : 土土竹竹竹
         * level : 5
         * countryCode : null
         * tel : +86 13379413275
         * emergencyContact : 23455677
         * emergencyContactTel : 13128851436
         * contactAddr : 火土竹竹
         * birthplace : 广东
         * onlineTime : 180
         * todayOnlineTime : 30
         * scheduleTime : 5
         * lateEarlyTime : 10
         * amount : 2300.0
         * amount : 2300.0
         * doctorStatus : ToAssess
         * onlineStatus : Online
         * accountStatus : WriteOff
         * allowOrder : Yes
         * nature : FullTime
         * practiceYear : 5
         * location : 广东
         * language : Mandarin
         * description : 蒐
         * createdBy : 32
         * creationDate : 1565081425000
         * lastUpdatedBy : 33
         * lastUpdatedDate : 1565229599000
         * doctorFiles : [{"id":"2c9380826c6ee3aa016c6ef3bd9e0002","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"IdCard_Front","name":null,"no":"456777","fileId":"2c9680826b9c3a7f016c6ef1c7730599","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/e28def80916846b5b6946880f6e82133/magazine-unlock-01-2.3.1512-B67BABC107F34DBBBAAC5B5DFE28E36D.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3bdbb0003","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"IdCard_Reverse","name":null,"no":"456777","fileId":"2c9680826b9c3a7f016c6ef1ce06059a","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/19393a235e124b18b88c779255c90eb0/magazine-unlock-04-2.3.1512-8B30003B020B4D43B66B5780AEAFD9F1.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3bdd40004","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Practice_Front","name":null,"no":"45788","fileId":"2c9680826b9c3a7f016c6ef27ac6059b","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/3fb8930f7a1d4d3388fbf80af556b42b/magazine-unlock-01-2.3.1512-B67BABC107F34DBBBAAC5B5DFE28E36D.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3bdee0005","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Practice_Reverse","name":null,"no":"45788","fileId":"2c9680826b9c3a7f016c6ef27ffa059c","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/ddebd92a3bd24b0187468b1eff1f8cdd/magazine-unlock-04-2.3.1512-8B30003B020B4D43B66B5780AEAFD9F1.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be090006","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Job_Certificate_Front","name":null,"no":"245667","fileId":"2c9680826b9c3a7f016c6ef2938b059d","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/ee843b9d009b461ab4f3ac09aeeb2f17/magazine-unlock-01-2.3.1512-8081E421D6E63C685381BFAECB00C78A.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be2d0007","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Job_Certificate_Reverse","name":null,"no":"245667","fileId":"2c9680826b9c3a7f016c6ef2938b059d","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/ee843b9d009b461ab4f3ac09aeeb2f17/magazine-unlock-01-2.3.1512-8081E421D6E63C685381BFAECB00C78A.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be470008","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Qualification_Front","name":null,"no":"456788","fileId":"2c9680826b9c3a7f016c6ef385bf059f","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/c26ad4183487489fa1db7c9a695a31af/magazine-unlock-05-2.3.1512-88AE78EA76804C3593129FE7F81B03F3.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be610009","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Qualification_Reverse","name":null,"no":"456788","fileId":"2c9680826b9c3a7f016c6ef38c7c05a0","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/0e8be68cf2bc43b1980ede6a4a4af246/magazine-unlock-05-2.3.1512-9D597D0E2065C52BF11F9C1805C9F214.jpg","createdBy":null,"creationDate":1565229563000,"lastUpdatedBy":null,"lastUpdatedDate":1565229563000},{"id":"2c9380826c6ee3aa016c6ef3be7b000a","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Other_Certificate_One","name":null,"no":"5678","fileId":"2c9680826b9c3a7f016c6ef3a31f05a1","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/f6dbbb8b640e4c6fbff1cfb207440eb5/magazine-unlock-01-2.3.1512-AE4D85AD8F4D261191CD93167D0BE0B3.jpg","createdBy":null,"creationDate":1565229564000,"lastUpdatedBy":null,"lastUpdatedDate":1565229564000},{"id":"2c9380826c6ee3aa016c6ef3be94000b","doctorId":"2c9280856c23ee7e016c23f6af9f0001","type":"Other_Certificate_Two","name":null,"no":"5678","fileId":"2c9680826b9c3a7f016c6ef3a9e805a2","filePath":"http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/eca169ac259548eba7baba3af5209a50/magazine-unlock-05-2.3.1512-9D597D0E2065C52BF11F9C1805C9F214.jpg","createdBy":null,"creationDate":1565229564000,"lastUpdatedBy":null,"lastUpdatedDate":1565229564000}]
         */

        private String id;
        private int userId;
        private String name;
        private String headImg;
        private String hospital;
        private String birthday;
        private String gender;
        private String age;
        private String sectionOfficeId;
        private String sectionOfficeName;
        private String sectionOfficeTel;
        private String jobTitle;
        private String level;
        private Object countryCode;
        private String tel;
        private String emergencyContact;
        private String emergencyContactTel;
        private String contactAddr;
        private String birthplace;
        private int onlineTime;
        private int todayOnlineTime;
        private int scheduleTime;
        private int lateEarlyTime;
        private double amount;
        private String doctorStatus;
        private String onlineStatus;
        private String accountStatus;
        private String allowOrder;
        private String nature;
        private String practiceYear;
        private String location;
        private String language;
        private String description;
        private String createdBy;
        private long creationDate;
        private String lastUpdatedBy;
        private long lastUpdatedDate;
        private List<DoctorFilesBean> doctorFiles;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSectionOfficeId() {
            return sectionOfficeId;
        }

        public void setSectionOfficeId(String sectionOfficeId) {
            this.sectionOfficeId = sectionOfficeId;
        }

        public String getSectionOfficeName() {
            return sectionOfficeName;
        }

        public void setSectionOfficeName(String sectionOfficeName) {
            this.sectionOfficeName = sectionOfficeName;
        }

        public String getSectionOfficeTel() {
            return sectionOfficeTel;
        }

        public void setSectionOfficeTel(String sectionOfficeTel) {
            this.sectionOfficeTel = sectionOfficeTel;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public Object getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(Object countryCode) {
            this.countryCode = countryCode;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
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

        public String getBirthplace() {
            return birthplace;
        }

        public void setBirthplace(String birthplace) {
            this.birthplace = birthplace;
        }

        public int getOnlineTime() {
            return onlineTime;
        }

        public void setOnlineTime(int onlineTime) {
            this.onlineTime = onlineTime;
        }

        public int getTodayOnlineTime() {
            return todayOnlineTime;
        }

        public void setTodayOnlineTime(int todayOnlineTime) {
            this.todayOnlineTime = todayOnlineTime;
        }

        public int getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(int scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

        public int getLateEarlyTime() {
            return lateEarlyTime;
        }

        public void setLateEarlyTime(int lateEarlyTime) {
            this.lateEarlyTime = lateEarlyTime;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getDoctorStatus() {
            return doctorStatus;
        }

        public void setDoctorStatus(String doctorStatus) {
            this.doctorStatus = doctorStatus;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getAccountStatus() {
            return accountStatus;
        }

        public void setAccountStatus(String accountStatus) {
            this.accountStatus = accountStatus;
        }

        public String getAllowOrder() {
            return allowOrder;
        }

        public void setAllowOrder(String allowOrder) {
            this.allowOrder = allowOrder;
        }

        public String getNature() {
            return nature;
        }

        public void setNature(String nature) {
            this.nature = nature;
        }

        public String getPracticeYear() {
            return practiceYear;
        }

        public void setPracticeYear(String practiceYear) {
            this.practiceYear = practiceYear;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public long getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(long creationDate) {
            this.creationDate = creationDate;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public long getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        public void setLastUpdatedDate(long lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
        }

        public List<DoctorFilesBean> getDoctorFiles() {
            return doctorFiles;
        }

        public void setDoctorFiles(List<DoctorFilesBean> doctorFiles) {
            this.doctorFiles = doctorFiles;
        }

        public static class DoctorFilesBean implements Serializable {
            @Override
            public String toString() {
                return "DoctorFilesBean{" +
                        "id='" + id + '\'' +
                        ", doctorId='" + doctorId + '\'' +
                        ", type='" + type + '\'' +
                        ", name=" + name +
                        ", no='" + no + '\'' +
                        ", fileId='" + fileId + '\'' +
                        ", filePath='" + filePath + '\'' +
                        ", createdBy=" + createdBy +
                        ", creationDate=" + creationDate +
                        ", lastUpdatedBy=" + lastUpdatedBy +
                        ", lastUpdatedDate=" + lastUpdatedDate +
                        '}';
            }

            /**
             * id : 2c9380826c6ee3aa016c6ef3bd9e0002
             * doctorId : 2c9280856c23ee7e016c23f6af9f0001
             * type : IdCard_Front
             * name : null
             * no : 456777
             * fileId : 2c9680826b9c3a7f016c6ef1c7730599
             * filePath : http://cms.qingbo.co:8091/home/GitStore/cloud-cms-script/V1.0.0/web/upload/e28def80916846b5b6946880f6e82133/magazine-unlock-01-2.3.1512-B67BABC107F34DBBBAAC5B5DFE28E36D.jpg
             * createdBy : null
             * creationDate : 1565229563000
             * lastUpdatedBy : null
             * lastUpdatedDate : 1565229563000
             */

            private String id;
            private String doctorId;
            private String type;
            private Object name;
            private String no;
            private String fileId;
            private String filePath;
            private Object createdBy;
            private long creationDate;
            private Object lastUpdatedBy;
            private long lastUpdatedDate;

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

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
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

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public Object getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(Object createdBy) {
                this.createdBy = createdBy;
            }

            public long getCreationDate() {
                return creationDate;
            }

            public void setCreationDate(long creationDate) {
                this.creationDate = creationDate;
            }

            public Object getLastUpdatedBy() {
                return lastUpdatedBy;
            }

            public void setLastUpdatedBy(Object lastUpdatedBy) {
                this.lastUpdatedBy = lastUpdatedBy;
            }

            public long getLastUpdatedDate() {
                return lastUpdatedDate;
            }

            public void setLastUpdatedDate(long lastUpdatedDate) {
                this.lastUpdatedDate = lastUpdatedDate;
            }
        }
    }
}
