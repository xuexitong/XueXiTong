package tp;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class hello extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String a = "no proper!";
	    String name=null;
	    String pwd=null;
	    String type=null;
	    Login log = null;
	    Regis reg=null;
	    type=req.getParameter("type");
	    name=req.getParameter("username");
		pwd=req.getParameter("password");
		if(type.equals("login")) a=log.login(name, pwd);
		if(type.equals("regis")) a=reg.regis(name, pwd);
		byte[] bytes = a.getBytes("utf-8");
		resp.setContentLength(bytes.length);
		resp.getOutputStream().write(bytes);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}
}
