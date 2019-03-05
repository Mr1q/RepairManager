package com.example.qjh.r.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.qjh.r.Main.Message_Bomb;
import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.MyApplication;
import com.example.qjh.r.R;

import java.util.List;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Control.ActivityCollector;
import Control.BaseActivity;
import SQlite.Text4;

public class First_Adapter extends RecyclerView.Adapter<First_Adapter.ViewHolder> {

    private List<Message_Bomb> MFruitList;
    private  OnItemClickListener onItemClickListener;
    public First_Adapter(List<Message_Bomb> fruitList) {
        this.MFruitList = fruitList;
    }

    @NonNull
    @Override
    public First_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.first_msg, viewGroup, false);
        final First_Adapter.ViewHolder viewHolder = new First_Adapter.ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Message_Bomb message_bomb=MFruitList.get(position);
                onItemClickListener.onClick(position);

            }
        });
        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                removeItem(position);
                return true;
            }
        });

        return viewHolder;
    }
    public void removeItem(int pos){
        MFruitList.remove(pos);
        notifyItemRemoved(pos);
    }
    public interface OnItemClickListener{
        void onClick( int position);
    }
    @Override
    public void onBindViewHolder(@NonNull final First_Adapter.ViewHolder viewHolder, int i) {
        Message_Bomb msg = MFruitList.get(i);
        viewHolder.container.setText(msg.getTitle());
        viewHolder.container2.setText(msg.getObj_Name());
        viewHolder.nuMber.setText(msg.getNumber());
        viewHolder.name.setText(msg.getName());
        viewHolder.time.setText(msg.getTime());
        viewHolder.question.setText(msg.getMsg());

    }
    public void addData(int position) {

    }

    @Override
    public int getItemCount() {
        return MFruitList.size();
    }


    /*
    内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView container;
        TextView container2;
        TextView nuMber;
        TextView name;
        TextView time;
        TextView question;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = (TextView) itemView.findViewById(R.id.container);
            container2= (TextView) itemView.findViewById(R.id.container2);
            nuMber = (TextView) itemView.findViewById(R.id.nuMber);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            question=(TextView) itemView.findViewById(R.id.question);
            view=itemView;
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }



}
