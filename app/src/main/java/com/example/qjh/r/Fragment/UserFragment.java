package com.example.qjh.r.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.qjh.r.Bean.User;
import com.example.qjh.r.R;
import com.example.qjh.r.Activity.UserMessageActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private static final int SUCCESS = 1;
    private View view;
    private TextView Enter_name;
    private TextView Enter_number;
    private CircleImageView Head_image;
    private FrameLayout MSG_Enter;//信息界面
    private RelativeLayout About_more;
    public SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    Enter_name.setText("用户名:"+user.getName());
                    Enter_number.setText("学号:"+user.getNumber());
                    if (user.getImage() != null) {
                        Glide.with(getContext()).load(user.getImage().getFileUrl()).into(Head_image);
                    }

                    refreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    private User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.light));
        Init();

        return view;
    }

    private void Init() {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Refreshs);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Fresh();

            }
        });
        About_more = (RelativeLayout) view.findViewById(R.id.About_more);
        About_more.setOnClickListener(this);
        MSG_Enter = (FrameLayout) view.findViewById(R.id.MSG_Enter);
        Head_image = (CircleImageView) view.findViewById(R.id.message_image_total);
        Enter_name = (TextView) view.findViewById(R.id.Enter_name);
        Enter_number = (TextView) view.findViewById(R.id.Enter_number);
        MSG_Enter.setOnClickListener(this);
        Head_image = (CircleImageView) view.findViewById(R.id.message_image_total);
        Fresh();


    }

    private void Fresh() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {

                    user = list.get(0);
                    handler.sendEmptyMessage(SUCCESS);
                    // Enter_name.setText(user.getName());
                    // Enter_number.setText(user.getNumber());
                    // Glide.with(getContext()).load(user.getImage().getFileUrl()).into(Head_image);

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Fresh();  //重新获取数据
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MSG_Enter:
                Intent intent = new Intent(getContext(), UserMessageActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.About_more:
                Snackbar.make(view, "谢谢使用", Snackbar.LENGTH_SHORT).show();

                break;
        }
    }
}
