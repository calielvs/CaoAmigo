package com.example.cao_amigo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cao_amigo.ActivityListarAdocaoUser;
import com.example.cao_amigo.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = ConBD.FirebaseAutenticacao();
        user = findViewById(R.id.UserLogado);
        userLogin();
    }

    public void userLogin(){
        user.setText(auth.getCurrentUser().getEmail());
    }

    public void deslogar(View view){
        try{
            auth.signOut();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Pegar o usuario que está logado
    public void teste(View view){
        Toast.makeText(this, auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

    }
    public void telaAdotar(View v){
        Intent i = new Intent(this,AdotarActivity.class);
        startActivity(i);
    }

    public void telaNovoLar(View v){
        Intent i = new Intent(this,NovoLarActivity.class);
        startActivity(i);
    }

    public void telaAdocaoUser(View v){
        Intent i = new Intent(this, ActivityListarAdocaoUser.class);
        startActivity(i);
    }


}