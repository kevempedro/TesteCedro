package projetofragmento.cursoandroidkotlin.com.countries.models;

import com.google.firebase.database.DatabaseReference;

import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;

/**
 * Created by Lupy on 10/03/2018.
 */

public class CountryFirebase {

    private String shortName;
    private String longName;
    private String code;
    private String data;
    private Integer id;

    public CountryFirebase() {

    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}
}
