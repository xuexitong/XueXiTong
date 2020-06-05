package com.example.learningthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayActivity extends AppCompatActivity {
    private Button RS,BRS,NEXT,RETURN;
    private TextView eng;
    private TextView ch;
    //int i = 0;   //背诵数量
    //int a = 1;   //删除数量
    //String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=JDBCDemo";

    private String startid;
    String dbURL="jdbc:jtds:sqlserver://192.168.1.4:1433;DatabaseName=learnthrough";//数据库连接url
    String userName="sa";//数据库用户名
    String userPwd="wyb990224";//数据库密码
    String tbname = "display";
    String coldata = "id,english,chinese,isok";
    Connection con;
    private Handler handler;
    private String eng_str,chs_str;
    private String now_id;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //初始配置读取
        SharedPreferences sharedPreferences = this.getSharedPreferences("startid", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //初始值
        startid = sharedPreferences.getString("startid", "0");

        eng=(TextView)findViewById(R.id.tv_eng);
        ch=(TextView)findViewById(R.id.tv_ch);
        RS=(Button)findViewById(R.id.btn_RS);
        BRS=(Button)findViewById(R.id.btn_BRS);
        NEXT=(Button)findViewById(R.id.btn_NEXT);
        eng_str = "";
        chs_str = "";

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                init();//初始化
            }


        });
        thread.start();

        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        System.out.println("==>"+ eng_str);
                        eng.setText(eng_str.trim());
                        break;
                    case 2:
                        System.out.println("==>"+ chs_str);
                        eng.setText(eng_str.trim());
                        ch.setText(chs_str.trim());
                        break;
                    case 3:

                        eng.setText("完成");
                        break;
                }
            }
        };

        BRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = new Message();
                m.what = 2;
                handler.sendMessage(m);
            }
        });

        NEXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("startid", now_id);
                editor.commit();



                Intent intent = new Intent(DisplayActivity.this,DisplayActivity.class);

                startActivity(intent);
            }
        });
        RETURN=findViewById(R.id.btn_return);
        RETURN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到Menu界面
                Intent intent = new Intent(DisplayActivity.this,Menu.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        try{

            con= DriverManager.getConnection(dbURL,userName,userPwd);

            String sql="select " + coldata + " from "+tbname+" where isok=0 and id>" + startid;
            System.out.println("==>"+sql);
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql);

            rs.next();
            eng_str = rs.getString(2);
            chs_str = rs.getString(3);
            now_id = rs.getString(1);
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

    public void showToast(View view) {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                isokdata();//初始化
            }


        });
        thread.start();

    }


    private void isokdata() {
        try{

            con= DriverManager.getConnection(dbURL,userName,userPwd);

            String sql="UPDATE  "+tbname+" SET isok = 1 where id=" + now_id;
            System.out.println("==>"+sql);
            Statement st=con.createStatement();
            st.execute(sql);

            st.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
