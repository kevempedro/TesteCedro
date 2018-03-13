package projetofragmento.cursoandroidkotlin.com.countries.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import projetofragmento.cursoandroidkotlin.com.countries.R;
import projetofragmento.cursoandroidkotlin.com.countries.adapters.VisitadosAdapter;
import projetofragmento.cursoandroidkotlin.com.countries.models.CountryFirebase;
import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitadosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private CountryFirebase paisExcluir;
    private DatabaseReference databaseReference;
    private ArrayList<CountryFirebase> countriesFirebase;
    private ValueEventListener valueEventListenerVisitados;
    private String codeText;
    private String usuarioLocal;
    private FirebaseAuth firebaseAuth;

    public VisitadosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visitados, container, false);

        countriesFirebase = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.list_visitados);

        //Instância o adapter
        adapter = new VisitadosAdapter(getActivity(), countriesFirebase);

        listView.setAdapter(adapter);

        //Usuario logado
        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
        usuarioLocal =  firebaseAuth.getCurrentUser().getUid();


        //Recupera os países do FireBase
        databaseReference = ConfiguracaoFirebase.getReferencia()
                .child("Countries")
                .child(usuarioLocal);

        //Listerner para verificar os países do firebase
        valueEventListenerVisitados = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                countriesFirebase.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){

                    CountryFirebase country = dados.getValue(CountryFirebase.class);
                    countriesFirebase.add(country);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();

            }
        };

        //AlertDialog com os Detalhes
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View buildView = getLayoutInflater().inflate(R.layout.dialog_detalhes_visitados, null);

                //Recupera views do layout dialog_detalhes
                TextView shortName = (TextView) buildView.findViewById(R.id.short_dialog_visitados);
                TextView longName = (TextView) buildView.findViewById(R.id.long_dialog_visitados);
                TextView code = (TextView) buildView.findViewById(R.id.code_dialog_visitados);
                ImageView flag = (ImageView) buildView.findViewById(R.id.img_dialog_visitados);

                //Configura os nomes de acordo com a posição
                final String shortText = countriesFirebase.get(position).getShortName();
                final String longText = countriesFirebase.get(position).getLongName();
                final String codeText = countriesFirebase.get(position).getCode();
                Picasso.get().load("http://sslapidev.mypush.com.br/world/countries/" + countriesFirebase.get(position).getId() +
                        "/flag").into(flag);

                shortName.setText(shortText);
                longName.setText(longText);
                code.setText(codeText);

                //Recupera classe CountryFirebase
                paisExcluir = (CountryFirebase) adapter.getItem(position);

                //Botão de confirmação
                builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Recupera os países do FireBase
                                databaseReference = ConfiguracaoFirebase.getReferencia()
                                        .child("Countries");

                                databaseReference.child(paisExcluir.getId().toString()).removeValue();

                                Toast.makeText(getActivity(), "País excluido com sucesso!", Toast.LENGTH_SHORT).show();

                            }

                        });

                //Botão de fechar
                builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setCancelable(false);
                builder.setView(buildView);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerVisitados);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerVisitados);
    }

}
