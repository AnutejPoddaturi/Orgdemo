package com.anutejpoddaturi.orgdemo;

/**
 * Created by AnutejPoddaturi on 22/10/17.
 */

public class Blog {

    private String Location;
    private String desc;
    private String image;

    public Blog(){

    }

    public Blog(String location, String desc, String image) {
        Location = location;
        this.desc = desc;
        this.image = image;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
