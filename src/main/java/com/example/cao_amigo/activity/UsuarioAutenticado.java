package com.example.cao_amigo.activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioAutenticado {

    public static FirebaseUser usuarioLogado(){
        FirebaseAuth usuario = ConBD.FirebaseAutenticacao();
        return usuario.getCurrentUser();
    }
}
