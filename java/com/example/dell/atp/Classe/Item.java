package com.example.dell.atp.Classe;

//OBJET MODEL
public class Item {

    //Attributs
    private String _pseudo;
    private String _text;
    private String _profession;

    //Constructeur
    public Item(String pseudo, String profession, String text){
        this.set_pseudo(pseudo);
        this.set_profession(profession);
        this.set_text(text);
    }

    //Accesseurs
    public String get_pseudo() {return _pseudo;}

    public void set_pseudo(String _pseudo) {this._pseudo = _pseudo;}

    public String get_profession() {return _profession;}

    public void set_profession(String _profession) {this._profession = _profession;}

    public String get_text() {return _text;}

    public void set_text(String _text) {this._text = _text;}
}
