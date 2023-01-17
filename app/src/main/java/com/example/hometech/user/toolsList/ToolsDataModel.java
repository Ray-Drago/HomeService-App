package com.example.hometech.user.toolsList;

public class ToolsDataModel {

    private String id, name, price,quantity, phone, image;

    public ToolsDataModel(String id, String name, String price,String quantity,
                               String phone, String image){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.phone = phone;
        this.image = image;
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

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

}
