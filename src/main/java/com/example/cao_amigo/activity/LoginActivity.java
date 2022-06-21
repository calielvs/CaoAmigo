package com.example.cao_amigo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cao_amigo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText campoEmail,campoSenha;
    Button botaoAcessar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = ConBD.FirebaseAutenticacao();
        inicializarComponente();
    }

    public void cadastrar(View v){
        Intent i = new Intent(this,CadastroActivity.class);
        startActivity(i);
    }

    public void validarAutenticacao(View view){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                UsuariosModel usuario = new UsuariosModel();

                usuario.setEmail(email);
                usuario.setSenha(senha);

                logar(usuario);

            }else{
                Toast.makeText(this,"Digite sua senha", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Digite seu email", Toast.LENGTH_SHORT).show();
        }
    }

    private void logar(UsuariosModel usuario) {

        auth.signInWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirHome();
                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuario não cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email ou senha incorreto";
                    }catch (Exception e){
                        excecao = "Erro ao logar o usuario"+e.getMessage();e.printStackTrace();

                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }

    });
    }
    private void abrirHome() {
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAuth = auth.getCurrentUser();

        if(usuarioAuth != null){
            abrirHome();
        }
    }

    private void inicializarComponente(){
        campoEmail = findViewById(R.id.edtEmailLogin);
        campoSenha = findViewById(R.id.edtSenhaLogin);
        botaoAcessar = findViewById(R.id.btnLogar);
    }
}

