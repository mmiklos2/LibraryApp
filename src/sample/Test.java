package sample;

import java.util.ArrayList;
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
		
		/**
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
		*/
		sc.close();
		
		MySQLDatabase con= new MySQLDatabase("root","knjiga007","127.0.0.1","3306", "mydb");

		if(con.connect(conect)){
			System.out.println("Connected!");
		}
		else{
			System.out.println("Not connected");
			
		}

		/**ConcreteSearcher cs=new ConcreteSearcher(con);
		ArrayList<DetailedBook>dbs = cs.search("dible", "publisher");
        for(DetailedBook dbsa: dbs){
            dbsa.printInfo();
        }*/
		try {
			con.postP();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollbackTrans();
		}
		/**try {
			con.setBook_On_Loan();
		} catch (SQLException e) {
			e.printStackTrace();
		 con.rollbackTrans();
		}*/
		try {
			con.deleteP();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollbackTrans();
		}
		if(con.closeConnection()){
			System.out.println("connect closed ");
		}else{
			System.out.println("connect did not closed ");
			}
		}
}
