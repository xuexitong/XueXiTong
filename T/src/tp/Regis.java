package tp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Regis {
	/*public static void main(String[] args)
	{
		
	}*/
	public static String regis(String name,String pwd)
	{
		PreparedStatement ps = null;
	    Connection conn = null;
	    ResultSet rs = null;
	    String sql = null;
	    String url = "jdbc:sqlserver://localhost:1433;databaseName=app"; 
	    //"jdbc:sqlserver://localhost:1433;databaseName = app"是microsoft提供的java-sqlserver数据库连接驱动来访问sqlserver时的url
	    //localhost是指你的数据库服务器地址，1433为你的sqlserver端口号！
	    //“SPJ_TEST”是所要连接的数据库的名称
	    String user = "sa";
	    String password = "990904";
	    System.out.println("regising.....");
			try {
		        //1.加载驱动
		        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        System.out.println("加载驱动成功！");
		    }catch(Exception e) {
		        e.printStackTrace();
		        System.out.println("加载驱动失败！");
		    }
		    try {  
		        //2.连接
		        conn = DriverManager.getConnection(url,user,password);
		        if(conn!=null)
		        System.out.println("连接数据库成功！");
		        Statement test=conn.createStatement();
		        String a="hello";
		        sql="select * from login where name='"+name+"'";
		        rs=test.executeQuery(sql);
		        if(rs.next()) return "rename";
		        else{
		        	sql="insert into login values('"+name+"','"+pwd+"')";
			        int i=test.executeUpdate(sql);
			        if(i!=0) return "yes";
			        else return "insert error";
		        }
		    }catch(Exception e) {
		        e.printStackTrace();
		        System.out.println("连接数据库失败！");
		    }
			return "linkdb error";
		    
	}
}
