package com.example.cao_amigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cao_amigo.activity.AdocaoAdapter;
import com.example.cao_amigo.activity.ConBD;
import com.example.cao_amigo.activity.ListarAdocaoUserAdapter;
import com.example.cao_amigo.activity.NovoLarModel;
import com.google.firebase.auth.AuthCredential;
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
        String teste = "";
        ref.child("Adocao").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dn:snapshot.getChildren()){
                    NovoLarModel u = dn.getValue(NovoLarModel.class);
                    if(u.getEmailUser().toString().equals(UserLogado.toString())) {
                        animais.add(u);
                    }
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
}