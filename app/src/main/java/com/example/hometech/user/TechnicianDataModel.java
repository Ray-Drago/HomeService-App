package com.example.hometech.user;

class TechnicianDataModel {

    private String id, username, email, phone, distance, place,wage,days;

    public TechnicianDataModel(String id, String username, String email,
                               String phone, String distance, String place,String wage,String days){
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.distance = distance;
        this.place = place;
        this.wage=wage;
        this.days=days;
    }

    public String getWage() {
        return wage;
    }

    public String getDays() {
        return days;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDistance() {
        return distance;
    }

    public String getPlace() {
        return place;
    }
}
