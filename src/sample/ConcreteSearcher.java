package sample;

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


    public ArrayList<String> search(String searchInput, String typeOfSearch) {
        String[] searchTerms = searchInput.split(" ");
        ArrayList<String> values = new ArrayList<>();
        ArrayList<DetailedBook> returnedBooks = new ArrayList<>();
        Books aBook = null;
        Publisher aPublisher = null;
        Author anAuthor = null;
        Genres aGenre = null;
        boolean firstRun = true;
        boolean booksSearched = false;
        boolean publishersSearched = false;
        boolean authorsSearched = false;
        boolean genresSearched = false;
        String finalQuery = "SELECT";
        if (typeOfSearch.equals("book")) {
            finalQuery += "* FROM book WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += "( ( book_isbn LIKE '%?%' ) OR ( book_title LIKE '%?%' ) OR ( book_published_year LIKE '%?%' ) OR ( book_genre LIKE '%?%' ) )";
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
            }
            firstRun = true;
            finalQuery += ")";
            booksSearched = true;
        } else if (typeOfSearch.equals("publisher")) {
            finalQuery += "* FROM publisher WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += "( ( publisher_name LIKE '%?%' ) OR ( publisher_city LIKE '%?%' ) )";
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
            }
            firstRun = true;
            finalQuery += ")";
            publishersSearched = true;
        } else if (typeOfSearch.equals("genre")) {
            finalQuery += "* FROM genre WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += "( ( genre_name LIKE '%?%' ) )";
                values.add(searchTerms[i]);
            }
            firstRun = true;
            finalQuery += ")";
            genresSearched = true;
        } else if (typeOfSearch.equals("author")) {
            finalQuery += "* FROM author WHERE(";
            for (int i = 0; i < searchTerms.length; i++) {
                if (!firstRun) {
                    finalQuery += " AND ";
                }
                firstRun = false;
                finalQuery += "( ( author_firstname LIKE '%?%' ) OR ( author_lastname LIKE '%?%' ) )";
                values.add(searchTerms[i]);
                values.add(searchTerms[i]);
            }
            finalQuery += ")";
            authorsSearched = true;
        }
        ArrayList<ArrayList<String>> queryResponse = db.getData(finalQuery, values);
        if (booksSearched) {
            for(int i=1;i<queryResponse.size();i++) {
                aBook = new Books(Integer.parseInt(queryResponse.get(i).get(0)), queryResponse.get(i).get(1), queryResponse.get(i).get(2), Integer.parseInt(queryResponse.get(i).get(3)), Integer.parseInt(queryResponse.get(i).get(4)), Integer.parseInt(queryResponse.get(i).get(5)), queryResponse.get(i).get(6), Integer.parseInt(queryResponse.get(i).get(7)));
                ArrayList<ArrayList<String>> publisherData = db.getData("SELECT * FROM publisher WHERE publisher_id = " + aBook.getBook_publisher_id() + ";", true);
                ArrayList<ArrayList<String>> genreData = db.getData("SELECT * FROM genre WHERE genre_id = " + aBook.getBook_genre_id() + ";", true);


                //aBook=new DetailedBook(queryResponse.get(1).get(1), queryResponse.get(1).get(2), queryResponse.get(1).get(4), queryResponse.get(1).get(5), queryResponse.get(1).get(6),);
            }
        }

        for (int i = 0; i < queryResponse.size(); i++) {
            for (int j = 0; j < queryResponse.get(i).size(); j++) {

            }
        }
        return new ArrayList<>();
    }

}
