package com.example.dell.atp.Activite;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.atp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    //Déclaration variables graphique
    private Button mBtnTchat;
    private TextView mtextViewPrenom, mtextViewNom, mtextViewAge, mtextViewActiviteRenseigne, mTextViewActivite,
            mTextViewDescriptionRenseigne;


    //Déclaration DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Instanciation DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        //Référencements éléments graphiques
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBtnTchat = findViewById(R.id.btn_tchat);
        mtextViewPrenom = findViewById(R.id.textViewPrenomRenseigne);
        mtextViewNom = findViewById(R.id.textViewNomRenseigne);
        mtextViewAge = findViewById(R.id.textViewAgeRenseigne);
        mtextViewActiviteRenseigne = findViewById(R.id.textViewActiviteRenseigne);
        mTextViewActivite = findViewById(R.id.textViewActivite);
        mTextViewDescriptionRenseigne = findViewById(R.id.textViewDescriptionRenseigne);


        //Afficher infos utilisateur
        defSurnom();
        affichageInfo("_nom", mtextViewNom);
        affichageInfo("_age", mtextViewAge);
        defProfession();
        affichageInfo("_description", mTextViewDescriptionRenseigne);

        //ACCEDER TCHAT
        mBtnTchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start activité Tchat
            }
        });
    }

    //Création Menu ActionBar
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

    //Gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rechercher:
                //Comportement bouton rechercher
                startActivity(new Intent(Account.this, Recherche.class));
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

    //Définir le renseignement d'affichage
    public void affichageInfo(final String info, final TextView renseignement){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Récupérer la valeur de la donnée "info"
                String donnee = dataSnapshot.child(Sign.getUid()).child(info).getValue(String.class);

                //Si cette donnée n'est pas nulle,
                //Sa valeur est attribuée au TextView
                if(donnee != null) renseignement.setText(donnee);

                //Sinon on affiche "Non renseigné"
                else renseignement.setText("Non renseigné");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "La lecture a échoué : "
                        +databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Définit le surnom de l'utilisateur qui sera affiché
    public void defSurnom(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                String prenom = dataSnapshot.child(Sign.getUid()).child("_prenom").getValue(String.class);
                String surnom = dataSnapshot.child(Sign.getUid()).child("_surnom").getValue(String.class);
                if(prenom != null) mtextViewPrenom.setText(prenom);
                else if(prenom == null) mtextViewPrenom.setText(surnom);
                else if (surnom == null) mtextViewPrenom.setText("toi :) ");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "La lecture a échoué : "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //A retoucher : ne fonctionne pas pourtant récupère les bonnes variables
    public void defProfession(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Récupération des données de la base
                try {
                    final String activite = dataSnapshot.child(Sign.getUid()).child("_activite").getValue(String.class);
                    final String profession = dataSnapshot.child(Sign.getUid()).child("_profession").getValue(String.class);

                //Si activité = "professionnel(le)" et profession existe :
                if(!activite.isEmpty() && activite.equals("professionnel(le)") && profession != null)
                    mtextViewActiviteRenseigne.setText(profession);

                //Sinon :
                else mtextViewActiviteRenseigne.setText("particulier");

                }catch(Exception e){}
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "La lecture a échoué : "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
