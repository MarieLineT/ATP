package com.example.dell.atp;


import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.atp.Sign.getUid;
import static com.example.dell.atp.Sign.getUser;

public class Account extends AppCompatActivity {

    //Déclaration variables graphique
    private Button mBtnRecherche, mBtnTchat;
    private TextView mtextViewPrenom;

    //Déclarer utilisateur pour datasnapshot JE SAIS PAS COMMENT car n'EXISTE PAS POUR LE MOMENT
    User currentUser;

    //Déclaration DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Instanciation BD
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child(getUid());

        //Instanciation user


        //Référencements éléments graphiques
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBtnRecherche = findViewById(R.id.btn_recherche);
        mBtnTchat = findViewById(R.id.btn_tchat);
        mtextViewPrenom = findViewById(R.id.textViewPrenom);

        //Modifier Surnom
        defSurnom();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    //Fonction Logout
    public void logOut() {
        new AlertDialog.Builder(this)
                .setTitle("Quitter la session ?")
                .setMessage("Êtes-vous sûr(e) de vouloir vous déconnecter ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Account.this, Sign.class));
                        finish();
                    }
                }).create().show();
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rechercher:
                //Comportement bouton rechercher
                return true;
            case R.id.action_logout:
                //Comportement action logout
                logOut();
                return true;
            case R.id.action_info:
                //Comportement bouton "mes infos"
                startActivity(new Intent(Account.this, Info.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Définir le surnom d'affichage
    public void defSurnom(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                String prenom = dataSnapshot.child("_prenom").getValue(String.class);
                String surnom = dataSnapshot.child("_surnom").getValue(String.class);
                if(prenom != null) mtextViewPrenom.setText(prenom);
                else if(prenom == null) mtextViewPrenom.setText(surnom);
                else if (surnom == null) mtextViewPrenom.setText("toi :) ");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
