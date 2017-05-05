package sample;

import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MySQLDatabase {


    private static Connection connection;

    private static String username = " ";
    private static String password = " ";
    private static String port = "";
    private static String server = "";
    private static String dbName = "";


    public MySQLDatabase(String _username, String _password, String _server, String _port, String _dbName) {
        username = _username;
        password = _password;
        server = _server;
        port = _port;
        dbName = _dbName;

    }

    public boolean connect(Connection con) {
        try {

            con = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + dbName + "?verifyServerCertificate=false&useSSL=true", username, password);
            System.out.println("Connected");
        } catch (SQLException sql) {
            System.out.println("Connecting to the database failed.");
        }
        if (con != null) {
            connection = con;
            return true;
        } else {
            connection = null;
            return false;
        }
    }

    public boolean closeConnection() {

        boolean works = false;

        if (connection != null) {
            try {
                connection.close();
                works = true;
            } catch (SQLException e) {
                works = false;
            }
        }
        return works;
    }


    public ArrayList<ArrayList<String>> getData(String SQLStatement, ArrayList<String> values, boolean search) {
        PreparedStatement prepStmt = prepare(SQLStatement, values, search);
        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
        try {
            ResultSet rs = prepStmt.executeQuery();
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
    public ArrayList<ArrayList<String>> getData(String SQLStatement, boolean columns) {
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


    public boolean setData(String SQLStatement, ArrayList<String> values, boolean search) {
        try {
            PreparedStatement stmnt = prepare(SQLStatement, values, search);
            int rowsAffected = stmnt.executeUpdate();
            if (rowsAffected >= 1)
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
            System.out.println(e);
            return false;
        }
        return false;
    }


    private PreparedStatement prepare(String SQLStatement, ArrayList<String> values, boolean search) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement(SQLStatement);
            for (int i = 1; i <= values.size(); i++) {
                if (search)
                    prepStmt.setString(i, "%" + values.get(i - 1) + "%");
                else
                    prepStmt.setString(i, values.get(i - 1));
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
    public void startTrans() {

        try {
            connection.setAutoCommit(false);
        } catch (SQLException sqle) {
        }
    }

    /**
     * A method that ends transaction
     */
    public void endTrans() {

        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException sqle) {
        }
    }

    /**
     * A method that rolls back the transaction
     */
    public void rollbackTrans() {

        try {
            connection.rollback();
        } catch (SQLException sqle) {
        }
    }

    public void postP(String book_Title, String book_ISBN, String book_YearofPublication, String book_Copies, String book_Location, String publisher_Name, String publisher_City, String genre_Name, String author_FirstName, String author_LastName) throws SQLException {
        if(!book_Title.equals("") && !book_Location.equals("") && !publisher_City.equals("") && !publisher_Name.equals("") && !genre_Name.equals("") && !author_FirstName.equals("") && !author_LastName.equals("") && !book_Copies.matches("-?\\d+(\\.\\d+)?") && !book_ISBN.matches("-?\\d+(\\.\\d+)?") && !book_YearofPublication.matches("-?\\d+(\\.\\d+)?")){
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("Add book error");
            alert_loan.setContentText("Book data is not valid.");
            alert_loan.showAndWait();
            return;
        }
        startTrans();
        //////////////////////// FOR PUBLISHER select
        ArrayList<ArrayList<String>> results = null;
        ArrayList<ArrayList<String>> results1 = null;
        ArrayList<ArrayList<String>> results2 = null;
        ArrayList<ArrayList<String>> results3 = null;
        ArrayList<String> isbn = new ArrayList<>();
        ArrayList<String> publish = new ArrayList<>();
        ///////////////

        /////////////COLLECTIONS
        ArrayList<String> collection_Books = new ArrayList<>();
        ArrayList<String> collection_Author = new ArrayList<>();
        ArrayList<String> collection_Publisher = new ArrayList<>();
        ArrayList<String> collection_genre = new ArrayList<>();


        ArrayList<String> collection_AuthorBook = new ArrayList<>();

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
        results = getData(st, publish, false);
        int book_pub_id = Integer.parseInt(results.get(1).get(0));

        /////////

        ////BOOKS INSERT

        String genr = "SELECT genre_id from genres where genre_name=?";
        results1 = getData(genr, collection_genre, false);

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
        setDataReturnValue = setData(books, collection_Books, false);
        /////////////////

        /////////////AUTHOR
        String author = "INSERT INTO author ( author_firstname, author_lastname)"
                + " VALUES (?, ?);";

        setDataReturnValue = setData(author, collection_Author, false);
        //////////////////

        String author_book = "INSERT INTO author_book (author_id, book_id) " + "VALUES (?,?)";

        String author_id = " SELECT author_id from author where author_firstname=? and author_lastname=?";
        results2 = getData(author_id, collection_Author, false);
        collection_AuthorBook.add(results2.get(1).get(0));

        String book_isbn = "SELECT book_id FROM books where book_isbn=?";
        isbn.add(book_ISBN);

        results3 = getData(book_isbn, isbn, false);
        collection_AuthorBook.add(results3.get(1).get(0));
        setDataReturnValue = setData(author_book, collection_AuthorBook, false);
        endTrans();
    }

    public void setBook_On_Loan(String username, String book_isbn) throws SQLException {

        boolean setDataReturnValue = false;


        ArrayList<String> values = new ArrayList<>();
        ArrayList<ArrayList<String>> book_id_results = null;
        ArrayList<ArrayList<String>> user_id_results = null;
        ArrayList<ArrayList<String>> book_copies_results = null;
        ArrayList<String> book_id_values = new ArrayList<>();
        ArrayList<String> user_id_values = new ArrayList<>();
        book_id_values.add(book_isbn);

        String book_isbn1 = "SELECT book_id FROM books where book_isbn=?";
        book_id_results = getData(book_isbn1, book_id_values, false);
        if (book_id_results.size() == 1) {
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("Book error");
            alert_loan.setContentText("This book does not exist");
            alert_loan.showAndWait();
            return;
        }
        String book_id = book_id_results.get(1).get(0);
        values.add(book_id);
        user_id_values.add(username);
        String user_id = "SELECT user_id FROM user where user_username=?";
        user_id_results = getData(user_id, user_id_values, false);
        if (user_id_results.size() == 1) {
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("User error");
            alert_loan.setContentText("This user does not exist");
            alert_loan.showAndWait();
            return;
        }
        values.add(user_id_results.get(1).get(0));

        ArrayList<ArrayList<String>> loaned_book_results = null;
        ArrayList<String> loaned_book_values = new ArrayList<>();
        loaned_book_values.add(book_id);
        loaned_book_results = getData("SELECT user_id FROM books_on_loan WHERE book_id = ?", loaned_book_values, false);


        // if there's already such a loaned book
        if (loaned_book_results.size() > 1) {
            if (loaned_book_results.get(1).get(0).equals(user_id_results.get(1).get(0))) {
                Alert alert_loan = new Alert(Alert.AlertType.WARNING);
                alert_loan.setTitle("Rent error");
                alert_loan.setContentText("You are already renting this book.");
                alert_loan.showAndWait();
                return;
            }
        }

        book_copies_results = getData("SELECT book_copies FROM books where book_id =?", loaned_book_values, false);
        if (Integer.parseInt(book_copies_results.get(1).get(0)) <= loaned_book_results.size() - 1) {
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("Rent error");
            alert_loan.setContentText("All the copies of this book are already loaned");
            alert_loan.showAndWait();
            return;
        }


        LocalDate today = LocalDate.now();

        //add 2 week to the current date
        LocalDate next2Week = today.plus(2, ChronoUnit.WEEKS);

        values.add(next2Week + " 20:00:00");
        values.add("0");
        String inst = "INSERT INTO books_on_loan (book_id,user_id,date_due,returned) " + "VALUES(?, ?, ?, ?)";
        startTrans();
        setDataReturnValue = setData(inst, values, false);
        endTrans();
    }


    public void deleteP(String username, String book_isbn) throws SQLException {
        startTrans();
        boolean dataFound = false;
        ArrayList<String> values = new ArrayList<>();
        ArrayList<ArrayList<String>> results = null;
        ArrayList<ArrayList<String>> results1 = null;
        ArrayList<String> geto = new ArrayList<>();
        ArrayList<String> wurf = new ArrayList<>();
        String book_isbn1 = "SELECT book_id FROM books where book_isbn=?";
        geto.add(book_isbn);
        results = getData(book_isbn1, geto, false);
        if (results.size() == 1) {
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("Book error");
            alert_loan.setContentText("This book does not exist");
            alert_loan.showAndWait();
            return;
        }
        values.add(results.get(1).get(0));
        wurf.add(username);
        String user_id = "SELECT user_id FROM user where user_username=?";

        results1 = getData(user_id, wurf, false);
        if (results1.size() == 1) {
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("User error");
            alert_loan.setContentText("This user does not exist");
            alert_loan.showAndWait();
            return;
        }
        values.add(results1.get(1).get(0));

        dataFound = setData("DELETE FROM books_on_loan WHERE book_id=? AND user_id=?", values, false);


        if (dataFound) {
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("Book returned");
            alert_loan.setContentText("The book was returned successfully.");
            alert_loan.showAndWait();
            endTrans();
            return;
        } else {
            Alert alert_loan = new Alert(Alert.AlertType.WARNING);
            alert_loan.setTitle("Book error");
            alert_loan.setContentText("The user has not loaned this book.");
            alert_loan.showAndWait();
            endTrans();
            return;

        }

    }

    public String login(String username, String password) throws SQLException {
        ArrayList<ArrayList<String>> results = null;
        ArrayList<String> vals = new ArrayList<>();
        vals.add(username);
        vals.add(password);

        results = getData("Select role FROM user WHERE user_username=? AND user_password = ?;", vals, false);

        if (results.size() > 1) {
            String role = results.get(1).get(0);
            return role;
        } else
            return "error";
    }

    public void regLib(String fName, String lName, String email, String username, String password) {
        String role = "L";
        boolean setDataReturnValue = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(username);
        values.add(email);
        values.add(password);
        values.add(fName);
        values.add(lName);
        values.add(role);

        String st = "INSERT INTO user (user_username, user_email, user_password, user_firstname, user_lastname, role) " + "VALUES(?,?,?,?,?,?)";
        setDataReturnValue = setData(st, values, false);


        endTrans();

    }

    public void regUser(String fName, String lName, String email, String username, String password) throws SQLException {
        String role = "U";
        boolean setDataReturnValue = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(username);
        values.add(email);
        values.add(password);
        values.add(fName);
        values.add(lName);
        values.add(role);

        String st = "INSERT INTO user (user_username, user_email, user_password, user_firstname, user_lastname, role) " + "VALUES(?,?,?,?,?,?)";
        setDataReturnValue = setData(st, values, false);


        endTrans();

    }

}

		
	