package com.example.rpl.hobi_kita_rev;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


//class adapter untuk row pada comment list
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    //deklarasi variable
    private List<CommentModel> commentList;

    //class viewholder untuk declare dan inisialisasi views pada row yang digunakan
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvComment;
        public ImageView imgAvatar;

        public MyViewHolder(View view) {
            super(view);
            imgAvatar = view.findViewById(R.id.imgAvatar);
            tvName = view.findViewById(R.id.tvNama);
            tvComment = view.findViewById(R.id.tvKomentar);
        }
    }

    //konstruktor untuk menerima data yang dikirimkan dari activity ke adapter
    public CommentAdapter(List<CommentModel> commentList) {
        this.commentList = commentList;
    }

    //create ke layout row yang dipilih
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    //binding antara data yang didapatkan ke dalam views
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CommentModel model = commentList.get(position);
        holder.tvComment.setText(model.getComment());
        holder.tvName.setText(model.getName());
    }

    //count data
    @Override
    public int getItemCount() {
        return commentList.size();
    }
}