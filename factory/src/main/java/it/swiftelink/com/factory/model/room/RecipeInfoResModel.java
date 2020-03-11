package it.swiftelink.com.factory.model.room;

import it.swiftelink.com.common.factory.BaseResModel;

public class RecipeInfoResModel extends BaseResModel {

    /**
     * data : {"allergies":"33","pastHistory":"22344","diagnosisSuggest":"2323","needPrescription":1,"familyHistory":"343r3fafa","diagnosis":{"birthday":"2019-08-02","no":"7000092000","patientName":"13379413275","gender":"","patientId":"40289fe46c557d8b016c5666c0ab0012","preliminaryDiagnosis":"感冒","symptomDescription":"哈哈","diagnosisStartTime":1564818639000,"doctorName":"陈医生","doctorId":"2c9280856c23ee7e016c23f6af9f0001","sectionOfficeName":"外科","id":"40289fe46c566f4f016c56758add0000","age":0,"maritalStatus":"N","status":"FINISH"},"id":"1","currentMedicalHistory":"22222","mainAppeal":"1111"}
     */
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * allergies : 33
         * pastHistory : 22344
         * diagnosisSuggest : 2323
         * needPrescription : 1
         * familyHistory : 343r3fafa
         * diagnosis : {"birthday":"2019-08-02","no":"7000092000","patientName":"13379413275","gender":"","patientId":"40289fe46c557d8b016c5666c0ab0012","preliminaryDiagnosis":"感冒","symptomDescription":"哈哈","diagnosisStartTime":1564818639000,"doctorName":"陈医生","doctorId":"2c9280856c23ee7e016c23f6af9f0001","sectionOfficeName":"外科","id":"40289fe46c566f4f016c56758add0000","age":0,"maritalStatus":"N","status":"FINISH"}
         * id : 1
         * currentMedicalHistory : 22222
         * mainAppeal : 1111
         */

        private String allergies;
        private String pastHistory;
        private String diagnosisSuggest;
        private int needPrescription;
        private String familyHistory;
        private DiagnosisBean diagnosis;
        private String weight;
        private String id;
        private String currentMedicalHistory;
        private String mainAppeal;

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getAllergies() {
            return allergies;
        }

        public void setAllergies(String allergies) {
            this.allergies = allergies;
        }

        public String getPastHistory() {
            return pastHistory;
        }

        public void setPastHistory(String pastHistory) {
            this.pastHistory = pastHistory;
        }

        public String getDiagnosisSuggest() {
            return diagnosisSuggest;
        }

        public void setDiagnosisSuggest(String diagnosisSuggest) {
            this.diagnosisSuggest = diagnosisSuggest;
        }

        public int getNeedPrescription() {
            return needPrescription;
        }

        public void setNeedPrescription(int needPrescription) {
            this.needPrescription = needPrescription;
        }

        public String getFamilyHistory() {
            return familyHistory;
        }

        public void setFamilyHistory(String familyHistory) {
            this.familyHistory = familyHistory;
        }

        public DiagnosisBean getDiagnosis() {
            return diagnosis;
        }

        public void setDiagnosis(DiagnosisBean diagnosis) {
            this.diagnosis = diagnosis;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCurrentMedicalHistory() {
            return currentMedicalHistory;
        }

        public void setCurrentMedicalHistory(String currentMedicalHistory) {
            this.currentMedicalHistory = currentMedicalHistory;
        }

        public String getMainAppeal() {
            return mainAppeal;
        }

        public void setMainAppeal(String mainAppeal) {
            this.mainAppeal = mainAppeal;
        }

        public static class DiagnosisBean {
            /**
             * birthday : 2019-08-02
             * no : 7000092000
             * patientName : 13379413275
             * gender :
             * patientId : 40289fe46c557d8b016c5666c0ab0012
             * preliminaryDiagnosis : 感冒
             * symptomDescription : 哈哈
             * diagnosisStartTime : 1564818639000
             * doctorName : 陈医生
             * doctorId : 2c9280856c23ee7e016c23f6af9f0001
             * sectionOfficeName : 外科
             * id : 40289fe46c566f4f016c56758add0000
             * age : 0
             * maritalStatus : N
             * status : FINISH
             */

            private String birthday;
            private String no;
            private String patientName;
            private String gender;
            private String medicalRecord;
            private String patientId;
            private String preliminaryDiagnosis;
            private String symptomDescription;
            private long diagnosisStartTime;
            private String doctorName;
            private String doctorId;
            private String sectionOfficeName;
            private String id;
            private String age;
            private String maritalStatus;
            private String status;

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getPatientName() {
                return patientName;
            }

            public void setPatientName(String patientName) {
                this.patientName = patientName;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getPatientId() {
                return patientId;
            }

            public void setPatientId(String patientId) {
                this.patientId = patientId;
            }

            public String getPreliminaryDiagnosis() {
                return preliminaryDiagnosis;
            }

            public void setPreliminaryDiagnosis(String preliminaryDiagnosis) {
                this.preliminaryDiagnosis = preliminaryDiagnosis;
            }

            public String getSymptomDescription() {
                return symptomDescription;
            }

            public void setSymptomDescription(String symptomDescription) {
                this.symptomDescription = symptomDescription;
            }

            public String getMedicalRecord() {
                return medicalRecord;
            }

            public void setMedicalRecord(String medicalRecord) {
                this.medicalRecord = medicalRecord;
            }

            public long getDiagnosisStartTime() {
                return diagnosisStartTime;
            }

            public void setDiagnosisStartTime(long diagnosisStartTime) {
                this.diagnosisStartTime = diagnosisStartTime;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getSectionOfficeName() {
                return sectionOfficeName;
            }

            public void setSectionOfficeName(String sectionOfficeName) {
                this.sectionOfficeName = sectionOfficeName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getMaritalStatus() {
                return maritalStatus;
            }

            public void setMaritalStatus(String maritalStatus) {
                this.maritalStatus = maritalStatus;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
