package com.example.qjh.r.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.qjh.r.Fragment.Fragment2;
import com.example.qjh.r.Main.Message_Bomb;
import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.MyApplication;
import com.example.qjh.r.R;
import com.example.qjh.r.Receiver.VPM;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class First_Adapter extends RecyclerView.Adapter<First_Adapter.ViewHolder> {

    private List<Message_Bomb> message_bombs;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public First_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.first_msg, viewGroup, false);
        final First_Adapter.ViewHolder viewHolder = new First_Adapter.ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Message_Bomb message_bomb = (Message_Bomb) message_bombs.get(position);
                onItemClickListener.onClick(position);

            }
        });
        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(view,"是否删除数据",Snackbar.LENGTH_SHORT).setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAdapterPosition();
                        Message_Bomb message_bombss=new Message_Bomb();
                        message_bombss.setObjectId(message_bombs.get(position).getSelf());
                        message_bombss.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null)
                                {
                                    Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        removeItem(position);
                        VPM.SetBadgeNum(message_bombs.size());
                    }
                }).show();


                return true;
            }
        });

        return viewHolder;
    }

    public First_Adapter(List<Message_Bomb> message_bombs) {
        this.message_bombs = message_bombs;
    }

    public void removeItem(int pos) {
        message_bombs.remove(pos);
        notifyItemRemoved(pos);
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull final First_Adapter.ViewHolder viewHolder, int i) {
        Message_Bomb msg = message_bombs.get(i);
        viewHolder.Title_Front.setText(msg.getTitle());
        viewHolder.Obj_Front.setText(msg.getObj_Name());
        viewHolder.number_Front.setText(msg.getNumber());
        viewHolder.name_Front.setText(msg.getName());
        viewHolder.time_Front.setText(msg.getTime());
        viewHolder.locaton_Front.setText(msg.getLocation());
        Glide.with(viewHolder.view.getContext()).load(msg.getPicture_uri()).into(viewHolder.picture);



    }

    public void addData(int position) {

    }

    @Override
    public int getItemCount() {
        return message_bombs.size();
    }


    /*
    内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title_Front; //标题
        TextView Obj_Front;  //项目名称
        TextView number_Front; //学号
        TextView name_Front; //姓名
        TextView time_Front; //预约时间
        TextView locaton_Front; //位置
        ImageView picture; //图片
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title_Front = (TextView) itemView.findViewById(R.id.title_front);
            Obj_Front = (TextView) itemView.findViewById(R.id.obj_front);
            number_Front = (TextView) itemView.findViewById(R.id.number_front);
            name_Front = (TextView) itemView.findViewById(R.id.name_front);
            time_Front = (TextView) itemView.findViewById(R.id.time_front);
            locaton_Front = (TextView) itemView.findViewById(R.id.Location_front);
            picture=(ImageView)itemView.findViewById(R.id.image_front);
            view = itemView;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
