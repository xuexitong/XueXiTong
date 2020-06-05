package com.example.learningthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    private Button start;
    private Button personal;
    private Button mBtnqiandao;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sharedPreferences = this.getSharedPreferences("startid", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        start=(Button)findViewById(R.id.btn_start);
        personal=(Button)findViewById(R.id.btn_personal);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到展示display界面
                Intent intent = new Intent(Menu.this,DisplayActivity.class);

                startActivity(intent);
            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到展示个人中心界面


                //重置学习ID*/
                //初始配置读取

                editor.putString("startid", "0");
                editor.commit();


                Intent intent = new Intent(Menu.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
        mBtnqiandao=findViewById(R.id.btn_qiandao);
        mBtnqiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到Menu界面
                Intent intent = new Intent(Menu.this,dakaqiandao.class);
                startActivity(intent);
            }
        });

    }
}
