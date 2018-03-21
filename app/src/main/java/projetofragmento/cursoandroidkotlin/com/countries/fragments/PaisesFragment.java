package projetofragmento.cursoandroidkotlin.com.countries.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import projetofragmento.cursoandroidkotlin.com.countries.R;
import projetofragmento.cursoandroidkotlin.com.countries.adapters.PaisesAdapter;
import projetofragmento.cursoandroidkotlin.com.countries.models.Country;
import projetofragmento.cursoandroidkotlin.com.countries.models.CountryFirebase;
import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;
import projetofragmento.cursoandroidkotlin.com.countries.services.CountryService;
import projetofragmento.cursoandroidkotlin.com.countries.services.CountryServiceProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.CamcorderProfile.get;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaisesFragment extends Fragment {

    private ArrayList<Country> countries;
    private ListView listView;
    private DatabaseReference databaseCountry;
    private String usuarioLocal;
    private FirebaseAuth firebaseAuth;

    public PaisesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_paises, container, false);

        listView = (ListView) view.findViewById(R.id.list_paises);

        //Conexão com a class  CountryServiceProvider
        CountryServiceProvider countryServiceProvider = new CountryServiceProvider();

        //Conexão com a class  CountryService
        CountryService countryService = countryServiceProvider.getRetrofit().create(CountryService.class);
        Call call = countryService.getCountry();

        //Chamada de requisição
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                countries = (ArrayList<Country>) response.body();
                listView.setAdapter(new PaisesAdapter(getActivity(), countries));

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();

            }
        });

        //AlertDialog com os Detalhes
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View buildView = getLayoutInflater().inflate(R.layout.dialog_detalhes, null);

                //Recupera views do layout dialog_detalhes
                TextView shortName = (TextView) buildView.findViewById(R.id.short_dialog);
                TextView longName = (TextView) buildView.findViewById(R.id.long_dialog);
                TextView code = (TextView) buildView.findViewById(R.id.code_dialog);
                ImageView flag = (ImageView) buildView.findViewById(R.id.img_dialog);
                final EditText data = (EditText) buildView.findViewById(R.id.edit_dialog);

                //Criando as mascaras para os campos de entrada
                SimpleMaskFormatter simpleMaskNumero = new SimpleMaskFormatter("NN/NN/NNNN");
                MaskTextWatcher maskTextNumero = new MaskTextWatcher(data, simpleMaskNumero);
                data.addTextChangedListener(maskTextNumero);

                //Configura os nomes de acordo com a posição
                final String shortText = countries.get(position).getShortname();
                final String longText = countries.get(position).getLongname();
                final String codeText = countries.get(position).getCallingCode();
                final Integer idText = countries.get(position).getId();
                Picasso.get().load("http://sslapidev.mypush.com.br/world/countries/" + countries.get(position).getId() +
                        "/flag").into(flag);

                shortName.setText(shortText);
                longName.setText(longText);
                code.setText(codeText);

                //Butao salvar
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            final String dataText = data.getText().toString();

                        //Usuario logado
                        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
                        usuarioLocal =  firebaseAuth.getCurrentUser().getUid();

                            databaseCountry = ConfiguracaoFirebase.getReferencia();

                            databaseCountry.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(data.getText().toString().isEmpty()){

                                            Toast.makeText(getActivity(), "Preencha a data!", Toast.LENGTH_LONG).show();

                                        }else{

                                            databaseCountry = ConfiguracaoFirebase.getReferencia();
                                            databaseCountry = databaseCountry.child("Countries")
                                                    .child(usuarioLocal)
                                                    .child(idText.toString());

                                            //Salva os dados no firebase
                                            CountryFirebase country = new CountryFirebase();
                                            country.setShortName(shortText);
                                            country.setLongName(longText);
                                            country.setData(dataText);
                                            country.setCode(codeText);
                                            country.setId(idText);

                                            databaseCountry.setValue(country);

                                            Toast.makeText(getActivity(), "País salvo com sucesso.", Toast.LENGTH_LONG).show();

                                        }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {



                                }
                            });

                        }
                });

                //Botão de cancelar
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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

}
