package sample;

import java.util.Scanner;
import java.sql.*;

public class Test {
	
	public static void main(String []args) {
		
		
		
		try {
			 
		       Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		
		Connection conect=null;
		
		
		System.out.println("Enter your username: ");
		String username = sc.next();
		System.out.println("Your username is " + username);
		
		System.out.println("Enter your Password: ");
		String password = sc.next();
		
		System.out.println("Enter localhost or new address: ");
		String server = sc.next();
		
		System.out.println("Enter Port: ");
		String port = sc.next();
		
		System.out.println("Enter Data base name: ");
		String dbName = sc.next();
		
		sc.close();
		
		MySQLDatabase con= new MySQLDatabase(username,password,server,port, dbName);
		
		if(con.Connection(conect)){
			System.out.println("Connected!");
		}
		else{
			System.out.println("Not connected");
			
		}
		
		if(con.closeConnection()){
			System.out.println("Connection closed ");
		}else{
			System.out.println("Connection did not closed ");
			}
		}
}
