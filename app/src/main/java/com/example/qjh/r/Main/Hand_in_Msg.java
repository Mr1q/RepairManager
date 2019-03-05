package com.example.qjh.r.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qjh.r.R;

import java.security.PublicKey;

import Control.BaseActivity;

public class Hand_in_Msg extends BaseActivity {
    Button sure;
    EditText msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_msg);
        sure=(Button)findViewById(R.id.sure);
        msg=(EditText)findViewById(R.id.msg);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hand_in_Msg.this,Repair.class);
                intent.putExtra("data",msg.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });
    }


}
