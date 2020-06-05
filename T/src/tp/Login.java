package tp;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
	public static String login(String name,String pwd)
	{
		PreparedStatement ps = null;
	    Connection conn = null;
	    ResultSet rs = null;
	    String sql=null;
	    Statement test = null;
	    String url = "jdbc:sqlserver://localhost:1433;databaseName=app"; 
	    //"jdbc:sqlserver://localhost:1433;databaseName = app"是microsoft提供的java-sqlserver数据库连接驱动来访问sqlserver时的url
	    //localhost是指你的数据库服务器地址，1433为你的sqlserver端口号！
	    //“SPJ_TEST”是所要连接的数据库的名称
	    String user = "sa";
	    String password = "990904";
			try {
		        //1.加载驱动
		        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        //System.out.println("加载驱动成功！");
		    }catch(Exception e) {
		        e.printStackTrace();
		        System.out.println("加载驱动失败！");
		    }
		    try {  
		    	//2.连接
		    	conn = DriverManager.getConnection(url,user,password);
			    test=conn.createStatement();
			    sql="select * from login where name='"+name+"' and pwd='"+pwd+"'";
			    rs=test.executeQuery(sql);
			    if(rs.next()) return "yes";
			    else return "no";
		    }catch(Exception e) {
		        e.printStackTrace();
		        System.out.println("连接数据库失败！");
		    }
		    return "error";
	}


}
