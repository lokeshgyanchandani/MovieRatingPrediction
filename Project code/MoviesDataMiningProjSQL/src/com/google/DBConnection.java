package com.google;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	
	static Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	public static Connection getConnection() throws Exception
	{
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies", "root", "root");
	    System.out.println(conn.toString());
		return conn;
	}

	public static void main(String[] args) 
	{
		try
		{
			conn = getConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public void addMemberInfo(int movieid, String title, String genres) throws Exception
	{
		try
		{
			stmt = conn.prepareStatement("Insert into movie_info(MovieID, Title, Genres) values(" + movieid + "," + title + "," + genres + ")");
			stmt.setInt(1, movieid);
			stmt.setString(2, title);
			stmt.setString(3, genres);
			stmt.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if(conn != null)
				conn.close();
			if(stmt != null)
				stmt.close();
		}
	}
}
