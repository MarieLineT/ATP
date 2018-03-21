package com.example.dell.atp;


import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    //Déclaration variables graphique
    private Button mBtnRecherche, mBtnTchat, mBtnInfo;

    //Déclaration DB
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Instanciation BD
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Référencements éléments graphiques
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBtnRecherche = findViewById(R.id.btn_recherche);
        mBtnTchat = findViewById(R.id.btn_tchat);
        mBtnInfo = findViewById(R.id.btn_info);

        //ACCEDER PAGE INFO
        mBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start activité info
                startActivity(new Intent(Account.this, Info.class));
                finish();
            }
        });

        //ACCEDER TCHAT
        mBtnTchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start activité Tchat
            }
        });

        //ACCEDER RECHERCHE
        mBtnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start activité recherche
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        /*Lire depuis la BD
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Méthode appelée une fois avec la valeur initiale
                //puis à chaque fois que que la donnée à cet emplacement est actualisée
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is : "+value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Echec de lecture de la donnée
                Log.w(TAG, "Echec de lecture de la donnée.", databaseError.toException());

            }
        });*/

    }

}