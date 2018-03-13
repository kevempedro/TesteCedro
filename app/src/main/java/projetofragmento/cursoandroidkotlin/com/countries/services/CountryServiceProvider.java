package projetofragmento.cursoandroidkotlin.com.countries.services;


import android.util.Log;
import android.widget.ArrayAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import projetofragmento.cursoandroidkotlin.com.countries.events.CountryEvent;
import projetofragmento.cursoandroidkotlin.com.countries.models.Country;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryServiceProvider {

    private static final String BASE_URL = "http://sslapidev.mypush.com.br/world/countries/active/";
    private Retrofit retrofit;

    //Método para conexão om a API
    public Retrofit getRetrofit(){

        if(retrofit == null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }

        return this.retrofit;

    }

}
