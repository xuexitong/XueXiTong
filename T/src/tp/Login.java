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
	    //"jdbc:sqlserver://localhost:1433;databaseName = app"��microsoft�ṩ��java-sqlserver���ݿ���������������sqlserverʱ��url
	    //localhost��ָ������ݿ��������ַ��1433Ϊ���sqlserver�˿ںţ�
	    //��SPJ_TEST������Ҫ���ӵ����ݿ������
	    String user = "sa";
	    String password = "990904";
			try {
		        //1.��������
		        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        //System.out.println("���������ɹ���");
		    }catch(Exception e) {
		        e.printStackTrace();
		        System.out.println("��������ʧ�ܣ�");
		    }
		    try {  
		    	//2.����
		    	conn = DriverManager.getConnection(url,user,password);
			    test=conn.createStatement();
			    sql="select * from login where name='"+name+"' and pwd='"+pwd+"'";
			    rs=test.executeQuery(sql);
			    if(rs.next()) return "yes";
			    else return "no";
		    }catch(Exception e) {
		        e.printStackTrace();
		        System.out.println("�������ݿ�ʧ�ܣ�");
		    }
		    return "error";
	}


}
