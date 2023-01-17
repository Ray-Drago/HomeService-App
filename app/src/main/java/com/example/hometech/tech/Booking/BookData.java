package com.example.hometech.tech.Booking;

public class BookData {

    private String id,techname, username, contact,date,time;


    public BookData(String id, String techname, String username, String contact, String date, String time) {
        this.id = id;
        this.techname = techname;
        this.username = username;
        this.contact = contact;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTechname() {
        return techname;
    }

    public String getUsername() {
        return username;
    }

    public String getContact() {
        return contact;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
