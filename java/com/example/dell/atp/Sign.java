package com.example.dell.atp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign extends AppCompatActivity {


    //Déclaration variables graphique
    private EditText mEmail, mPassword;
    private Button mBtnSignIn, mBtnSignUp, mBtnResetPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    //Déclaration DB
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        //Instanciation authentification firebase
        mAuth = FirebaseAuth.getInstance();

        //Connecte automatiquement sur le la page Account
        //Vérifier authentification au début de l'activitité
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(Sign.this, Account.class));
            finish();
        }

        //Instanciation DB
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Référencement éléments graphiques
        mBtnSignIn = findViewById(R.id.sign_in_button);
        mBtnSignUp = findViewById(R.id.sign_up_button);
        mBtnResetPassword = findViewById(R.id.btn_reset_password);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mProgressBar = findViewById(R.id.progressBar);

        //Instanciation authentification firebase
        mAuth = FirebaseAuth.getInstance();

        //REINITIALISER MDP
        mBtnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBtnResetPassword.setEnabled(false);
                String email = mEmail.getText().toString().trim();

                //Si Email non renseigné
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), "Entrez votre adresse mail de connexion.", Toast.LENGTH_SHORT).show();
                    return;
                }

                    mProgressBar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Sign.this, "Un mail vient d'être envoyé à cette adresse.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Sign.this, "L'envoi du mail a échoué. Vérifiez votre adresse de connexion.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    mProgressBar.setVisibility(View.GONE);
            }

        });

        //SE CONNECTER AVEC SON EMAIL
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Récupération des données de connexion
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                existeChamps(email, password);

                //Afficher message si mdp trop court
                if (password.length() < 6) {
                    //Toast.makeText(getApplicationContext(), "Entrez au minimum 6 caractères !", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                //Connexion
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Sign.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mProgressBar.setVisibility(View.GONE);

                                if(!task.isSuccessful()){
                                    //Echec connexion
                                    Toast.makeText(getApplicationContext(), "L'identification a échoué...", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Connexion avec succès
                                    Toast.makeText(getApplicationContext(), "Identification réussie !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Sign.this, Account.class));
                                    finish();

                                }

                            }
                        });
            }
        });

        //CREER UN COMPTE
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Récupération des données renseignées
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                existeChamps(email, password);

                /*Vérifier si utilisateur existe déjà
                String ID = getUid();
                if (ID != null){
                    Toast.makeText(getApplicationContext(), "Cette adresse est déjà associée à un compte.", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                //Afficher message si mdp trop court
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Entrez au minimum 6 caractères !", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                //Créer utilisateur avec Email
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Sign.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mProgressBar.setVisibility(View.GONE);

                                //Création utilisateur a échoué :
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Sign.this, "L'enregistrement a échoué.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Création réussie :
                                    Toast.makeText(Sign.this, "Compte créé avec succès !", Toast.LENGTH_SHORT).show();
                                    succesAuth(task.getResult().getUser());
                                    finish();
                                }
                            }
                        });
            }

        });

    }

    //Récupérer le surnom à partir de l'email
    private String surnomEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    //Authentification
    private void succesAuth(FirebaseUser user){
        //Récupérer surnom à partir de l'adresse mail
        String surnom = surnomEmail(user.getEmail());

        //Créer nouvel utilisateur
        creerNouvelUtilisateur(user.getUid(), surnom, user.getEmail());

        //Aller à l'activité Account
        startActivity(new Intent(Sign.this, Account.class));
    }

    //Vérifier que les Text ont bien été remplis
    private void existeChamps(String email, String password){

        if(email.isEmpty()) {
            mEmail.setError("Champs requis");
            return;
        }

        if(password.isEmpty()){
            mPassword.setError("Champs requis");
            return;
        }

        return;
    }

    //Nouvel utilisateur
    public void creerNouvelUtilisateur(String userId, String surnom, String email){
        User user = new User(surnom, email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    //Récupérer l'Id de l'utilisateur actuel
    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    //Récupérer Utilisateur actuel
    public static FirebaseUser getUser() { return FirebaseAuth.getInstance().getCurrentUser();}

}
