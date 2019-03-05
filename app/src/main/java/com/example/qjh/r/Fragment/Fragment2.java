package com.example.qjh.r.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.qjh.r.Adapter.First_Adapter;
import com.example.qjh.r.Main.Message_Bomb;
import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.R;

import java.util.ArrayList;

public class Fragment2 extends Fragment implements  View.OnClickListener{
    private  View view;
    private ImageButton repair;//维修
     public   static  ArrayList<Message_Bomb> fruitList;
    private RecyclerView recyclerView;
    public static First_Adapter fruit_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.first,container,false);
        init();


        return  view;
    }

   public  static void set(ArrayList<Message_Bomb> message_bombs)
   {
       fruitList=message_bombs;
   }
    public  void init()
    {
         fruitList=new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            Message_Bomb fruit1=new Message_Bomb("家电","水","xxxx","xxx","xxx","","12");
            fruitList.add(fruit1);
            Message_Bomb fruit2=new Message_Bomb("12sad","电路","12ds","12","12","12","12");
            fruitList.add(fruit2);
        }
         recyclerView=(RecyclerView)view.findViewById(R.id.first_msg);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
         fruit_adapter=new First_Adapter(fruitList);
        fruit_adapter.setOnItemClickListener(new First_Adapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getContext(),Repair.class);
                Message_Bomb message_bomb=fruitList.get(position);
                intent.putExtra("0",message_bomb.getTitle());
                intent.putExtra("1",message_bomb.getNumber());
                intent.putExtra("2",message_bomb.getName());
                intent.putExtra("3",message_bomb.getTime());
                intent.putExtra("4",message_bomb.getMsg());


                startActivity(intent);
            }
        });
        recyclerView.setAdapter(fruit_adapter);
        repair=(ImageButton)view.findViewById(R.id.repair);
        repair.setOnClickListener(this);
    }

        public  static int getnumber()
        {
            return fruitList.size();
        }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.repair:
                AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                builder.setTitle("报修须知" ) ;
                builder.setMessage("请仔细阅读如下须知:\n" +
                        "提示:为了您的报修及时得到处理，请自行阅读如下须知，判断您要报修的问题属于以下哪类问题，谢谢您的配合\n" +
                        "五金:下水道堵、厕所堵门锁坏、窗、桌椅，洗手盆下水管漏水；天花板坏\n" +
                        "水:水笼头漏水、冲水阀坏、水管漏水\n" +
                        "电:灯管灯架坏、风扇或开关坏、电路跳闸、电路短路、走廊路灯\n" +
                        "网络故障:请直接电话报修，花江校\n" +
                        "区:2290739，金鸡岭校区:2291549\n" +
                        "饮水机故障:请直接电话报修，联系电\n" +
                        "话:13607734136\n" +
                        "提示:为了让维修师傅更好地了解现场情况，请您在报修时，拍摄现场照片并上传，谢谢您的配合") ;
                builder.setPositiveButton("点击报修", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent=new Intent(getActivity().getApplicationContext(), Repair.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();


                break;
        }
    }
}
