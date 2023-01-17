package com.example.hometech.tech_cartList;

public class TechCartDataModel {

    private String id, price, name, image, username,quantity;

    public TechCartDataModel( String id,String price, String name, String image,String username,String quantity){
        this.id=id;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
        this.image = image;
        this.username=username;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }



    public String getImage(){
        return image;
    }


}
