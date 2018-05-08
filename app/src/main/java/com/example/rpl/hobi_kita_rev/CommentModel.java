package com.example.rpl.hobi_kita_rev;

import java.io.Serializable;

public class CommentModel implements Serializable {

    //Deklarasi variable
    public String name;
    public String email;
    public String comment;

    //konstruktor kosong *diperlukan oleh firebase
    public CommentModel() {
    }

    //konstruktor
    public CommentModel(String name, String email, String comment) {
        this.name = name;
        this.email = email;
        this.comment = comment;
    }

    //getter setter semua variable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
