package com.example.cao_amigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.example.cao_amigo.activity.ConBD;
import com.example.cao_amigo.activity.ListarAdocaoUserAdapter;
import com.example.cao_amigo.activity.NovoLarModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityListarAdocaoUser extends AppCompatActivity {

    List<NovoLarModel> animais;
    RecyclerView recyclerView;
    ListarAdocaoUserAdapter ListarAdocaoUserAdapter;



    FirebaseDatabase dbEx = FirebaseDatabase.getInstance();
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_adocao_user);
        recyclerView = findViewById(R.id.RCVUAdocao);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        animais = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        auth = ConBD.FirebaseAutenticacao();
        String UserLogado = auth.getCurrentUser().getEmail();
        ref.child("Adocao").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*for(DataSnapshot dn:snapshot.getChildren()){
                    //String guardValue= snapshot.getKey();
                    String uid = dn.getKey();
                    NovoLarModel u = dn.getValue(NovoLarModel.class);
                    u.setID(uid);
                    if(u.getEmailUser().toString().equals(UserLogado.toString())) {

                        animais.add(u);


                    }
                }*/
                for(DataSnapshot dn:snapshot.getChildren()){
                    String uid = dn.getKey();
                    //dbEx.getReference().child("Adocao").child(uid).updateChildren("ID","teste");
                    NovoLarModel u = dn.getValue(NovoLarModel.class);
                    u.setID(uid);
                    if(u.getEmailUser().toString().equals(UserLogado.toString())) {
                        animais.add(u);
                    }
                    //NovoLarModel b = dn.getValue(NovoLarModel.class);
                    //animais.add(b);
                }

               // Toast.makeText(ActivityListarAdocaoUser.this,UserLogado +" + "+,Toast.LENGTH_LONG).show();
                ListarAdocaoUserAdapter = new ListarAdocaoUserAdapter(animais);
                recyclerView.setAdapter(ListarAdocaoUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
        public void deletar(View view){
        AlertDialog.Builder confExcl = new AlertDialog.Builder(ActivityListarAdocaoUser.this);
        confExcl.setTitle("Tem certeza que deseja excluir?");
        confExcl.setMessage("Para confirmar a exclusão digite o ID da adoção:");
        dbEx.getReference().child("Adocao").child("").removeValue();
        //ListarAdocaoUserAdapter.excluir();
        //dbEx.getReference().child("Adocao").child()
    }


}