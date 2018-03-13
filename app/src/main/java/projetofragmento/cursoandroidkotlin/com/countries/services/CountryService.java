package projetofragmento.cursoandroidkotlin.com.countries.services;


import java.util.ArrayList;
import java.util.List;

import projetofragmento.cursoandroidkotlin.com.countries.models.Country;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface CountryService {

    @GET(".")
    Call<ArrayList<Country>> getCountry();

}
