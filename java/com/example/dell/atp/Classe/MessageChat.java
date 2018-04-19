package com.example.dell.atp.Classe;

import java.util.Date;

public class MessageChat {

    //Attributs
    private String _messageText;
    private String _messageUser;
    private Long _messageTime;

    //Constructeur par défaut
    public MessageChat(){} //nécessaire pour appeler DataSnapshot.getValue(User.class)

    //Constructeur
    public MessageChat(String messageText, String messageUser){
        this.set_messageText(messageText);
        this.set_messageUser(messageUser);
        this.set_messageTime(new Date().getTime());
    }

    //Accesseurs
    public String get_messageText() {return _messageText;}

    public void set_messageText(String _messageText) {this._messageText = _messageText;}

    public String get_messageUser() {return _messageUser;}

    public void set_messageUser(String _messageUser) {this._messageUser = _messageUser;}

    public Long get_messageTime() {return _messageTime;}

    public void set_messageTime(Long _messageTime) {this._messageTime = _messageTime;}
}
