
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase {
	
		
		private static Connection connection;
		
		private static String username = " ";	
		private static String password = " ";
		private static String port="";
		private static String server="";
		private static String dbName="";
	
		public MySQLDatabase(String _username,String _password, String _server, String _port, String _dbName){
			username=_username;
			password=_password;
			server=_server;
			port=_port;
			dbName=_dbName;
			
		}
		
		public boolean  Connection(Connection con)
	    {
	        try{
	 
	        con= DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+dbName+"?verifyServerCertificate=false&useSSL=true",username,password);
	        System.out.println("Connected");
	        }
	        catch (SQLException sql ) {
	            System.out.println (sql);
	          }
			if(con !=null){
				connection=con;
				return true;
			}
			else{
				connection=null;
				return false;
			}
	    }
		public boolean closeConnection(){
			
			boolean works = false;
			
			if(connection != null){
				try {
					connection.close();
					works = true;
				} catch (SQLException e) {
					works = false;
				}
			}
			return works;
		}
	}
		
		
	