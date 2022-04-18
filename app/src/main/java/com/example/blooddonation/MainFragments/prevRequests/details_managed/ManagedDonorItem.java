package com.example.blooddonation.MainFragments.prevRequests.details_managed;

public class ManagedDonorItem {

    private final String name;
    private final String contactNumber;

    public ManagedDonorItem(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
