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
	    //"jdbc:sqlserver://localhost:1433;databaseName = app"��microsoft�ṩ��java-sqlserver���ݿ���������������sqlserverʱ��url
	    //localhost��ָ������ݿ��������ַ��1433Ϊ���sqlserver�˿ںţ�
	    //��SPJ_TEST������Ҫ���ӵ����ݿ������
	    String user = "sa";
	    String password = "990904";
	    System.out.println("regising.....");
			try {
		        //1.��������
		        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        System.out.println("���������ɹ���");
		    }catch(Exception e) {
		        e.printStackTrace();
		        System.out.println("��������ʧ�ܣ�");
		    }
		    try {  
		        //2.����
		        conn = DriverManager.getConnection(url,user,password);
		        if(conn!=null)
		        System.out.println("�������ݿ�ɹ���");
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
		        System.out.println("�������ݿ�ʧ�ܣ�");
		    }
			return "linkdb error";
		    
	}
}
