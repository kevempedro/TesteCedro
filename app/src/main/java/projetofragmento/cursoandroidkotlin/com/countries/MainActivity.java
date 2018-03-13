package projetofragmento.cursoandroidkotlin.com.countries;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import projetofragmento.cursoandroidkotlin.com.countries.adapters.TabsAdapter;
import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;
import projetofragmento.cursoandroidkotlin.com.countries.services.CountryService;
import projetofragmento.cursoandroidkotlin.com.countries.services.CountryServiceProvider;
import projetofragmento.cursoandroidkotlin.com.countries.utils.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera Views
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //Configurando Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setTitle("World Countries");
        setSupportActionBar(toolbar);

        //Configura Adapter
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_tab);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_sair:
                deslogarUsuario();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }

    private void deslogarUsuario() {

        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();

        if (firebaseAuth.getCurrentUser() != null) {

            firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
            firebaseAuth.signOut();

            Toast.makeText(MainActivity.this, "Conta deslogada com sucesso!", Toast.LENGTH_LONG).show();
            finish();

            Intent intent = new Intent(MainActivity.this, TelaLogin.class);
            startActivity(intent);

        }

    }
}
