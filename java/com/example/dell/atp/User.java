package com.example.dell.atp;

/**
 * Created by DELL on 04/03/2018.
 */

public class User {

    //Attribut
    public String _userId;
    public String _email;
    public String _surnom;

    //Constructeur par défaut
    public User(){
        //nécessaire pour appeler DataSnapshot.getValue(User.class)
    }

    public User(String surnom, String email)
    {
        this._email = email;
        this._surnom = surnom;
    }

}

