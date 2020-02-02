package com.example.qjh.r.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.qjh.r.Control.ActivityCollector;
import com.example.qjh.r.Loc;
import com.example.qjh.r.Activity.RepairActivity;
import com.example.qjh.r.R;
import com.example.qjh.r.Activity.EvaluateActivity;

public class TotalFragment extends Fragment implements View.OnClickListener {
    private ScrollView scrollView;
    private RelativeLayout location; //定位按钮
    private View view;
    private RelativeLayout frameLayout;
    private RelativeLayout evaluate;//评价
    private IntentFilter intentFilter;
    private NetChangeBroder netChangeBroder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message, container, false);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        evaluate = (RelativeLayout) view.findViewById(R.id.evaluate);
        evaluate.setOnClickListener(this);
        location = (RelativeLayout) view.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Loc.class);
                startActivity(intent);

            }
        });
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("维修导航");
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        frameLayout = (RelativeLayout) view.findViewById(R.id.repair);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RepairActivity.class);
                startActivity(intent);
            }
        });

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netChangeBroder = new NetChangeBroder();
        getContext().registerReceiver(netChangeBroder, intentFilter);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate:
                Intent intent = new Intent(getContext(), EvaluateActivity.class);
                startActivity(intent);


                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        inflater.inflate(R.menu.menui, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(netChangeBroder);
    }

    class NetChangeBroder extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);  //系统服务类
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
               // Toast.makeText(getContext(), "网络启动", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "未检测到网络", Toast.LENGTH_LONG).show();
            }

        }
    }

}
