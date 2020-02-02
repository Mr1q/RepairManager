package com.example.qjh.r.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qjh.r.Adapter.FirstAdapter;
import com.example.qjh.r.Bean.MessageBomb;
import com.example.qjh.r.Bean.User;
import com.example.qjh.r.Control.BaseActivity;
import com.example.qjh.r.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FinishHistoryActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ImageView RTU;
    private final int SUCCESS = 1;
    private final int FAIL = -1;
    private View nullview;
    public static User user = BmobUser.getCurrentUser(User.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_finishhistory);
        initialData();
    }

    private void initialData() {
        nullview = findViewById(R.id.nullview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RTU = findViewById(R.id.RTU);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
    }

    public List<MessageBomb> message_bombList=new ArrayList<>();
    public ArrayList<MessageBomb> msgList=new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
//                    message_bombList.clear();
                    for (MessageBomb messageBomb : message_bombList) {
                        if (messageBomb.getFinish()) {  //已完成
                            msgList.add(new MessageBomb(false, messageBomb.getTitle(),
                                    messageBomb.getObj_Name(),
                                    messageBomb.getLocation(), messageBomb.getNumber(), messageBomb.getName(),
                                    messageBomb.getTime(), messageBomb.getMsg(), messageBomb.getPhone(),
                                    messageBomb.getPicture(), messageBomb.getObjectId()));
                        }
                    }
                    if (msgList.size() == 0) {
                        nullview.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    } else {
                        nullview.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    FirstAdapter firstAdapter = new FirstAdapter(message_bombList, FinishHistoryActivity.this);
                    recyclerView.setAdapter(firstAdapter);
                    firstAdapter.notifyDataSetChanged();
                    break;


            }
        }
    };


    private void getData() {
        message_bombList.clear();
        BmobQuery<MessageBomb> query = new BmobQuery<>();
        query.addWhereEqualTo("idd", user.getObjectId());
        query.findObjects(new FindListener<MessageBomb>() {
            @Override
            public void done(List<MessageBomb> list, BmobException e) {
                if (e == null) {
                    message_bombList = list;
                    handler.sendEmptyMessage(SUCCESS);
                }
            }
        });
    }
}
