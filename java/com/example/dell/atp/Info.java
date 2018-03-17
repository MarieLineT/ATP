package com.example.dell.atp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Info extends AppCompatActivity {

    //Déclaration viariables graphique
    private EditText mNom, mPrenom, mAge, mGenre;
    private ProgressBar mProgressBar;
    private Button mBtnModif;

    //Déclaration DB
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Instanciation DB
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Référencement des éléments graphiques
        mNom =  findViewById(R.id.nom);
        mPrenom =  findViewById(R.id.prenom);
        mAge =  findViewById(R.id.age);
        mGenre =  findViewById(R.id.genre);
        mProgressBar =  findViewById(R.id.progressBar);
        mBtnModif = findViewById(R.id.btn_modif);

        mBtnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enregistrer informations utilisateurs
            }
        });




    }
}
