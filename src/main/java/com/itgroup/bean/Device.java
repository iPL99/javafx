package com.itgroup.bean;

public class Device {
    private int dnum;
    private String model;
    private String brand;
    private String opSystem;
    private String releaseDate;
    private int price;
    private String image01;

    public Device() {
    }

    public Device(int dnum, String model, String brand, String opSystem, String release_year, int price, String image01) {
        this.dnum = dnum;
        this.model = model;
        this.brand = brand;
        this.opSystem = opSystem;
        this.releaseDate = release_year;
        this.price = price;
        this.image01 = image01;
    }

    public Device(String text, String text1, int i, int i1) {
    }

    @Override
    public String toString() {
        String SEPARATOR = "/"; // 항목 구분자

        String temp = "";

        temp += dnum + SEPARATOR;
        temp += model + SEPARATOR;
        temp += brand + SEPARATOR;
        temp += opSystem + SEPARATOR;
        temp += releaseDate + SEPARATOR;
        temp += price + SEPARATOR;
        temp += image01 + SEPARATOR;

        return temp;

    }


    public int getDnum() {
        return dnum;
    }

    public void setDnum(int dnum) {
        this.dnum = dnum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOpSystem() {
        return opSystem;
    }

    public void setOpSystem(String opSystem) {
        this.opSystem = opSystem;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String release_year) {
        this.releaseDate = release_year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage01() {
        return image01;
    }

    public void setImage01(String image01) {
        this.image01 = image01;
    }
}
