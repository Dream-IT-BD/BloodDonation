package com.example.blooddonation.MainFragments.prevRequests.details_interested;

public class InterestedDonorItem {
    private final String name;
    private final String blood_request_id;
    private final String user_id;

    public InterestedDonorItem(String name, String blood_request_id, String user_id) {
        this.name = name;
        this.blood_request_id = blood_request_id;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getBlood_request_id() {
        return blood_request_id;
    }

    public String getUser_id() {
        return user_id;
    }
}
