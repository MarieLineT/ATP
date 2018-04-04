package com.example.dell.atp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static com.example.dell.atp.Sign.getUid;


public class Info extends AppCompatActivity {

    //Déclaration DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Déclaration viariables graphique
    private EditText mNom, mPrenom, mAge, mPro;
    private ProgressBar mProgressBar;
    private Button mBtnModif;
    private RadioButton mRdBtnProfessionnel;

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
            startActivity(new Intent(Info.this, Account.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Instanciation DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child(getUid());

        //Référencement des éléments graphiques
        mNom = findViewById(R.id.nom);
        mPrenom = findViewById(R.id.prenom);
        mAge = findViewById(R.id.age);
        mProgressBar = findViewById(R.id.progressBar);
        mBtnModif = findViewById(R.id.btn_modif);
        mPro = findViewById(R.id.pro);
        mRdBtnProfessionnel = findViewById(R.id.professionnel);

        //Action radiobutton professionnel :
        mRdBtnProfessionnel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPro.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });

        //Action du clic sur bouton : enregistrer modifications
        mBtnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressBar.setVisibility(View.VISIBLE);

                //Choix catégorie
                boolean professionnel = mRdBtnProfessionnel.isChecked();
                if(professionnel == true) enregistrerDonneeString("_activite", "professionnel(le)");

                //Enregistrer informations utilisateurs
                //Si je les met tous : fonctionne
                //Si age(int) + 1 des autres string : fonctionne
                //Sinon : activité se ferme, trouver pourquoi !!
                //Solution 1 : tout mettre en String : fonctionne pas
                //Solution 2 : classer infos par infos : fonctionne pas
                //Solution 3 : try/catch + boucle de if
                //Solution 4 : else

                int age = Integer.parseInt(mAge.getText().toString().trim());
                //Convertir int age en String :
                final String nouvelAge = String.valueOf(age);
                final String nouveauPrenom = mPrenom.getText().toString().trim();
                final String nouveauNom = mNom.getText().toString().trim();

                try {
                    if (!nouvelAge.isEmpty()) enregistrerDonneeString("_age", nouvelAge);
                    if (!nouveauPrenom.isEmpty()) enregistrerDonneeString("_prenom", nouveauPrenom);
                    else if(!nouveauNom.isEmpty()) enregistrerDonneeString("_nom", nouveauNom);
                }
                catch (IllegalArgumentException e){
                    throw new RuntimeException(e);
                }

                //Aller à l'activité Account
                Toast.makeText(Info.this, "Modifications enregistrées !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Info.this, Account.class));
            }
        });

    }

    //Fonction permettant de modifier une valeur donnée (String)
    public void enregistrerDonneeString(String donnee, String value) {
        myRef.child(donnee).setValue(value);
    }

    //Fonction permettant de modifier une valeur donnée (int)
    public void enregistrerDonneeInt(String donnee, int valeur) {
        myRef.child(donnee).setValue(valeur);
    }


}
