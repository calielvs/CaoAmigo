package com.example.cao_amigo.activity;

import com.google.firebase.auth.FirebaseAuth;

public class ConBD {

    private static FirebaseAuth auth;

    public static FirebaseAuth FirebaseAutenticacao(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
