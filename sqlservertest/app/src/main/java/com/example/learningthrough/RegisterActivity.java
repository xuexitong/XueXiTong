package com.example.learningthrough;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.service.autofill.Validator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button a;
    private Button b;
    private EditText username;
    private EditText password;
    private EditText password2;
    private EditText school;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        a = (Button) findViewById(R.id.btn_register);
        b=(Button)findViewById(R.id.btn_return) ;
        username=(EditText)findViewById(R.id.et_user);
        password=(EditText)findViewById(R.id.et_password);
        password2=(EditText)findViewById(R.id.et_password2);
        school=(EditText)findViewById(R.id.et_school);
        phone=(EditText)findViewById(R.id.et_phone);
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
                it.setClass(RegisterActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
    private void netregis()
    {
        if(!password.getText().toString().equals(password2.getText().toString()))
            Toast.makeText(getApplicationContext(),"前后密码不一致！", Toast.LENGTH_SHORT).show();
        else if(username.getText().toString().equals("")||password.getText().toString().equals(""))
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
                                .add("username", username.getText().toString())
                                .add("password",password.getText().toString())
                                .add("school",school.getText().toString())
                                .add("phone",phone.getText().toString())
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
