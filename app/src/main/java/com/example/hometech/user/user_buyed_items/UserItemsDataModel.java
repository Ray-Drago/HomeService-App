package com.example.hometech.user.user_buyed_items;

public class UserItemsDataModel {

    private String id,name, price,quantity,  image, username,status,bdate,btime,location,datestatus;

    public UserItemsDataModel(String id, String name, String price, String quantity,
      String image, String username,String status,String bdate,String btime,String location,String datestatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.username = username;
        this.status = status;
        this.bdate = bdate;
        this.btime = btime;
        this.location = location;
        this.datestatus = datestatus;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public String getBdate() {
        return bdate;
    }

    public String getBtime() {
        return btime;
    }

    public String getLocation() {
        return location;
    }

    public String getDatestatus() {
        return datestatus;
    }
}
