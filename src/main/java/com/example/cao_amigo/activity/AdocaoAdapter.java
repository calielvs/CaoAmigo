package com.example.cao_amigo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import java.net.URI;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cao_amigo.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
public class AdocaoAdapter extends RecyclerView.Adapter {


    List<NovoLarModel> adocao;
    String URI;
    String id;
    ImageView iv;
    Context context;
    String UrlImagem;

    public AdocaoAdapter(List<NovoLarModel> adocao) {

        this.adocao = adocao;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adocao,parent,false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        //Adapter não tem context para podermos usar o "This", aqui estou criando uma variavel que está armazenando o Context
        context = parent.getContext();
        return vhClass;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        NovoLarModel animais = adocao.get(position);
        vhClass.tvNome.setText(animais.getNomeAnimal());
        vhClass.tvRaca.setText(animais.getRacaAnimal());
        vhClass.tvIdade.setText(animais.getIdade());
        //vhClass.ID.setText(animais.getID());
        vhClass.peso.setText(animais.getPeso());
        vhClass.endereco.setText(animais.getEndereço());
        vhClass.contato.setText(animais.getContato());
        //Estou armazenando a URL da imagem dentro desta variavel URI para posteriormente utilizar a biblioteca picasso para fazer a exibição
        UrlImagem = animais.getUriImagem();

        //Biblioteca picasso pega a string da imagem e faz o donwload dela em cache e depois seta na ImageView dentro do XML
        //Devo a syntase é Picasso.get().load(URL).into(ImageView);
        Picasso.get().load(UrlImagem).into(vhClass.urlImg);
    }


    @Override
    public int getItemCount() {
        return adocao.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        //Declaração de variaveis para o ViewHolder
        TextView tvNome,tvRaca,tvIdade,ID,peso,endereco,contato;
        ImageView urlImg;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.txtvNome);
            tvRaca = itemView.findViewById(R.id.txtvRaca);
            tvIdade = itemView.findViewById(R.id.txtvIdade);
            urlImg = itemView.findViewById(R.id.imgView);
            //ID = itemView.findViewById(R.id.Id_adocao);
            peso = itemView.findViewById(R.id.txtvPeso);
            endereco = itemView.findViewById(R.id.txtvEnd);
            contato = itemView.findViewById(R.id.txtvCont);
        }
    }





}
