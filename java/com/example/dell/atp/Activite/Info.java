package com.example.dell.atp.Activite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.atp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Info extends AppCompatActivity {

    //Déclaration DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Déclaration viariables graphique
    private EditText mNom, mPrenom, mAge, mPro, mDescription;
    private ProgressBar mProgressBar;
    private Button mBtnModif;
    private CheckBox mCheckbxProfessionnel;

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
        myRef = database.getReference("users").child(Sign.getUid());

        //Référencement des éléments graphiques
        mNom = findViewById(R.id.nom);
        mPrenom = findViewById(R.id.prenom);
        mAge = findViewById(R.id.age);
        mDescription = findViewById(R.id.description);
        mProgressBar = findViewById(R.id.progressBar);
        mBtnModif = findViewById(R.id.btn_modif);
        mPro = findViewById(R.id.pro);
        mCheckbxProfessionnel = findViewById(R.id.professionnel);

        //Action checkbox professionnel :
        mCheckbxProfessionnel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Choix catégorie
                boolean professionnel = mCheckbxProfessionnel.isChecked();

                //Si la case est checkée, l'activité est enregistrée en tant que
                //"professionnel(le)"
                if(professionnel) {
                    enregistrerDonneeString("_activite", "professionnel(le)");
                    Toast.makeText(Info.this, "Renseignez votre profession", Toast.LENGTH_SHORT).show();
                    mPro.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                }
                else{//Sinon l'activité est enregistrée en tant que "particulier"
                    enregistrerDonneeString("_activite", "particulier");
                    Toast.makeText(Info.this, "Statut de particulier par défaut", Toast.LENGTH_SHORT).show();
                    mPro.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Action du clic sur bouton : ENREGISTRER MODIFICATIONS
        mBtnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressBar.setVisibility(View.VISIBLE);

                //Enregistrer prénom
                try{
                    final String nouveauPrenom = mPrenom.getText().toString().trim();
                    if (!nouveauPrenom.isEmpty()) enregistrerDonneeString("_prenom", nouveauPrenom);
                }catch (Exception e){}

                //Enregistrer âge
                try{
                    int age = Integer.parseInt(mAge.getText().toString().trim());
                    //Convertir int age en String :
                    final String nouvelAge = String.valueOf(age);
                    if (!nouvelAge.isEmpty()) enregistrerDonneeString("_age", nouvelAge);
                }catch(Exception e){}

                //Enregistrer nom
                try{
                    final String nouveauNom = mNom.getText().toString().trim();
                    if (!nouveauNom.isEmpty()) enregistrerDonneeString("_nom", nouveauNom);
                }catch(Exception e){}

                //Enregistrer profession
                try{
                    final String nouvelleProfession = mPro.getText().toString().trim();
                    if(!nouvelleProfession.isEmpty()) enregistrerDonneeString("_profession", nouvelleProfession);
                }catch (Exception e){}

                //Enregistrer description
                try{
                    final String nouvelleDescription = mDescription.getText().toString().trim();
                    if(!nouvelleDescription.isEmpty()) enregistrerDonneeString("_description", nouvelleDescription);
                }catch (Exception e){}

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

}
