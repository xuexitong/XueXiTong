package com.example.learningthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonalActivity extends AppCompatActivity {
    private TextView user,school,finish,begin;
    String dbURL="jdbc:jtds:sqlserver://192.168.1.4:1433;DatabaseName=learnthrough";//数据库连接url
    String userName="sa";//数据库用户名
    String userPwd="wyb990224";//数据库密码
    private int count_finish,count_begin;
    private Handler handler;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        user=(TextView)findViewById(R.id.et_user);
        school=(TextView)findViewById(R.id.et_school);
        finish=(TextView)findViewById(R.id.et_finish);
        begin=(TextView)findViewById(R.id.et_begin);
        count_finish=0;
        count_begin=0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                init_1();//初始化
                init_2();//初始化
                //init_3();//初始化

            }
        });
        thread.start();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        System.out.println("==>"+ count_finish);
                        finish.setText(count_finish+"");
                        begin.setText(count_begin+"");
                        break;
                    case 2:
                        begin.setText(count_begin+"");
                        break;
                }
            }
        };
    }
    private void init_1() {
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            String sql="select count(*)  from display where isok=1 " ;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            count_finish=rs.getInt(1);
            //System.out.println("==>" + count_finish);
            //System.out.println("==>" + count_begin);
            Message m = new Message();
            m.what = 1;
            handler.sendMessage(m);
            rs.close();
            st.close();
            con.close();
        }catch(Exception e){
            Message m = new Message();
            m.what = 3;
            handler.sendMessage(m);
            e.printStackTrace();
        }
    }
    private void init_2() {
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            String sql="select count(*)  from display where isok=0 " ;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            count_begin=rs.getInt(1);
            Message m = new Message();
            m.what = 2;
            handler.sendMessage(m);
            rs.close();
            st.close();
            con.close();
        }catch(Exception e){
            Message m = new Message();
            m.what = 3;
            handler.sendMessage(m);
            e.printStackTrace();
        }
    }
    /*private void init_3() {
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            String sql="select   from display where isok=0 " ;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            count_begin=rs.getInt(1);
            Message m = new Message();
            m.what = 2;
            handler.sendMessage(m);
            rs.close();
            st.close();
            con.close();
        }catch(Exception e){
            Message m = new Message();
            m.what = 3;
            handler.sendMessage(m);
            e.printStackTrace();
        }
    }  */
    }
