package com.example.dell.atp.Activite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.atp.Classe.MessageChat;
import com.example.dell.atp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {

    //Déclaration DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Bouton retour téléphone
    private static long back_pressed;
    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Appuyez de nouveau pour sortir", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
            startActivity(new Intent(Chat.this, Account.class));
        }
    }

    //Déclaration variables graphiques
    EditText mInput;
    ListView mListeMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Instanciation DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Conversations").child("Messages");

        //Référencement des variables graphiques
        mInput = findViewById(R.id.input);
        mListeMessages = findViewById(R.id.list_of_messages);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = mInput.getText().toString().trim();
                String ID = Sign.getUid();

                myRef.push().setValue(new MessageChat(message,ID));

                mInput.setText("");
            }
        });
    }

}
