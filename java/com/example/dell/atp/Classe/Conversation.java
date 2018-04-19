package com.example.dell.atp.Classe;

import com.example.dell.atp.Classe.MessageChat;
import com.example.dell.atp.Classe.User;

import java.util.List;

public class Conversation {

    //Attribut
    private User _user1ID;
    private User _user2ID;
    //private String _conversationID;
    private List<MessageChat> _listeMessages;

    //Constructeur par défaut
    public Conversation(){}

    public Conversation(User user1ID, User user2ID, List<MessageChat> listeMessages){
        this.set_user1ID(user1ID);
        this.set_user2ID(user2ID);
        //this.set_conversationID(conversationID);
        this.set_listeMessages(listeMessages);
    }

    public Conversation(User user1ID, User user2ID){
        this.set_user1ID(user1ID);
        this.set_user2ID(user2ID);
        //this.set_conversationID(conversationID);
    }

    /*Accesseurs
    public String get_conversationID() {return _conversationID;}

    public void set_conversationID(String _conversationID) {this._conversationID = _conversationID;}*/

    public List<MessageChat> get_listeMessages() {return _listeMessages;}

    public void set_listeMessages(List<MessageChat> _listeMessages) {this._listeMessages = _listeMessages;}

    public User get_user1ID() {return _user1ID;}

    public void set_user1ID(User _user1ID) {this._user1ID = _user1ID;}

    public User get_user2ID() {return _user2ID;}

    public void set_user2ID(User _user2ID) {this._user2ID = _user2ID;}
}
