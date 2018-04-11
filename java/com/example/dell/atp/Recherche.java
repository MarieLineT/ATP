package com.example.dell.atp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.dell.atp.Sign.getUid;

public class Recherche extends AppCompatActivity {

    private static final String TAG = "RechercheActivity";

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
            startActivity(new Intent(Recherche.this, Account.class));
        }
    }

    //Déclaration des variables graphiques
    private EditText mProfession, mNom, mMotsCle;
    private Button mRechercher;
    private ListView mListView;
    private ProgressBar mProgress;
    private List<Item> items;

    final ArrayList<String> nomsUsers = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        //Instanciation DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");

        //Référencement des éléments graphiques
        mProfession = findViewById(R.id.profession);
        mNom = findViewById(R.id.nom);
        mMotsCle = findViewById(R.id.motscle);
        mListView = findViewById(R.id.listView);
        mRechercher = findViewById(R.id.btn_recherche);
        mProgress = findViewById(R.id.progressBar);

        items = new ArrayList<Item>();
        ItemAdapter adapter = new ItemAdapter(Recherche.this, items);
        mListView.setAdapter(adapter);

        /*TEST
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomsUsers);
        mListView.setAdapter(arrayAdapter);

        //TEST
        /*List<Item> items = requete();
        ItemAdapter adapter = new ItemAdapter(Recherche.this, items);
        mListView.setAdapter(adapter);*/


        mRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ProgressBar devient visible
                mProgress.setVisibility(View.VISIBLE);

                //Récupération donnée utlisateur
                final String professionRecherche = mProfession.getText().toString().trim();

                //Requête de récupération données DB
                myRef.orderByChild("_prenom").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        //FAIRE REQUETE : Profession = donnée renseignée !!!
                        User users = dataSnapshot.getValue(User.class);
                        String prenom = users.get_prenom();
                        String surnom = users.get_surnom();
                        String profession = users.get_profession();
                        if (prenom == null) items.add(new Item(surnom, profession));
                        else items.add(new Item(prenom, profession));

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Echec de la recherche", databaseError.toException());
                        Toast.makeText(Recherche.this, "Echec de la recherche",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
