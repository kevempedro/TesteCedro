package projetofragmento.cursoandroidkotlin.com.countries.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import projetofragmento.cursoandroidkotlin.com.countries.R;
import projetofragmento.cursoandroidkotlin.com.countries.models.CountryFirebase;

/**
 * Created by Lupy on 10/03/2018.
 */

public class VisitadosAdapter extends ArrayAdapter<CountryFirebase> {

    private Context context;
    private ArrayList<CountryFirebase> countriesFirebase;

    public VisitadosAdapter(Context context, ArrayList<CountryFirebase> countriesFirebase) {
        super(context, 0, countriesFirebase);
        this.context = context;
        this.countriesFirebase = countriesFirebase;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CountryFirebase countryFirebase = countriesFirebase.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.lista_visitados, parent, false);

        TextView shortName = (TextView) convertView.findViewById(R.id.text_short_visitados);
        TextView data = (TextView) convertView.findViewById(R.id.text_data_visitados);
        ImageView flag = (ImageView) convertView.findViewById(R.id.img_visitados);

        shortName.setText(countryFirebase.getShortName());
        data.setText(countryFirebase.getData());
        Picasso.get().load("http://sslapidev.mypush.com.br/world/countries/" + countryFirebase.getId() +
                "/flag").into(flag);

        return convertView;

    }
}
