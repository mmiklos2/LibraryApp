package sample;

import java.sql.*;
import java.util.ArrayList;

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

	public boolean Connection(Connection con)
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


	public ArrayList<ArrayList<String>> getData(String SQLStatement, ArrayList<String>values, boolean search){
		PreparedStatement prepStmt=prepare(SQLStatement, values, search);
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		try {
			ResultSet rs=prepStmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			ArrayList<String> columnNames = new ArrayList<String>();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				columnNames.add(rsmd.getColumnName(i + 1));
			}
			while (rs.next()) {
				ArrayList<String> row = new ArrayList<String>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add(rs.getString(i));
				}
				table.add(row);
			}
			table.add(0, columnNames);
			return table;
		} catch (SQLException e) {
			System.out.println("An SQL exception occurred");
			e.printStackTrace();
		}
		return null;
	}

    /**
     * getData method with columns name possibility
     *
     * @param SQLStatement used for executeQuery()
     * @param columns      determines if the column names will be returned
     * @return returns an ArrayList representing the table
     */
    public ArrayList<ArrayList<String>> getData(String SQLStatement, boolean columns){
        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
            try {
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery(SQLStatement);
                ResultSetMetaData rsmd = rs.getMetaData();
                ArrayList<String> columnNames = new ArrayList<String>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columnNames.add(rsmd.getColumnName(i + 1));
                }
                stmnt = connection.createStatement();
                rs = stmnt.executeQuery(SQLStatement);
                while (rs.next()) {
                    ArrayList<String> row = new ArrayList<String>();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    table.add(row);
                }
                table.add(0, columnNames);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return table;
    }


	public boolean setData(String SQLStatement, ArrayList<String>values, boolean search){
		try {
			PreparedStatement stmnt = prepare(SQLStatement, values, search);
			int rowsAffected = stmnt.executeUpdate();
			if (rowsAffected <= 1)
				return true;
			else
				return false;
		} catch (SQLNonTransientException e) {
			e.printStackTrace();
			System.out.println("You must fix the cause of the exception before retrying");
		} catch (SQLTransientException e) {
			e.printStackTrace();
			System.out.println("You may retry");
		} catch (SQLRecoverableException e) {
			e.printStackTrace();
			System.out.println("Restart the program and try again");
		} catch (BatchUpdateException e) {
			e.printStackTrace();
			System.out.println("Error occurred during batch update operation");
		} catch (SQLClientInfoException e) {
			e.printStackTrace();
			System.out.println("One or more properties could not be set on Connection");
		} catch (SQLException e) {
			System.out.println("ALTER, UPDATE, INSERT, or DELETE failed.");
			return false;
		}
		return false;
	}



	private PreparedStatement prepare(String SQLStatement, ArrayList<String> values, boolean search) {
		PreparedStatement prepStmt = null;
		try {
			prepStmt = connection.prepareStatement(SQLStatement);
			System.out.println(SQLStatement);
			for (int i = 1; i <= values.size(); i++) {
			    System.out.println(values.get(i-1));
			    if(search)
				    prepStmt.setString(i, "%"+values.get(i-1)+"%");
			    else
                    prepStmt.setString(i, values.get(i-1));
			}
		} catch (SQLException e) {
			System.out.println("An SQL exception occurred");
			e.printStackTrace();
		}
		return prepStmt;
	}

    /**
     * A method that starts transaction
     */
    public void startTrans(){

        try{
            connection.setAutoCommit(false);
        }
        catch(SQLException sqle){
        }
    }

    /**
     * A method that ends transaction
     */
    public void endTrans(){

        try{
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch(SQLException sqle){
        }
    }

    /**
     * A method that rolls back the transaction
     */
    public void rollbackTrans(){

        try{
            connection.rollback();
        }
        catch(SQLException sqle){
        }
    }

}
		
		
	