package com.example.dell.atp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private ListView mListView;
    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Récupération de la liste créée dans le fichier main.xml
        mListView = findViewById(R.id.listView);

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"
        //Création de la ArrayList permettant de remplir la ListView
        /*final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestActivity.this,
                android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);*/

        List<Item> items = genererItems();

        ItemAdapter adapter = new ItemAdapter(TestActivity.this, items);
        mListView.setAdapter(adapter);
    }

    private List<Item> genererItems(){
        List<Item> items = new ArrayList<Item>();
        for(int i = 0; i<prenoms.length; i++){
            items.add(new Item(prenoms[i]));
        }
        return items;
    }
}
