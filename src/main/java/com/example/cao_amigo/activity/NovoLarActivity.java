package com.example.cao_amigo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cao_amigo.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class NovoLarActivity extends AppCompatActivity {
    //Firabase Auth e Sotrage
    //FirebaseAuth authenticacao;
    FirebaseAuth auth;
    StorageReference mStorageRef;
    FirebaseDatabase database;
    DatabaseReference rootDatabaseref;
    List<NovoLarModel> animais;

    //Variaveis e construtores
    NovoLarModel novoLar;

    EditText campoNomeAmigo,campoIdade,campoRaca,campoPeso,campoRClinico,campoVacina,campoInfoAdd,campoMAdocao,campoBreveApre,campoEndereco,campoTelefone;
    String stringTest,emailUserSave,ID,adotado;
    ImageView img;
    Button botaoCadastrarAdocao,botaoGaleria,botaoCarregar;
    //Tipo de permissão 1001 é a permissão para a galeria
    int PERMISSION_CODE_GALERY = 1001; //acesso a galeria

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_lar);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        auth = ConBD.FirebaseAutenticacao();
        inicializar();
    }
    //relecionar as variaveis criadas com os campos de inputText
    private void inicializar(){
        campoNomeAmigo = findViewById(R.id.edtAmigoNome);
        campoIdade = findViewById(R.id.edtIdade);
        campoRaca =findViewById(R.id.edtAmigoRaca);
        campoPeso =findViewById(R.id.edtAmigoPeso);
        campoRClinico =findViewById(R.id.edtProblemasClinicos);
        campoVacina =findViewById(R.id.edtVacinacao);
        campoInfoAdd =findViewById(R.id.edtInformacoesAdicionais);
        campoMAdocao =findViewById(R.id.edtMotivoAdocao);
        campoBreveApre = findViewById(R.id.edtApresentacao);
        campoEndereco = findViewById(R.id.edtEndNovoLar);
        campoTelefone = findViewById(R.id.edtContato);
        botaoCadastrarAdocao = findViewById(R.id.btnCadastrarAdocao);
        img = findViewById(R.id.imgView);
        emailUserSave = auth.getCurrentUser().getEmail();
        botaoGaleria = findViewById(R.id.btnConhecerAmigo);
    }
    //Necessário criar uma intent pois vamos abrir uma nova tela (galeria)
    public void escolherImagem(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1000);
    }
    //Validar permissão para acessar a galeria e caso não tenha, solicitar
    public void abrirGaleria (View view){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            String[] permissao = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissao, PERMISSION_CODE_GALERY);
         }else{
            escolherImagem();
        }
    }

    //Solicitando a permissão para abrir a galeria e caso já tenha, não precisa solicitar que irá brir direto
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1001:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    escolherImagem();
                }else{
                    Toast.makeText(this, "Necessário habilitar permissão", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //Setando a imagem que cadastramos na imagemView do XML
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1000){
            img.setImageURI(data.getData());
                    }
    }
    public void carregarFt(View view){
            enviarFoto2();
    }
    public void validarCampos(View view){
        String nome = campoNomeAmigo.getText().toString();
        String idade = campoIdade.getText().toString();
        String raca = campoRaca.getText().toString();
        String peso = campoPeso.getText().toString();
        String rClinico = campoRClinico.getText().toString();
        String vacina = campoVacina.getText().toString();
        String adInfo = campoInfoAdd.getText().toString();
        String mAdocao = campoMAdocao.getText().toString();
        String apresentacao = campoBreveApre.getText().toString();
        String testeUri = stringTest;
        String endereco = campoEndereco.getText().toString();
        String telefone = campoTelefone.getText().toString();
        adotado = "0";

        if(!nome.isEmpty()){
            if(!idade.isEmpty()){
                if(!raca.isEmpty()){
                    if(!peso.isEmpty()){
                        if(!telefone.isEmpty()){
                            if(!endereco.isEmpty()){
                                if(!rClinico.isEmpty()){
                                    if(!vacina.isEmpty()){
                                        if(!mAdocao.isEmpty()){
                                            if(!apresentacao.isEmpty()){
                                                novoLar = new NovoLarModel();
                                                novoLar.setNomeAnimal(nome);
                                                novoLar.setIdade(idade);
                                                novoLar.setRacaAnimal(raca);
                                                novoLar.setPeso(peso);
                                                novoLar.setProblemaClinico(rClinico);
                                                novoLar.setVacina(vacina);
                                                novoLar.setInfoAdicional(adInfo);
                                                novoLar.setMotivoAdocao(mAdocao);
                                                novoLar.setApresentacao(apresentacao);
                                                novoLar.setUriImagem(testeUri);
                                                novoLar.setEmailUser(emailUserSave);
                                                novoLar.setEndereço(endereco);
                                                novoLar.setContato(telefone);

                                                //novoLar.setUriImagem();

                                                cadastrarAmigoAdocao();



                                            }else{
                                                Toast.makeText(this, "Preencha a apresentação", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(this, "Preencha o motivo da adoção", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(this, "Preencha o campo vacinacao", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(this, "Preencha os dados clinicos", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(this, "Preencha o Endereço", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(this, "Preencha o Contato", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Preencha o peso", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Preencha a raca", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Preencha a idade", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }


    }

    public void cadastrarAmigoAdocao(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        //Forma antiga de cadastro
        ref.child("Adocao").push().setValue(novoLar);


        //Cadastro definindo o ID como eu quiser
        //ref.child("Adocao").child(ID).push().setValue(novoLar);
        }

    public void enviarFoto(){
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagem = byteArrayOutputStream.toByteArray();

        StorageReference imgRef = mStorageRef.child(campoNomeAmigo.getText().toString() + "_" + campoRaca.getText().toString() + "_" + campoIdade.getText().toString() + ".JPEG");
        UploadTask uploadTask = imgRef.putBytes(imagem);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(NovoLarActivity.this,"Foto cadastrada com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Enviar foto e salvar a URL para posteriormente conseguir faer o download dela
    //Necessário validar a questão da URI, não está enviando para o DATABASE
    public void enviarFoto2(){

        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagem = byteArrayOutputStream.toByteArray();
        StorageReference imgRef = mStorageRef.child(campoNomeAmigo.getText().toString() + "_" + campoRaca.getText().toString() + "_" + campoIdade.getText().toString() + ".JPEG");

        UploadTask uploadTask = imgRef.putBytes(imagem);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){

            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return imgRef.getDownloadUrl();
            }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                 if(task.isSuccessful()) {
                    Uri uri = task.getResult();
                    stringTest = uri.toString();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(NovoLarActivity.this);
                    alerta.setMessage("Teste" + stringTest).show();

                    Toast.makeText(NovoLarActivity.this, "Upload realizado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NovoLarActivity.this, "Erro ao realizar Upload", Toast.LENGTH_SHORT).show();
                }

            };
        });
    }
}