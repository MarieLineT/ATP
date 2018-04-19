package com.example.dell.atp.Classe;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.atp.Classe.Item;
import com.example.dell.atp.R;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    //items est la liste des models à afficher
    public ItemAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Lorsque vue créee : Android fournit un convertView null
        //sinon : il nous fournit une vue recyclée
        if(convertView == null){

            //Récupération d'un cellule_item.xml via LayoutInflater
            //charge un objet xml dans un objet View
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cellule_item,parent, false);
        }
        //Création contrôleur
        ItemViewHolder viewHolder = (ItemViewHolder) convertView.getTag();

        //les vues seront réutilisées, cellule possède déjà un ViewHolder
        if(viewHolder == null){
            //Récupérer les sous-vues
            viewHolder = new ItemViewHolder();
            viewHolder.pseudo = convertView.findViewById(R.id.pseudo);
            viewHolder.profession = convertView.findViewById(R.id.profession);
            viewHolder.desc = convertView.findViewById(R.id.desc);
            //Sauvegarder le contrôleur dans la vue
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Item item = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(item.get_pseudo());
        viewHolder.profession.setText(item.get_profession());
        viewHolder.desc.setText(item.get_text());


        //Vue renvoyée à adaptateur pour être affichée
        //est mise à recyclée une fois sortie de l'écran
        return convertView;
    }

    //OBJET MODEL ViewHolder
    //Contrôleur associé à chaque cellule : stocke les références vers les vues
    private class ItemViewHolder {
        public TextView pseudo;
        public TextView profession;
        public TextView desc;
    }
}
