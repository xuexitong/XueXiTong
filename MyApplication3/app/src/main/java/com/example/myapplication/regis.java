package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class regis extends AppCompatActivity {
    private Button a;
    private Button b;
    private EditText p;
    private EditText n;
    private EditText n2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       // View view = View.inflate( this, R.layout.activity_main2, null);
        a = (Button) findViewById(R.id.b);
        b=(Button)findViewById(R.id.b2) ;
        p=(EditText)findViewById(R.id.editText);
        n=(EditText)findViewById(R.id.editText2);
        n2=(EditText)findViewById(R.id.editText3);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                netregis();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                it.setClass(regis.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
    private void netregis()
    {
        if(!n.getText().toString().equals(n2.getText().toString()))
            Toast.makeText(getApplicationContext(),"前后密码不一致！", Toast.LENGTH_SHORT).show();
        else if(p.getText().toString().equals("")||n.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(),"用户名或密码不得为空！", Toast.LENGTH_SHORT).show();
        else
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{

                        OkHttpClient c=new OkHttpClient();
                        FormBody formBody = new FormBody.Builder()
                                .add("type","regis")
                                .add("username", p.getText().toString())
                                .add("password",n.getText().toString())
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
    }
    private void show(final String r)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(r.equals("yes")) {
                    Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show(); ;
                }
                else if(r.equals("rename"))
                    Toast.makeText(getApplicationContext(),"用户名重复！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"服务器故障", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
