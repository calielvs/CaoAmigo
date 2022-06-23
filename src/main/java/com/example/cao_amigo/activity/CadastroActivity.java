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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    UsuariosModel usuario;
    FirebaseAuth authenticacao;
    EditText campoNome,campoEmail,campoTelefone,campoEndereco,campoSenha,campoCSenha;
    Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializar();
    }

    private void inicializar(){
        campoNome = findViewById(R.id.edtNome);
        campoEmail = findViewById(R.id.edtEmailCad);
        campoTelefone = findViewById(R.id.edtTelefone);
        campoEndereco = findViewById(R.id.edtEndereco);
        campoSenha = findViewById(R.id.edtSenhaCad);
        campoCSenha = findViewById(R.id.edtConfirmSenha);
        botaoCadastrar = findViewById(R.id.btnCadastrar);

    }
    //Valida se nenhum campo está vazio para depois seguir para o cadastro
    public void validarCampos(View v){
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String end = campoEndereco.getText().toString();
        String senha = campoSenha.getText().toString();
        String csenha = campoCSenha.getText().toString();

        if(!nome.isEmpty()){
            if(!email.isEmpty()){
                if(!telefone.isEmpty()){
                    if(!end.isEmpty()){
                        if(!senha.isEmpty()){
                            if(senha.equals(csenha)){
                                usuario = new UsuariosModel();

                                usuario.setNome(nome);
                                usuario.setEmail(email);
                                usuario.setContato(telefone);
                                usuario.setEndereco(end);
                                usuario.setSenha(senha);
                                usuario.setcSenha(csenha);


                                cadastrarUsuario();
                                salvarUsuarioDB();


                            }else{
                                Toast.makeText(this, "As senhas não conferem, tente novamente", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Preencha o endereco", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Preencha o telefone", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
    }
    //Função que de fato faz o cadastro do usuario e tambem valida a conexão com o BD e outras validações do próprio Firebase
    public void cadastrarUsuario(){

        authenticacao = ConBD.FirebaseAutenticacao();
        authenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Usuario cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um email válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Email já cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuario" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void salvarUsuarioDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("Usuario").push().setValue(usuario);
    }
}