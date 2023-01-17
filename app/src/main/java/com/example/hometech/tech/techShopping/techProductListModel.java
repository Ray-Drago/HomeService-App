package com.example.hometech.tech.techShopping;

public class techProductListModel {
    private String id, name, price, image,quantity;

    public techProductListModel(String id, String name, String price, String image, String quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
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

    public String getImage() {
        return image;
    }
}

