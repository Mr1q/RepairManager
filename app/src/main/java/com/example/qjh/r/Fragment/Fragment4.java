package com.example.qjh.r.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.qjh.r.Login.User;
import com.example.qjh.r.R;
import com.example.qjh.r.UserMessage.User_Enter_msg;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment implements View.OnClickListener {
    private View view;
    private  TextView Enter_name;
    private  TextView Enter_number;
    private CircleImageView Head_image;
    private FrameLayout MSG_Enter;//信息界面
    private  FrameLayout About_more;
    public Fragment4() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.total_3, container, false);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        Window window =  getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.light));
        Init();

        return view;
    }

    private void Init() {
        About_more=(FrameLayout)view.findViewById(R.id.About_more);
        About_more.setOnClickListener(this);
        MSG_Enter=(FrameLayout)view.findViewById(R.id.MSG_Enter);
        Head_image=(CircleImageView)view.findViewById(R.id.message_image_total);
        Enter_name=(TextView)view.findViewById(R.id.Enter_name);
        Enter_number=(TextView)view.findViewById(R.id.Enter_number);
        MSG_Enter.setOnClickListener(this);
        Head_image=(CircleImageView)view.findViewById(R.id.message_image_total);
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null)
                {
                    User user=list.get(0);
                    Enter_name.setText(user.getName());
                    Enter_number.setText(user.getNumber());
                    Glide.with(getContext()).load(user.getImage().getFileUrl()).into(Head_image);

                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.MSG_Enter:
                Intent intent=new Intent(getContext(), User_Enter_msg.class);
                startActivity(intent);
                break;
            case R.id.About_more:
                Snackbar.make(view,"谢谢使用",Snackbar.LENGTH_SHORT).show();

                break;
        }
    }
}
