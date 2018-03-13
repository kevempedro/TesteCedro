package projetofragmento.cursoandroidkotlin.com.countries.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Lupy on 10/03/2018.
 */

public class ConfiguracaoFirebase {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getReferencia(){

        if(databaseReference == null){

            databaseReference = FirebaseDatabase.getInstance().getReference();

        }

        return databaseReference;
    }

    public static FirebaseAuth getAutenticacao(){

        if (firebaseAuth == null){

            firebaseAuth = FirebaseAuth.getInstance();

        }

        return firebaseAuth;
    }

}
