package com.google;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.google.DBConnection;

public class MemberInfoConverter {

    static DBConnection dbConn = new DBConnection();
    static Connection conn = null;
    static PreparedStatement stmt = null;
    static ResultSet rs = null;
        
	public static void main(String[] args) {
	    MemberInfoConverter m = new MemberInfoConverter();
	    try{
	        File file = new File("E:\\MS\\Fall 13\\Data Mining\\MovieLens+Data set\\ml-1m\\ml-1m\\movies.dat");
	        Scanner input = new Scanner(file);
	        int i = 0;
	        String strDump[] = new String[3883];
	        int strDumpCount = 0;
	        int count = 0;
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies", "root", "root");
	        System.out.println(conn.toString());

	        String nextLine;
	        while (input.hasNext()) {
	            nextLine = input.nextLine();
	            strDump[strDumpCount++]= nextLine;
	            m.printMemberInfo(nextLine);
	        }
	        input.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private void printMemberInfo(String strMember) throws Exception
	{
		String[] strArray = strMember.split("::");

		System.out.print("Member ID :" + strArray[0] + "::");
		System.out.print("Member Name :" + strArray[1] + "::");
		System.out.println("Member Reviews :" + strArray[2]);
		addMemberInfo(Integer.parseInt(strArray[0]), strArray[1], strArray[2]);
	}

	public void addMemberInfo(int movieid, String title, String genres) throws Exception
    {
        try
        {
            stmt = conn.prepareStatement("Insert into movie_info(MovieID, Title, Genres) values(?, ?, ?)");
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
