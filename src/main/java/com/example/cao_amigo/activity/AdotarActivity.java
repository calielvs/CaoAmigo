package com.example.cao_amigo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cao_amigo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdotarActivity extends AppCompatActivity {
    List<NovoLarModel> animais;
    RecyclerView recyclerView;
    AdocaoAdapter adocaoAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adotar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        animais = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("Adocao").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dn:snapshot.getChildren()){
                        String uid = dn.getKey();
                        NovoLarModel u = dn.getValue(NovoLarModel.class);
                        u.setID(uid);
                        animais.add(u);
                    }
                    adocaoAdapter = new AdocaoAdapter(animais);
                    recyclerView.setAdapter(adocaoAdapter);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}