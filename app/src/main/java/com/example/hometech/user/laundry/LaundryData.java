package com.example.hometech.user.laundry;

class LaundryData {

    private String  username, email, phone, place,wage,days,category,subcategory;

    public LaundryData(String username, String email, String phone, String place, String wage, String days, String category, String subcategory){

        this.username = username;
        this.email = email;
        this.phone = phone;
        this.place = place;
        this.wage=wage;
        this.days=days;
        this.category=category;
        this.subcategory=subcategory;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getWage() {
        return wage;
    }

    public String getDays() {
        return days;
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

    public String getPlace() {
        return place;
    }
}
