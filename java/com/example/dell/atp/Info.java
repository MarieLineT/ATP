package com.example.dell.atp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.atp.Sign.getUid;
import static java.util.Objects.isNull;

public class Info extends AppCompatActivity {

    //Déclaration viariables graphique
    private EditText mNom, mPrenom, mAge, mPro;
    private ProgressBar mProgressBar;
    private Button mBtnModif;
    private Spinner mActivite;

    //Déclaration DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    private static long back_pressed;
    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Appuyez de nouveau pour sortir", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
            startActivity(new Intent(Info.this, Account.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Instanciation BD
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child(getUid());

        //Référencement des éléments graphiques
        mNom = findViewById(R.id.nom);
        mPrenom = findViewById(R.id.prenom);
        mAge = findViewById(R.id.age);
        mProgressBar = findViewById(R.id.progressBar);
        mBtnModif = findViewById(R.id.btn_modif);
        mActivite = findViewById(R.id.activite);
        mPro = findViewById(R.id.pro);

        //Creation liste du Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activite_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActivite.setAdapter(adapter);

        mActivite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String nouvelleActivite = (String)mActivite.getSelectedItem();
                enregistrerDonneeString("_activite", nouvelleActivite);
                if (nouvelleActivite != "Professionnel(le)") mPro.setVisibility(mPro.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBtnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressBar.setVisibility(View.VISIBLE);

                //Enregistrer informations utilisateurs
                String nouveauNom = mNom.getText().toString().trim();
                String nouveauPrenom = mPrenom.getText().toString().trim();
                int nouvelAge = Integer.parseInt(mAge.getText().toString().trim());

                //Ajouter les valeurs à la BD
                //Certains champs peuvent ne pas être remplis : comment le prendre en compte ?
                //Peut pas créer une seule fonction : cas par cas
                if (!nouveauNom.isEmpty()) {
                    enregistrerDonneeString("_nom", nouveauNom);
                }
                if (!nouveauPrenom.isEmpty()) {
                    enregistrerDonneeString("_prenom", nouveauPrenom);
                }
                if(nouvelAge != 0){
                    enregistrerDonneeInt("_age", nouvelAge);
                }

                //Aller à l'activité Account
                Toast.makeText(Info.this, "Modifications enregistrées !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Info.this, Account.class));
                finish();
            }
        });

    }

    //Fonction permettant de modifier une valeur donnée (String)
    public void enregistrerDonneeString(String donnee, String valeur) {
        myRef.child(donnee).setValue(valeur);
    }

    //Fonction permettant de modifier une valeur donnée (int)
    public void enregistrerDonneeInt(String donnee, int valeur) {
        myRef.child(donnee).setValue(valeur);
    }


}
