package com.example.cao_amigo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cao_amigo.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = ConBD.FirebaseAutenticacao();
    }

    public void deslogar(View view){
        try{
            auth.signOut();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void telaAdotar(View v){
        Intent i = new Intent(this,AdotarActivity.class);
        startActivity(i);
    }

    public void telaNovoLar(View v){
        Intent i = new Intent(this,NovoLarActivity.class);
        startActivity(i);
    }


}