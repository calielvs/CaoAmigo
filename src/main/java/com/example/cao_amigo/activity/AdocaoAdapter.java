package com.example.cao_amigo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import java.net.URI;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cao_amigo.R;
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
    ImageView iv;


    public AdocaoAdapter(List<NovoLarModel> adocao) {
        this.adocao = adocao;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adocao,parent,false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        //Picasso.with().load(animais.getUriImagem()).into(vhClass.urlImg);
        return vhClass;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        NovoLarModel animais = adocao.get(position);
        vhClass.tvNome.setText(animais.getNomeAnimal());
        vhClass.tvRaca.setText(animais.getRacaAnimal());
        vhClass.tvIdade.setText(animais.getIdade());
        vhClass.teste.setText(animais.getUriImagem());
        //Picasso.with().load(animais.getUriImagem()).into(vhClass.urlImg);
        //vhClass.imgUriT = animais.getUriImagem();

        //Teste de download 1
        //vhClass.urlImg.setImageBitmap(getBitmapFromURL("https://firebasestorage.googleapis.com/v0/b/caoamigo-54d9d.appspot.com/o/TestebtnFoto_1_1.JPEG?alt=media&token=544ed784-9cec-4883-94d7-98a930ad860d"));
        //Teste Download 2
        //downloadfile(animais.getUriImagem(),vhClass.urlImg);


    }



    @Override
    public int getItemCount() {
        return adocao.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        //Por enquanto nada de errado aqui, talvez
        TextView tvNome,tvRaca,tvIdade,teste;
        ImageView urlImg;
        //String imgUriT;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.txtvNome);
            tvRaca = itemView.findViewById(R.id.txtvRaca);
            tvIdade = itemView.findViewById(R.id.txtvIdade);
            teste = itemView.findViewById(R.id.testeURI);
            urlImg = itemView.findViewById(R.id.imgView);
            //imgUriT = null;
        }
    }
    //Teste de download 1
    /*public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }*/

    //Teste de download 2
    /*public static void downloadfile(String fileurl, ImageView img) {
        Bitmap bmImg = null;
        URL myfileurl = null;
        try {
            myfileurl = new URL(fileurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myfileurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength();
            if (length > 0) {
                int[] bitmapData = new int[length];
                byte[] bitmapData2 = new byte[length];
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
                img.setImageBitmap(bmImg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
