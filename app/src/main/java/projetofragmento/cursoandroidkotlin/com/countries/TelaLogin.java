package projetofragmento.cursoandroidkotlin.com.countries;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import projetofragmento.cursoandroidkotlin.com.countries.models.Usuarios;
import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;

public class TelaLogin extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button logar;
    private TextView link_cadastrar;
    private FirebaseAuth firebaseAuth;
    private Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        email = (EditText) findViewById(R.id.edit_email_logar);
        senha = (EditText) findViewById(R.id.edit_senha_logar);
        logar = (Button) findViewById(R.id.btn_logar);
        link_cadastrar = (TextView) findViewById(R.id.txt_link_cadastrar);

        //Método para verificar se o usuário já está logado
        verificarUsuarioLogado();

        //Link para a tela cadastrar
        link_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(intent);

            }
        });

        //Botão logar
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pegando os dados dos EditTexts
                usuario = new Usuarios();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                if(email.getText().toString().isEmpty() ||
                        senha.getText().toString().isEmpty()){

                    Toast.makeText(TelaLogin.this, "Campo(s) vazios!",Toast.LENGTH_LONG).show();

                }else{

                    //Método para fazer login
                    validarLogin();
                    email.setText("");
                    senha.setText("");

                }

            }
        });

    }

    //Faz o a verificação e o login do usuário
    private void validarLogin(){

        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
        firebaseAuth.signInWithEmailAndPassword(

                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(TelaLogin.this, "Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TelaLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    String erroLogin = "";

                    try{

                        throw task.getException();

                    }catch (FirebaseAuthInvalidUserException e){
                        erroLogin = "E-mail não cadastrado!";
                        email.setText("");

                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroLogin = "Senha errada tente novamente!";
                        senha.setText("");

                    }catch (Exception e){
                        erroLogin = "sem conexão com a internet!";
                        e.printStackTrace();
                        email.setText("");
                        senha.setText("");
                    }

                    Toast.makeText(TelaLogin.this, "Erro: " + erroLogin, Toast.LENGTH_LONG).show();
                    Log.e("Signup Error", "onCancelled", task.getException());
                }

            }
        });

    }

    //Verifica se usuário já está logado
    private void verificarUsuarioLogado(){

        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();

        if(firebaseAuth.getCurrentUser() != null){

            Intent intent = new Intent(TelaLogin.this, MainActivity.class);
            startActivity(intent);

        }

    }
}
