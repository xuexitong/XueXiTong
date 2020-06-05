package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import static com.example.myapplication.login.LoginByPost;
public class MainActivity extends AppCompatActivity {
    private Button a;
    private Button b;
    private EditText passwd;
    private EditText number;
    String k="1";
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a = (Button) findViewById(R.id.b);
        b=(Button)findViewById(R.id.b2) ;
        passwd = (EditText) findViewById(R.id.t1);
        number = (EditText) findViewById(R.id.t2);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nethttp();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                it.setClass(MainActivity.this, regis.class);
                MainActivity.this.startActivity(it);
            }
        });
    }
    private void nethttp()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
               try{

                   OkHttpClient c=new OkHttpClient();
                   FormBody formBody = new FormBody.Builder()
                           .add("type","login")
                           .add("username", number.getText().toString())
                           .add("password",passwd.getText().toString())
                           .build();
                   Request r=new Request.Builder().url("http://10.0.2.2:8080/T/tp/hello").post(formBody).build();
                   Response re=c.newCall(r).execute();

                   String result=re.body().string();
                   show(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void show(final String r)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(r.equals("yes")) {
                Toast.makeText(getApplicationContext(),"登录成功", Toast.LENGTH_SHORT).show(); ;

                   /* Intent it=new Intent();
                    it.setClass(MainActivity.this, 登录之后的界面);
                     MainActivity.this.startActivity(it);*/
                }
                else if(r.equals("no"))
                    Toast.makeText(getApplicationContext(),"登录失败！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"服务器故障", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
