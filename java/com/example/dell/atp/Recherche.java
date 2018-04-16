package com.example.dell.atp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    private List<Item> items;

    final ArrayList<String> nomsUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        //Instanciation DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");

        //Référencement des éléments graphiques
        mProfession = findViewById(R.id.profession);
        mNom = findViewById(R.id.nomR);
        mMotsCle = findViewById(R.id.motscle);
        mListView = findViewById(R.id.listView);
        mRechercher = findViewById(R.id.btn_rechercher);

        items = new ArrayList<Item>();


        mRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Réinitialiser ArrayList pour ne pas que la ListView
                //n'affiche plusieurs fois le contenu à chaque click
                items.clear();

                //Récupération donnée utlisateur
                final String professionRecherche = mProfession.getText().toString().trim();
                final String nomRecherche = mNom.getText().toString().trim();
                final String motscleRecherche = mMotsCle.getText().toString().trim();

                //Générer Items
                genererItems(nomRecherche, professionRecherche, motscleRecherche);

            }
        });

        //Ouverture boite de dialogue en cliquant sur un item
        //Redirection vers message
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(Recherche.this)
                        .setTitle("Contacter...")
                        .setMessage("Envoyer un message à "+items.get(position).get_pseudo()+" ?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(Recherche.this, Account.class));
                                finish();
                            }
                        }).create().show();

                return true;

                }
            });

    }
    //Fonction requête de recherche
    public void genererItems(final String nomRecherche, final String professionRecherche,
                             final String motscleRecherche){

        //Requête de récupération données DB
        myRef.orderByChild("_nom").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Récupérer attributs utilisateurs via classe User
                User users = dataSnapshot.getValue(User.class);

                //Récupérer les valeurs de ces attributs pour tous les utilisateurs
                //Les passer en minuscule
                String nom = users.get_nom();
                String surnom = users.get_surnom();
                String profession = users.get_profession();
                String description = users.get_description();

                try {
                    //Si utilisateur a renseigné le nom
                    if(!nomRecherche.isEmpty()) {
                        boolean contient = verifString(nom, nomRecherche);
                        if (contient) items.add(new Item(nom, profession, description));
                    }
                    else if(!professionRecherche.isEmpty()) {
                        boolean contientBis = verifString(profession, professionRecherche);
                        if (contientBis) items.add(new Item(nom, profession, description));
                        }
                    else if (!motscleRecherche.isEmpty()){
                        boolean contientTer = verifString(description, motscleRecherche);
                        if(contientTer) items.add(new Item(nom, profession, description));
                    }
                    else{
                        Toast.makeText(Recherche.this, "Aucun champ n'a été renseigné.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){}

                ItemAdapter adapter = new ItemAdapter(Recherche.this, items);
                mListView.setAdapter(adapter);
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

    //Fonction permettant de vérifier que la donnée de la base (data) contient tout
    //ou partie de l'information renseignée par l'utilisateur (renseigne)
    public boolean verifString(String data, String renseigne){

        //Mettre les String en minuscule
        data = data.toLowerCase();
        renseigne = renseigne.toLowerCase();

        //Vérifier si data contient le renseignement
        boolean contient = data.contains(renseigne);

        //True = contient, false = ne contient pas
        return contient;
    }
}
