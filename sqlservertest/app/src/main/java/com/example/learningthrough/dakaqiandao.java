package com.example.learningthrough;



import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class dakaqiandao extends AppCompatActivity implements GridView.OnItemClickListener {
    private GridView registration_calendar_gv;
    private TextView today;
    private RegistrationAdapter adapter;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    int mYear=0;//年
    int mMonth=0;//月
    int mDay=0;//日
    int nMonth=0;//实际月

    String dbURL="jdbc:jtds:sqlserver://192.168.1.4:1433;DatabaseName=learnthrough";//数据库连接url
    String userName="sa";//数据库用户名
    String userPwd="wyb990224";//数据库密码
    Connection con;
    //private Handler handler;
    //private String eng_str,chs_str;
    //private String now_id;
    //private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dakaqiandao);
        //View view= this.getLayoutInflater().inflate((R.layout.activity_dakaqiandao), null);
        registration_calendar_gv=(GridView)findViewById(R.id.registration_calendar_gv);
        //GridView gridView= view.findViewById(R.id.registration_calendar_gv);
        today=(TextView)findViewById(R.id.today);

        Calendar calendar=Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR); // 获取当前年份
        mMonth = calendar.get(Calendar.MONTH) ;// 获取当前月份以（0开头）
        mDay = calendar.get(Calendar.DAY_OF_MONTH) ;// 获取当前天以（0开头）

        SpecialCalendar mCalendar=new SpecialCalendar();
        boolean isLeapYear =mCalendar.isLeapYear(mYear);
        int mDays=mCalendar.getDaysOfMonth(isLeapYear,mMonth+1);
        int week =mCalendar.getWeekdayOfMonth(mYear,mMonth);
        int nMonth=mMonth+1;

        adapter=new RegistrationAdapter(this,mDays,week,mDay,mMonth,mYear);
        registration_calendar_gv.setAdapter(adapter);
        registration_calendar_gv.setOnItemClickListener(this);
        today.setText(mYear+"年"+nMonth+"月"+mDay+"日");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int m=mMonth+1;
        String today=mYear+"-"+m+"-"+l;
        if (l!=0){
            if (l==mDay){
                TextView today_tv= (TextView) view.findViewById(R.id.day);
                today_tv.setBackgroundResource(R.mipmap.member_ok);
                today_tv.setTextColor(Color.BLACK);
                today_tv.setText(l+"");
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                Toast.makeText(view.getContext(),"签到成功",Toast.LENGTH_SHORT).show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        init();//初始化
                    }


                });
                thread.start();
            }else{
                Toast.makeText(view.getContext(),"您选择的日期："+today,Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void init(){

        try{
            int x=mMonth+1;
            con= DriverManager.getConnection(dbURL,userName,userPwd);
            String sql="insert into record values('"+mYear+"','"+x+"','"+mDay+"')";
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
