package projetofragmento.cursoandroidkotlin.com.countries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Splash extends AppCompatActivity {

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iniciaSplash();
        initApp();

    }

    private void initApp() {

    }

    private void showNotification(){


    }

    private void sendTextToNotification(String txt){


    }

    public void iniciaSplash (){


        new Thread(new Runnable() {

            @Override
            public void run() {

                counter ++;

                try{
                    while(counter == 1 || counter<= 2){

                        Thread.sleep(1000);

                        counter ++;

                        Log.e("Counter= ", Integer.toString(counter));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

                if(counter == 3){

                    Intent it = new Intent(Splash.this, TelaLogin.class);
                    startActivity (it);
                    finish();

                    counter ++;

                    Log.e("Counter=", Integer.toString(counter));

                }

            }
        }).start();

    }

}
