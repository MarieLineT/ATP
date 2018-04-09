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
    //Liste Test
    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"};

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

        //TEST
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomsUsers);
        mListView.setAdapter(arrayAdapter);

        //TEST
        /*List<Item> items = requete();
        ItemAdapter adapter = new ItemAdapter(Recherche.this, items);
        mListView.setAdapter(adapter);*/

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //Récupérer getKey = "_nom" !!!
                    //Toast.makeText(Recherche.this, "Nom : "+key, Toast.LENGTH_LONG).show();
                    //if(nom == null) nomsUsers.add("Non renseigné");
                    //else nomsUsers.add(nom);
                }
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

}
