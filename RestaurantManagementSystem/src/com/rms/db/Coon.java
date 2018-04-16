package com.rms.db;

import java.sql.*;

public class Coon {
	public static  String DBDRIVER = "com.mysql.jdbc.Driver";
	public static  String DBURL = "jdbc:mysql://localhost:3306/rms?useUnicode=true&characterEncoding=UTF-8";
	public static  String DBUSER = "tzhang";
	public static  String DBPASS = "tzhang";
	
	
	Connection conn = null;

	public Connection getCoon() {
		try {
			Class.forName(DBDRIVER);
			
		/*	 DBUSER = System.getenv("ACCESSKEY");
			 DBPASS = System.getenv("SECRETKEY");
			//System.getenv("MYSQL_HOST_S"); Îª´Ó¿â£¬Ö»¶Á
			DBURL= String.format("jdbc:mysql://%s:%s/%s", System.getenv("MYSQL_HOST"), System.getenv("MYSQL_PORT"), System.getenv("MYSQL_DB"));*/
			
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;

	}
}
