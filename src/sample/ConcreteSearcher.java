package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * @author Boyz
 */
public class ConcreteSearcher {
    private MySQLDatabase db;

    public ConcreteSearcher(MySQLDatabase db) {
        this.db = db;
    }

    public MySQLDatabase getDb() {

        return db;
    }

    public void setDb(MySQLDatabase db) {
        this.db = db;
    }


    public ArrayList<DetailedBook> search(String searchInput, String typeOfSearch) {
        String[] searchTerms = searchInput.split(" ");
        System.out.println("Search terms "+searchTerms.length);
        ArrayList<String> values = new ArrayList<>();
        ArrayList<DetailedBook> returnedBooks = new ArrayList<>();
        Books aBook = null;
        Publisher aPublisher = null;
        Author anAuthor = null;
        Genres aGenre = null;
        Author_Book anAuthor_book = null;
        boolean firstRun = true;
        boolean booksSearched = false;
        boolean publishersSearched = false;
        boolean authorsSearched = false;
        boolean genresSearched = false;
        String finalQuery = "SELECT";
        if (typeOfSearch.equals("Titles")) {
            finalQuery += " * FROM books WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += " ( book_isbn LIKE ? ) OR ( book_title LIKE ? ) OR ( book_published_year LIKE ? ) ";
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
            }
            firstRun = true;
            finalQuery += ");";
            booksSearched = true;
        } else if (typeOfSearch.equals("Publishers")) {
            finalQuery += "* FROM publisher WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += " ( publisher_name LIKE ? ) OR ( publisher_city LIKE ? ) ";
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
            }
            firstRun = true;
            finalQuery += ");";
            publishersSearched = true;
        } else if (typeOfSearch.equals("Genres")) {
            finalQuery += "* FROM genres WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += " ( genre_name LIKE ? ) ";
                values.add(searchTerms[i]);
            }
            firstRun = true;
            finalQuery += ");";
            genresSearched = true;
        } else if (typeOfSearch.equals("Authors")) {
            finalQuery += "* FROM author WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += " ( author_firstname LIKE ? ) OR ( author_lastname LIKE ? ) ";
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
            }
            finalQuery += ");";
            authorsSearched = true;
        }
        ArrayList<ArrayList<String>> queryResponse = db.getData(finalQuery, values, true);
        System.out.println(queryResponse.size() + "QR");
        System.out.println(queryResponse.get(0).size() + "QR");
        for(String s:queryResponse.get(0)){
            System.out.println(s);
        }
        // if a search for books has been done
        if (booksSearched) {
            for (int i = 1; i < queryResponse.size(); i++) {
                String authorList = "";
                aBook = new Books(Integer.parseInt(queryResponse.get(i).get(0)), queryResponse.get(i).get(1), queryResponse.get(i).get(2), Integer.parseInt(queryResponse.get(i).get(3)), Integer.parseInt(queryResponse.get(i).get(4)), Integer.parseInt(queryResponse.get(i).get(5)), queryResponse.get(i).get(6), Integer.parseInt(queryResponse.get(i).get(7)));

                aPublisher=getPublisher(aBook);
                aGenre=getGenres(aBook);
                authorList=getAuthorList(aBook);

                // ITERATE THROUGH POSSIBLE AUTHORS With FOR LOOP AND CREATE A NEW AUTHOR FULLNAME EVERY TIME YOU FIND ONE, PASS ARRAYLIST TO ADETAILEDBOOK
                DetailedBook aDetailedBook = new DetailedBook(new SimpleStringProperty(aBook.getBook_isbn()), new SimpleStringProperty(aBook.getBook_title()), aBook.getBook_publisher_year(), aBook.getBook_copies(), new SimpleStringProperty(aBook.getBook_location()), new SimpleStringProperty(aGenre.getGenre_name()), new SimpleStringProperty(aPublisher.getPublisher_name()), new SimpleStringProperty(authorList));
                returnedBooks.add(aDetailedBook);
            }
        }
        // if any of the other three searches has been made
        else {
            // author search been done
            if (authorsSearched) {
                for (int i = 1; i < queryResponse.size(); i++) {
                    anAuthor = new Author(Integer.parseInt(queryResponse.get(i).get(0)), queryResponse.get(i).get(1), queryResponse.get(i).get(2));
                    ArrayList<ArrayList<String>> author_bookData = db.getData("SELECT * FROM author_book WHERE author_id = " + anAuthor.getAuthor_id() + ";", true);
                    for (int j = 1; j < author_bookData.size(); j++) {
                        anAuthor_book = new Author_Book(Integer.parseInt(author_bookData.get(j).get(0)), Integer.parseInt(author_bookData.get(j).get(1)));
                        ArrayList<ArrayList<String>> bookData = db.getData("SELECT * FROM books WHERE book_id = " + anAuthor_book.getBook_id() + ";", true);
                        aBook = new Books(Integer.parseInt(bookData.get(1).get(0)), bookData.get(1).get(1), bookData.get(j).get(2), Integer.parseInt(bookData.get(1).get(3)), Integer.parseInt(bookData.get(1).get(4)), Integer.parseInt(bookData.get(1).get(5)), bookData.get(1).get(6), Integer.parseInt(bookData.get(1).get(7)));

                        aPublisher=getPublisher(aBook);
                        aGenre=getGenres(aBook);

                        DetailedBook aDetailedBook= new DetailedBook(new SimpleStringProperty(aBook.getBook_isbn()), new SimpleStringProperty(aBook.getBook_title()), aBook.getBook_publisher_year(), aBook.getBook_copies(), new SimpleStringProperty(aBook.getBook_location()), new SimpleStringProperty(aGenre.getGenre_name()), new SimpleStringProperty(aPublisher.getPublisher_name()), new SimpleStringProperty(anAuthor.getAuthor_firstname()+" "+anAuthor.getAuthor_lastname()));
                        returnedBooks.add(aDetailedBook);
                    }
                }
            }
            else if(publishersSearched){
                for (int i = 1; i < queryResponse.size(); i++) {
                    aPublisher = new Publisher(Integer.parseInt(queryResponse.get(i).get(0)), queryResponse.get(i).get(1), queryResponse.get(i).get(2));
                    ArrayList<ArrayList<String>> bookData= db.getData("SELECT * FROM books WHERE book_publisher_id = "+ aPublisher.getPublisherId() +"",true);
                    for(int j=1; j<bookData.size();j++){
                        aBook = new Books(Integer.parseInt(bookData.get(1).get(0)), bookData.get(1).get(1), bookData.get(j).get(2), Integer.parseInt(bookData.get(1).get(3)), Integer.parseInt(bookData.get(1).get(4)), Integer.parseInt(bookData.get(1).get(5)), bookData.get(1).get(6), Integer.parseInt(bookData.get(1).get(7)));
                        String authorList=getAuthorList(aBook);
                        aGenre=getGenres(aBook);
                        DetailedBook aDetailedBook= new DetailedBook(new SimpleStringProperty(aBook.getBook_isbn()), new SimpleStringProperty(aBook.getBook_title()), aBook.getBook_publisher_year(), aBook.getBook_copies(), new SimpleStringProperty(aBook.getBook_location()), new SimpleStringProperty(aGenre.getGenre_name()), new SimpleStringProperty(aPublisher.getPublisher_name()), new SimpleStringProperty(authorList));
                        returnedBooks.add(aDetailedBook);
                    }
                }
            }
            else if(genresSearched){
                for(int i=1;i<queryResponse.size();i++){
                    aGenre=new Genres(Integer.parseInt(queryResponse.get(i).get(0)), queryResponse.get(i).get(1));
                    ArrayList<ArrayList<String>> bookData=db.getData("SELECT * FROM books WHERE book_genre_id = "+ aGenre.getGenre_id() +";",true);
                    for(int j=1; j<bookData.size();j++){
                        aBook = new Books(Integer.parseInt(bookData.get(1).get(0)), bookData.get(1).get(1), bookData.get(j).get(2), Integer.parseInt(bookData.get(1).get(3)), Integer.parseInt(bookData.get(1).get(4)), Integer.parseInt(bookData.get(1).get(5)), bookData.get(1).get(6), Integer.parseInt(bookData.get(1).get(7)));
                        String authorList=getAuthorList(aBook);
                        aPublisher=getPublisher(aBook);

                        DetailedBook aDetailedBook= new DetailedBook(new SimpleStringProperty(aBook.getBook_isbn()), new SimpleStringProperty(aBook.getBook_title()), aBook.getBook_publisher_year(), aBook.getBook_copies(), new SimpleStringProperty(aBook.getBook_location()), new SimpleStringProperty(aGenre.getGenre_name()), new SimpleStringProperty(aPublisher.getPublisher_name()), new SimpleStringProperty(authorList));
                        returnedBooks.add(aDetailedBook);
                    }
                }
            }
        }
        return returnedBooks;
    }

    public Genres getGenres(Books aBook){
        ArrayList<ArrayList<String>> genreData = db.getData("SELECT * FROM genres WHERE genre_id = " + aBook.getBook_genre_id() + ";", true);
        return new Genres(Integer.parseInt(genreData.get(1).get(0)), genreData.get(1).get(1));
    }

    public Publisher getPublisher(Books aBook){
        ArrayList<ArrayList<String>> publisherData = db.getData("SELECT * FROM publisher WHERE publisher_id = " + aBook.getBook_publisher_id() + ";", true);
        return new Publisher(Integer.parseInt(publisherData.get(1).get(0)), publisherData.get(1).get(1), publisherData.get(1).get(2));
    }

    public String getAuthorList(Books aBook){
        ArrayList<ArrayList<String>> author_bookData = db.getData("SELECT * FROM author_book WHERE book_id = " + aBook.getBook_id() + ";", true);
        String authorList="";
        for (int k = 1; k < author_bookData.size(); k++) {
            Author_Book anAuthor_book = new Author_Book(Integer.parseInt(author_bookData.get(k).get(0)), Integer.parseInt(author_bookData.get(k).get(1)));
            ArrayList<ArrayList<String>> authorData = db.getData("SELECT * FROM author WHERE author_id = " + anAuthor_book.getAuthor_id() + ";", true);
           // System.out.println(authorData.get(0).get(0)+ authorData.get(0).get(1)+ authorData.get(0).get(2));
           // System.out.println(authorData.size()+" AD");
            Author anAuthor = new Author(Integer.parseInt(authorData.get(1).get(0)), authorData.get(1).get(1), authorData.get(1).get(2));
            authorList += anAuthor.getAuthor_firstname() + " " + anAuthor.getAuthor_lastname();
            if (k + 1 < author_bookData.size())
                authorList += ", ";
        }
        System.out.println(authorList);
        return authorList;
    }

}
