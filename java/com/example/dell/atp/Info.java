package com.example.dell.atp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.atp.Sign.getUid;

public class Info extends AppCompatActivity {

    //Déclaration viariables graphique
    private EditText mNom, mPrenom, mAge, mPro;
    private ProgressBar mProgressBar;
    private Button mBtnModif;
    private Spinner mActivite;

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
        mProgressBar =  findViewById(R.id.progressBar);
        mBtnModif = findViewById(R.id.btn_modif);
        mActivite = findViewById(R.id.activite);
        mPro = findViewById(R.id.pro);

        //Creation liste du Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activite_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActivite.setAdapter(adapter);
        //Afficher renseignement profession
        //Pas sûre de pouvoir le mettre là
        if(mActivite.getSelectedItem() == "Professionnel") mPro.setVisibility(View.VISIBLE);


        mBtnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enregistrer informations utilisateurs
                String nouveauNom = mNom.getText().toString().trim();
                String nouveauPrenom = mPrenom.getText().toString().trim();
                int age = mAge.getInputType();

                //Récupérer actvité chosie par utilisateur
                String activite ="";
                if (mActivite.getSelectedItem() == "Professionnel") activite = "Professionnel";
                if (mActivite.getSelectedItem() == "Particulier") activite = "Particulier";

                //Ajouter les valeurs à la BD
                //Certains champs peuvent ne pas être remplis : comment le prendre en compte ?
                //Peut pas créer une fonction car certains paramètres peuvent changer : cas par cas
                if(nouveauNom != null){
                    enregistrerDonnee("nom", nouveauNom );
                }
                else if(nouveauPrenom != null){
                    enregistrerDonnee("prenom", nouveauPrenom);
                }
            }
        });
    }

    //Fonction permettant de modifier une valeur donnée
    public void enregistrerDonnee(String donnee, String valeur){
        String UserId = getUid();
        mDatabase.child("users").child(UserId).child(donnee).setValue(valeur);
    }
}
