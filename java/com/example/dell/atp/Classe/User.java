package com.example.dell.atp.Classe;

public class User {

    //Attribut
    public String _email;
    public String _surnom;
    public String _nom;
    public String _prenom;
    public String _age;
    public String _activite;
    public String _description;
    public String _profession;


    //Constructeur par défaut
    public User(){
        //nécessaire pour appeler DataSnapshot.getValue(User.class)
    }

    //Création utilisateur durant connexion/inscription
    public User(String surnom, String email)
    {
        this._email = email;
        this._surnom = surnom;
    }

    public String get_email() {return _email;}

    public String get_surnom() {return _surnom;}

    public String get_nom(){return _nom;}

    public String get_prenom(){
        return _prenom;
    }

    public String get_age(){
        return _age;
    }

    public String get_activite() {return _activite;}

    public String get_description() {return _description;}

    public String get_profession() {return _profession;}
}

