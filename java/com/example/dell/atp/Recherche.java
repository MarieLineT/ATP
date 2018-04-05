package com.example.dell.atp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.atp.Sign.getUid;

public class Recherche extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        //Instanciation DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        //Référencement des éléments graphiques
        mProfession = findViewById(R.id.profession);
        mNom = findViewById(R.id.nom);
        mMotsCle = findViewById(R.id.motscle);
    }

    public void requete(){
        Query query = (Query) myRef.orderByChild("_nom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
