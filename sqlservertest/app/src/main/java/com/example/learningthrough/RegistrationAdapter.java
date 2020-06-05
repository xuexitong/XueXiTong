package com.example.learningthrough;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrationAdapter extends BaseAdapter {
    private Context context;
    private final int days;
    private final int week;
    private int[] dayNumber;
    private final int day;
    private final int month;
    private final int year;

    private String startid;
    String dbURL="jdbc:jtds:sqlserver://192.168.1.4:1433;DatabaseName=learnthrough";//数据库连接url
    String userName="sa";//数据库用户名
    String userPwd="wyb990224";//数据库密码
    String tbname = "display";
    String coldata = "id,english,chinese,isok";
    Connection con;
    private Handler handler;


    public RegistrationAdapter(Context context, int days, int week,int day,int month,int year) {
        this.context = context;
        this.days = days;
        this.week = week;
        this.day=day;
        this.month=month;
        this.year=year;

        getEveryDay();
    }



    @Override
    public int getCount() {
        return 42;
    }

    @Override
    public String getItem(int i) {

        return null;
    }

    @Override
    public long getItemId(int i) {
        return dayNumber[i];
    }//点击时

    private ViewHolder viewHolder;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null==view){
            view= LayoutInflater.from(context).inflate(R.layout.item_registrationadatper,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.day.setText(dayNumber[i]==0? "" : dayNumber[i]+"");

       // if (dayNumber[i]!=0&&dayNumber[i]<day&&dayNumber[i]%2==1){
           // viewHolder.day.setBackgroundResource(R.mipmap.member_ok);
       // }
       /* while(dayNumber[i]!=0&&dayNumber[i]<=day){
            String sql="select * from record where year='"+year+"' and month='"+month+"' and day='"+dayNumber[i]+"' ";
            /*int k=init2(sql);
            if(k==1){
                viewHolder.day.setBackgroundResource(R.mipmap.member_ok);
            }
            try{
                con= DriverManager.getConnection(dbURL,userName,userPwd);
                Statement st=con.createStatement();
                ResultSet rs= st.executeQuery(sql);
                if (rs.next()) {
                    viewHolder.day.setBackgroundResource(R.mipmap.member_ok);
                }
                st.close();
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        } */
        if (dayNumber[i]==day){
            viewHolder.day.setText("今");
            view.setBackgroundResource(R.color.colorPrimary);
            viewHolder.day.setTextColor(Color.parseColor("#ffffff"));
        }

        return view;
    }

    private class ViewHolder{
        private TextView day;

        public ViewHolder(View view) {
            this.day = (TextView) view.findViewById(R.id.day);
        }
    }

    /**
     * 得到42格子 每一格子的值
     */
    private void getEveryDay(){
        dayNumber=new int[42];

        for (int i=0;i<42;i++){
            if (i < days+week && i >= week){
                dayNumber[i]=i-week+1;
            }else{
                dayNumber[i]=0;
            }
        }
    }
    /*private void init2(){
        int flag;
        try{
            con= DriverManager.getConnection(dbURL,userName,userPwd);
            String sql="select * from record where day = "+daynumber[i]+"; "
            System.out.println("==>"+sql);
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(sql);
            if (rs.next()) {
                flag = 1;
            } else {
                flag = 0;}
            st.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    private  int init2(String sql) {
        int flag = 0;
        try{
            con= DriverManager.getConnection(dbURL,userName,userPwd);
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(sql);
            if (rs.next()) {
                flag = 1;
            } else {
                flag = 0;}
            st.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
