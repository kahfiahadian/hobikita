package com.example.rpl.hobi_kita_rev;

import java.io.Serializable;

public class FotoModel implements Serializable {

    //Deklarasi variable
    public String key;
    public String image_url;
    public String title;
    public String desc;
    public String name;
    public String email;
    public String loc;

    //konstruktor kosong *diperlukan oleh firebase
    public FotoModel() {
    }

    //konstruktor
    public FotoModel(String key, String image_url, String title, String loc, String name, String email, String desc) {
        this.key = key;
        this.image_url = image_url;
        this.title = title;
        this.desc = desc;
        this.name = name;
        this.email = email;
        this.loc = loc;
    }

    //getter setter semua variable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
