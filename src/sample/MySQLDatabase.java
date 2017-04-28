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
	Publisher pb = new Publisher("sdfgh","sdfg");
	Genres gr= new Genres("gfd");
	Author at= new Author("m,kju","asd");
	Books bk;
	User ur= new User("lxc8852", "lxc8852@g.rit.edu", "crnilegenda", "Luka", "Crnjakovic");

	public MySQLDatabase(String _username,String _password, String _server, String _port, String _dbName){
		username=_username;
		password=_password;
		server=_server;
		port=_port;
		dbName=_dbName;

	}

	public boolean connect(Connection con)
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
			System.out.println("One or more properties could not be set on connect");
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
			for (int i = 1; i <= values.size(); i++) {
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
	public void postP(String book_Title, String book_ISBN, String book_YearofPublication, String book_Copies, String book_Location, String publisher_Name, String publisher_City, String genre_Name, String author_FirstName, String author_LastName) throws SQLException {
    startTrans();
		//////////////////////// FOR PUBLISHER select
		ArrayList<ArrayList<String>> results = null;
		ArrayList<ArrayList<String>> results1 = null;
		ArrayList<String> publish = new ArrayList<>();
		///////////////

		/////////////COLEECTIONS
		ArrayList<String> collection_Books= new ArrayList<>();
		ArrayList<String> collection_Author= new ArrayList<>();
		ArrayList<String> collection_Publisher= new ArrayList<>();
		ArrayList<String> collection_genre= new ArrayList<>();
		///////////////////
		boolean setDataReturnValue = false;
		////////////////////////////////////////////////////////////////

		//////////////////PUBLISHER INSERT
		collection_Publisher.add(publisher_Name);
		collection_Publisher.add(publisher_City);
		/////////////////////////////////////////////////

		///////////////GENRE INSERT
		collection_genre.add(genre_Name);
		/////////////////////////

		////////////AUTHOR INSERT
		collection_Author.add(author_FirstName);
		collection_Author.add(author_LastName);
		/////////////////////////////////////////////

		/////PUBLISHER

		setDataReturnValue = setData("INSERT INTO publisher ( publisher_name, publisher_city) VALUES( ?, ?)", collection_Publisher, false);
		///////

		////GENRE
		String genre = "INSERT INTO genres (genre_name)" + " VALUES (?);";
		setDataReturnValue = setData(genre, collection_genre, false);
		///////WORKS

		///////////SELECTS FOR PUBLISHER

		publish.add(publisher_Name);
		String st = "SELECT publisher_id "
				+ "FROM publisher WHERE publisher_name = ?;";
		results = getData(st, publish,false);
		int book_pub_id=Integer.parseInt(results.get(1).get(0));

		/////////

		////BOOKS INSERT
		bk= new Books("1234656","rghsdff 5342",book_pub_id,1234,534,"gfdnhfgnhg",gr.getGenre_id());
		String genr= "SELECT genre_id from genres where genre_name=?";
		results1=getData(genr,collection_genre,false);

		collection_Books.add(book_ISBN);
		collection_Books.add(book_Title);
		collection_Books.add(Integer.toString(book_pub_id));
		collection_Books.add(book_YearofPublication);
		collection_Books.add(book_Copies);
		collection_Books.add(book_Location);

		collection_Books.add(results1.get(1).get(0));
		//////////

		///////////BOOKS
		String books = "INSERT INTO books ( book_isbn, book_title, book_publisher_id, book_published_year, book_copies, book_location, book_genre_id)"
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?);";
		setDataReturnValue = setData(books, collection_Books,false);
		/////////////////

		/////////////AUTHOR
		String author = "INSERT INTO author ( author_firstname, author_lastname)"
				+ " VALUES (?, ?);";

		setDataReturnValue = setData(author, collection_Author, false);
		//////////////////
		if(setDataReturnValue){
			System.out.println("works");
		}
		else{
			System.out.println("0 rows found!");
		}
    endTrans();
	}

	public void setBook_On_Loan()throws SQLException{
        startTrans();
		boolean setDataReturnValue= false;
		ArrayList<String> values= new ArrayList<>();
		ArrayList<ArrayList<String>> results= null;
		ArrayList<ArrayList<String>> results1= null;
		ArrayList<String> geto= new ArrayList<>();
		ArrayList<String> wurf= new ArrayList<>();
		geto.add(bk.getBook_isbn());
		String book_isbn= "SELECT book_id FROM books where book_isbn=?";

		results= getData(book_isbn,geto,false);
		values.add(results.get(1).get(0));
		wurf.add(ur.getUser_username());
		String user_id= "SELECT user_id FROM user where user_username=?";
		results1= getData(user_id,wurf,false);
		values.add(results1.get(1).get(0));
		values.add("2017-05-17 10:01:00.999999");
		values.add("0");
		String inst= "INSERT INTO books_on_loan (book_id,user_id,date_due,returned) "+ "VALUES(?, ?, ?, ?)";
		setDataReturnValue=setData(inst, values, false);
		if(setDataReturnValue){
			System.out.println("works");
		}
		else{
			System.out.println("0 rows found!");
		}
		endTrans();
	}
	public void deleteP() throws SQLException
	{   startTrans();
		boolean dataFound = false;
		ArrayList<String> values = new ArrayList<>();
		ArrayList<ArrayList<String>> results= null;
		ArrayList<ArrayList<String>> results1= null;
		ArrayList<String> geto= new ArrayList<>();
		ArrayList<String> wurf= new ArrayList<>();
		String book_isbn= "SELECT book_id FROM books where book_isbn=?";
		geto.add(bk.getBook_isbn());
		results= getData(book_isbn,geto,false);
		values.add(results.get(1).get(0));
		wurf.add(ur.getUser_username());
		String user_id= "SELECT user_id FROM user where user_username=?";

		results1= getData(user_id,wurf,false);
		values.add(results1.get(1).get(0));

			dataFound = setData("DELETE FROM books_on_loan WHERE book_id=? AND user_id=?", values,false);


		if (dataFound)
		{
			System.out.println("Rows deleted successfully");
		}
		else
		{
			System.out.println("No rows were found!");
		}
		endTrans();
	}

}
		
		
	