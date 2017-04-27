import java.sql.*;

public class Books {
	private int book_id;
	private String book_isbn;
	private String book_title;
	private int book_publisher_id;
	private int book_publisher_year;
	private int book_copies;
	private String book_location;
	private int book_gener_id;
	
	private static Connection connection;
	
	private static String username = " ";	
	private static String password = " ";
	private static String port="";
	private static String server="";
	private static String dbName="";
	
	public Books(){
		
	}
	public boolean   Connection(Connection con) 
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
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getBook_isbn() {
		return book_isbn;
	}
	public void setBook_isbn(String book_isbn) {
		this.book_isbn = book_isbn;
	}
	public String getBook_title() {
		return book_title;
	}
	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}
	public int getBook_publisher_id() {
		return book_publisher_id;
	}
	public void setBook_publisher_id(int book_publisher_id) {
		this.book_publisher_id = book_publisher_id;
	}
	public int getBook_publisher_year() {
		return book_publisher_year;
	}
	public void setBook_publisher_year(int book_publisher_year) {
		this.book_publisher_year = book_publisher_year;
	}
	public int getBook_copies() {
		return book_copies;
	}
	public void setBook_copies(int book_copies) {
		this.book_copies = book_copies;
	}
	public String getBook_location() {
		return book_location;
	}
	public void setBook_location(String book_location) {
		this.book_location = book_location;
	}
	public int getBook_gener_id() {
		return book_gener_id;
	}
	public void setBook_gener_id(int book_gener_id) {
		this.book_gener_id = book_gener_id;
	}
	
	
}
