package com.example.cao_amigo.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cao_amigo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListarAdocaoUserAdapter extends RecyclerView.Adapter{

    List<NovoLarModel> adocao;
    String URI;
    ImageView iv;
    String UrlImagem;

    public ListarAdocaoUserAdapter(List<NovoLarModel> adocao) {
        this.adocao = adocao;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adocao_user,parent,false);
        ListarAdocaoUserAdapter.ViewHolderClass vhClass = new ListarAdocaoUserAdapter.ViewHolderClass(view);
        return vhClass;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListarAdocaoUserAdapter.ViewHolderClass vhClass = (ListarAdocaoUserAdapter.ViewHolderClass) holder;
        NovoLarModel animais = adocao.get(position);
        vhClass.tvNome.setText(animais.getNomeAnimal());
        vhClass.tvRaca.setText(animais.getRacaAnimal());
        vhClass.tvIdade.setText(animais.getIdade());
        vhClass.user.setText(animais.getEmailUser());
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
        TextView tvNome,tvRaca,tvIdade,user;
        ImageView urlImg;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.txtvNomeAU);
            tvRaca = itemView.findViewById(R.id.txtvRacaAU);
            tvIdade = itemView.findViewById(R.id.txtvIdadeAU);
            urlImg = itemView.findViewById(R.id.imgViewAU);
            user = itemView.findViewById(R.id.User);
        }
    }
}
