package com.example.sharedspace;

public class ModelUser {

    //use name from firebase
    String name, search, phone, image;

    public ModelUser() {
    }

    public ModelUser(String name, String search, String phone, String image) {
        this.name = name;
        this.search = search;
        this.phone = phone;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
