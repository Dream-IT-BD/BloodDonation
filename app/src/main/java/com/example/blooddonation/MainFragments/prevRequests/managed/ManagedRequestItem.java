package com.example.blooddonation.MainFragments.prevRequests.managed;

public class ManagedRequestItem {
    private final String id, patient_name, hospital_name, blood_group ,date;

    public ManagedRequestItem(String id, String patient_name, String hospital_name, String blood_group, String date) {
        this.id = id;
        this.patient_name = patient_name;
        this.hospital_name = hospital_name;
        this.blood_group = blood_group;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public String getDate() {
        return date;
    }


}
