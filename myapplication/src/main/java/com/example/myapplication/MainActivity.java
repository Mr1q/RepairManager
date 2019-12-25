package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
     private TextView textView;
     private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            std stds=std.class.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        textView=(TextView)findViewById(R.id.text);
        button=(Button) findViewById(R.id.buttonPanel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }

    private void send() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                BufferedReader bufferedReader=null;
                try
                {
                    URL url=new URL("https://www.baidu.com");
                    httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    InputStream inputStream=httpURLConnection.getInputStream();
                    bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    final StringBuilder stringBuilder=new StringBuilder();
                    String msg;
                    while((msg=bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(msg);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(stringBuilder.toString());
                        }
                    });

                }catch (Exception E)
                {
                    E.printStackTrace();
                }
                finally {
                    if(bufferedReader!=null)
                    {
                        try {
                            bufferedReader.close();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    if(httpURLConnection!=null)
                    {
                        try {
                            httpURLConnection.disconnect();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                }


            }
        }).start();
    }
}
class std
{
    public std()
    {

    }

}
