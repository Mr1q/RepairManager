package com.example.qjh.r.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.support.v7.widget.Toolbar;

import com.example.qjh.r.Loc;
import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.MyApplication;
import com.example.qjh.r.R;

import Control.ActivityCollector;
import Control.BaseActivity;

public class Fragment1 extends Fragment {
    private ScrollView scrollView;
    private  FrameLayout location; //定位按钮
    private  View view;
    private ImageButton frameLayout;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.back:
               ActivityCollector.Finish_All();
               break;
       }

        return true;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menui,menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.message,container,false);
        scrollView=(ScrollView)view.findViewById(R.id.scrollView);
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("维修导航");
        location=(FrameLayout)view.findViewById(R.id.location);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), Loc.class);
                startActivity(intent);

            }
        });
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        frameLayout=(ImageButton)view.findViewById(R.id.repair);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Repair.class);
                startActivity(intent);
            }
        });
        return  view;

    }
}
