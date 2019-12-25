package com.example.qjh.r.Main;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.qjh.r.R;

import Control.BaseActivity;

public class Hand_in_Msg extends BaseActivity {
    Button sure;
    EditText msg;
    ImageButton back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_msg);
        Intent intents=getIntent();
        back=(ImageButton)findViewById(R.id.back_2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sure=(Button)findViewById(R.id.sure);
        msg=(EditText)findViewById(R.id.msg);
        msg.setText(intents.getStringExtra("datas"));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hand_in_Msg.this,Repair.class);
                intent.putExtra("data",msg.getText().toString().trim());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }


}
