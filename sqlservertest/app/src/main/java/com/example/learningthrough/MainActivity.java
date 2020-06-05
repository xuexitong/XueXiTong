package com.example.learningthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private Button mBtnRegister;
    private Button mBtnLogin;
    private EditText password;
    private EditText username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnRegister=findViewById(R.id.btn_Register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到Register界面
               Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                 startActivity(intent);
            }
        });

        //登陆
        username=findViewById(R.id.et_1);
        password=findViewById(R.id.et_2);
        mBtnLogin=findViewById(R.id.btn_Login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nethttp();
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
                            .add("username",username.getText().toString())
                            .add("password",password.getText().toString())
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
                    //Toast.makeText(getApplicationContext(),"登录成功", Toast.LENGTH_SHORT).show(); ;
                    //跳转到Menu界面
                    Intent intent = new Intent(MainActivity.this,Menu.class);
                    startActivity(intent);
                    //显示登入成功
                    Toast.makeText(MainActivity.this,"登入成功！",Toast.LENGTH_SHORT).show();
                }
                else if(r.equals("no"))
                    Toast.makeText(getApplicationContext(),"登录失败！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"服务器故障", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
