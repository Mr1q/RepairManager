package com.example.qjh.r.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.qjh.r.Adapter.First_Adapter;
import com.example.qjh.r.Login.User;
import com.example.qjh.r.Main.Message_Bomb;
import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.R;
import com.example.qjh.r.Receiver.VPM;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment2 extends Fragment implements View.OnClickListener {
    private View view;
    private ImageButton repair;//维修
    private EditText search;
    public static ArrayList<Message_Bomb> message_bombs_list;
    private RecyclerView recyclerView;
    public static First_Adapter fruit_adapter;
    public static User user = BmobUser.getCurrentUser(User.class);
    public static int Numbwe; //实时个数
    private SwipeRefreshLayout refreshLayout; //下拉刷新

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first, container, false);
        init();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        return view;
    }

    public static void set(ArrayList<Message_Bomb> message_bombs) {
        message_bombs_list = message_bombs;
    }

    public void init() {
        search=view.findViewById(R.id.search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH)
                {
                    if(!search.getText().toString().isEmpty()) {
                        BmobQuery<Message_Bomb> query = new BmobQuery<>();
                        query.addWhereEqualTo("title", search.getText());
                        query.findObjects(new FindListener<Message_Bomb>() {
                            @Override
                            public void done(List<Message_Bomb> list, BmobException e) {
                                if (e == null) {
                                    Find(list);
                                }
                            }
                        });
                    }
                }
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!search.getText().toString().isEmpty())
                {
                    BmobQuery<Message_Bomb> query = new BmobQuery<>();
                    query.addWhereEqualTo("title", search.getText());
                    query.findObjects(new FindListener<Message_Bomb>() {
                        @Override
                        public void done(List<Message_Bomb> list, BmobException e) {
                            if (e == null) {
                                Find(list);
                            }
                        }
                    });
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(search.getText().toString().isEmpty())
                    {
                        BmobQuery<Message_Bomb> query = new BmobQuery<>();
                        query.addWhereEqualTo("idd", user.getObjectId());
                        query.findObjects(new FindListener<Message_Bomb>() {
                            @Override
                            public void done(List<Message_Bomb> list, BmobException e) {
                                if (e == null) {
                                    Find(list);
                                }
                            }
                        });
                        search.clearFocus();
                        InputMethodManager inputMethodManager=(InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if(inputMethodManager!=null)
                        {
                            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                        }
                    }

            }
        });
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Refresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Fresh();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.first_msg);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        message_bombs_list = new ArrayList<>();
        BmobQuery<Message_Bomb> query = new BmobQuery<>();
        query.addWhereEqualTo("idd", user.getObjectId());
        query.findObjects(new FindListener<Message_Bomb>() {
            @Override
            public void done(List<Message_Bomb> list, BmobException e) {
                if (e == null) {
                    Find(list);

                }
            }
        });
        repair = (ImageButton) view.findViewById(R.id.repair);
        repair.setOnClickListener(this);
    }

    private void Find(List<Message_Bomb> list) {
        message_bombs_list.clear();
        for (Message_Bomb msg : list) {
            message_bombs_list.add(new Message_Bomb(msg.getTitle(), msg.getObj_Name(), msg.getLocation(), msg.getNumber(), msg.getName(), msg.getTime(), msg.getArea1(), msg.getArea2(), msg.getMsg(), msg.getPhone(), msg.getPicture(), msg.getObjectId()));
        }
        VPM.SetBadgeNum(message_bombs_list.size());
        fruit_adapter = new First_Adapter(message_bombs_list);
        recyclerView.setAdapter(fruit_adapter);
        fruit_adapter.setOnItemClickListener(new First_Adapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), Repair.class);
                Message_Bomb message_bomb = message_bombs_list.get(position);
                intent.putExtra("0", message_bomb.getTitle());
                intent.putExtra("1", message_bomb.getArea1());
                // intent.putExtra("2",message_bomb.getArea2());
                intent.putExtra("3", message_bomb.getLocation());
                intent.putExtra("4", message_bomb.getObj_Name());
                intent.putExtra("5", message_bomb.getMsg());
                intent.putExtra("6", message_bomb.getNumber());
                intent.putExtra("7", message_bomb.getName());
                intent.putExtra("8", message_bomb.getPhone());
                intent.putExtra("9", message_bomb.getTime());
                intent.putExtra("10", message_bomb.getPicture_uri());
                intent.putExtra("id", message_bomb.getSelf());
                intent.putExtra("Where", true);
                startActivity(intent);
            }
        });
    }

    private void Fresh() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Message_Bomb> query = new BmobQuery<>();
                query.addWhereEqualTo("idd", user.getObjectId());
                query.findObjects(new FindListener<Message_Bomb>() {
                    @Override
                    public void done(List<Message_Bomb> list, BmobException e) {
                        if (e == null) {
                            Find(list);
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });
                fruit_adapter.notifyDataSetChanged();

            }
        });
    }

    public static int getnumber() {
        return message_bombs_list.size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repair:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("报修须知");
                builder.setMessage("请仔细阅读如下须知:\n" +
                        "提示:为了您的报修及时得到处理，请自行阅读如下须知，判断您要报修的问题属于以下哪类问题，谢谢您的配合\n" +
                        "五金:下水道堵、厕所堵门锁坏、窗、桌椅，洗手盆下水管漏水；天花板坏\n" +
                        "水:水笼头漏水、冲水阀坏、水管漏水\n" +
                        "电:灯管灯架坏、风扇或开关坏、电路跳闸、电路短路、走廊路灯\n" +
                        "网络故障:请直接电话报修，花江校\n" +
                        "区:2290739，金鸡岭校区:2291549\n" +
                        "饮水机故障:请直接电话报修，联系电\n" +
                        "话:13607734136\n" +
                        "提示:为了让维修师傅更好地了解现场情况，请您在报修时，拍摄现场照片并上传，谢谢您的配合");
                builder.setPositiveButton("点击报修", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity().getApplicationContext(), Repair.class);
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
