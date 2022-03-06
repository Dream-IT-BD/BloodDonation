package com.example.blooddonation.dataModel;

import androidx.annotation.NonNull;

public class bloodRequestModel {
    /*
    {
    "id": 1,
    "user_id": 1,
    "patient_name": "Md test",
    "patient_diagnosis": "test diagnosis",
    "blood_group": "A+",
    "hospital_name": "test Hospital",
    "gender": "Male",
    "division": "Rajshahi",
    "district": "Bogura",
    "upazila": "Bogura Sadar",
    "created_at": "2022-02-03T13:22:12.000000Z",
    "updated_at": "2022-02-03T13:22:12.000000Z"
    }
     */

    private int user_id;
    private String patient_name;
    private String patient_diagnosis;
    private String blood_group;
    private String hospital_name;
    private String gender;
    private String division;
    private String district;
    private String upazila;

    public bloodRequestModel(int user_id, String patient_name, String patient_diagnosis, String blood_group, String hospital_name, String gender, String division, String district, String upazila) {
        this.user_id = user_id;
        this.patient_name = patient_name;
        this.patient_diagnosis = patient_diagnosis;
        this.blood_group = blood_group;
        this.hospital_name = hospital_name;
        this.gender = gender;
        this.division = division;
        this.district = district;
        this.upazila = upazila;
    }

    public bloodRequestModel() {

    }

    @Override
    public String toString() {
        return "Patient Name = " + patient_name + '\n' +
                "Patient Diagnosis = " + patient_diagnosis + '\n' +
                "Blood Group = " + blood_group + '\n' +
                "Hospital Name = " + hospital_name + '\n' +
                "Gender = " + gender + '\n' +
                "Division = " + division + '\n' +
                "District = " + district + '\n' +
                "Upazila = " + upazila;
    }


    // Original toString();
    /*
        @Override
    public String toString() {
        return "bloodRequestModel{" +
                "user_id=" + user_id +
                ", patient_name='" + patient_name + '\'' +
                ", patient_diagnosis='" + patient_diagnosis + '\'' +
                ", blood_group='" + blood_group + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", gender='" + gender + '\'' +
                ", division='" + division + '\'' +
                ", district='" + district + '\'' +
                ", upazila='" + upazila + '\'' +
                '}';
    }
     */

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_diagnosis() {
        return patient_diagnosis;
    }

    public void setPatient_diagnosis(String patient_diagnosis) {
        this.patient_diagnosis = patient_diagnosis;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }
}
