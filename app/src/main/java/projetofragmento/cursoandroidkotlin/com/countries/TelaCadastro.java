package projetofragmento.cursoandroidkotlin.com.countries;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import projetofragmento.cursoandroidkotlin.com.countries.models.Usuarios;
import projetofragmento.cursoandroidkotlin.com.countries.services.ConfiguracaoFirebase;

public class TelaCadastro extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button cadastrar;
    private FirebaseAuth firebaseAuth;
    private Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        email = (EditText) findViewById(R.id.edit_email_cadastrar);
        senha = (EditText) findViewById(R.id.edit_senha_cadastrar);
        cadastrar = (Button) findViewById(R.id.btn_cadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pegando os dados dos EditTexts
                usuario = new Usuarios();

                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                //Método de cadastro do usuário
                cadastrarUsuario();

            }
        });


    }

    //Valida e cadastra os usuários
    private void cadastrarUsuario(){

        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();

        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(TelaCadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(TelaCadastro.this, "cadastrado com sucesso!",Toast.LENGTH_LONG).show();

                    FirebaseUser firebaseUser = task.getResult().getUser();
                    usuario.setId(firebaseUser.getUid());

                    email.setText("");
                    senha.setText("");

                    finish();

                }else{
                    String erroCadastrar = "";

                    try{

                        throw task.getException();

                    }catch(FirebaseAuthWeakPasswordException e){
                        erroCadastrar = "Digite uma senha mais forte, contendo caracteres e com letras e números!";
                        senha.setText("");

                    }catch(FirebaseAuthInvalidCredentialsException e){
                        erroCadastrar = "O e-mail digitado é inválido, digite um novo e-mail!";
                        email.setText("");

                    }catch (FirebaseAuthUserCollisionException e){
                        erroCadastrar = "Essse e-mail já está em uso no App!";
                        email.setText("");

                    }catch (Exception e){
                        erroCadastrar = "Ao cadastrar!";
                        e.printStackTrace();
                        email.setText("");
                        senha.setText("");
                    }

                    Toast.makeText(TelaCadastro.this, "Erro: " + erroCadastrar,Toast.LENGTH_LONG).show();
                    Log.e("Signup Error", "onCancelled", task.getException());
                }

            }
        });

    }

}
