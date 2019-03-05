package com.example.qjh.r.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qjh.r.Login.Login;
import com.example.qjh.r.Login.User;
import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.R;

import java.util.Calendar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/*

页面功能：显示用户信息
 */
public class Fragment3 extends Fragment implements View.OnClickListener {
    private View view;
    private Button save;
    private Button modify;
    private ImageButton over;
    private Button riqi;
    private EditText username2;
    private EditText mail_1;
    private EditText address;
    private RadioGroup radioGroup;
    private RadioButton male;
    private RadioButton female;

    private  User user=BmobUser.getCurrentUser(User.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_message, container, false);
        init();
        return view;
    }

    public void init() {
        male=(RadioButton)view.findViewById(R.id.male);
        female=(RadioButton)view.findViewById(R.id.femle);
        radioGroup=(RadioGroup)view.findViewById(R.id.rg);
        username2=(EditText)view.findViewById(R.id.username2);
        mail_1=(EditText)view.findViewById(R.id.mail_1);
        address=(EditText)view.findViewById(R.id.address);
        riqi = (Button) view.findViewById(R.id.riqi);
        riqi.setOnClickListener(this);
        over = (ImageButton) view.findViewById(R.id.over);
        over.setOnClickListener(this);
        save = (Button) view.findViewById(R.id.save);
        modify = (Button) view.findViewById(R.id.modify);
        modify.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify:
                save.setVisibility(View.VISIBLE);
                modify.setVisibility(View.INVISIBLE);
                username2.setEnabled(true);
                mail_1.setEnabled(true);
                address.setEnabled(true);
                male.setEnabled(true);
                female.setEnabled(true);
                riqi.setEnabled(true);
                break;
            case R.id.save:
                save.setVisibility(View.INVISIBLE);
                modify.setVisibility(View.VISIBLE);
                username2.setEnabled(false);
                mail_1.setEnabled(false);
                male.setEnabled(false);
                female.setEnabled(false);
                address.setEnabled(false);
                riqi.setEnabled(false);
                Save();
                break;
            case R.id.over:
                BmobUser.logOut();
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.riqi:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

                        riqi.setText(year + "-" + (month + 1) + "-" + dayOfMonth + " ");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


                break;
        }
    }

    private void Save() {

        user.setUsername(username2.getText().toString());
        user.setAddress(address.getText().toString());
        user.setRiqi(riqi.getText().toString());
        user.setMail(mail_1.getText().toString());
        if(radioGroup.getCheckedRadioButtonId()==R.id.male)
        {
            user.setSex("男");
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.femle)
        {
            user.setSex("女");
        }
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null)
                {
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
}
