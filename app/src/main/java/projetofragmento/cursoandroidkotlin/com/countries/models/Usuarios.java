package projetofragmento.cursoandroidkotlin.com.countries.models;


import com.google.firebase.database.DatabaseReference;

import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;

public class Usuarios {

    private String id;
    private String email;
    private String senha;

    public Usuarios(){

    }

    public void Salvar(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getReferencia();
        databaseReference.child("Countries").child(getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
