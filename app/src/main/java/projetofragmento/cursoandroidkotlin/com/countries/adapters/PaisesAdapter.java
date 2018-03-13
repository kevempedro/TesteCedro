package projetofragmento.cursoandroidkotlin.com.countries.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import projetofragmento.cursoandroidkotlin.com.countries.R;
import projetofragmento.cursoandroidkotlin.com.countries.models.Country;
import projetofragmento.cursoandroidkotlin.com.countries.models.CountryFirebase;
import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;

/**
 * Created by Lupy on 09/03/2018.
 */

public class PaisesAdapter extends ArrayAdapter<Country> {

    private Context context;
    private ArrayList<Country> countries;

    public PaisesAdapter(Context context, ArrayList<Country> countries){
        super(context, R.layout.lista_paises, countries);
        this.context = context;
        this.countries = countries;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.lista_paises, parent, false);

        Country country = countries.get(position);

        TextView shortName = (TextView) convertView.findViewById(R.id.text_paises);
        shortName.setText(country.getShortname());

        ImageView flag = (ImageView) convertView.findViewById(R.id.img_flag);
        Picasso.get().load("http://sslapidev.mypush.com.br/world/countries/" + country.getId() + "/flag").into(flag);

        return convertView;

    }
}
