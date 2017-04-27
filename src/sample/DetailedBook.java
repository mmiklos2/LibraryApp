package sample;

import java.util.ArrayList;

/**
 * @author Boyz
 */
public class DetailedBook {
    private String book_isbn;
    private String book_title;
    private int book_publisher_year;
    private int book_copies;
    private String book_location;
    private int book_genre;
    private String publisher_name;
    private ArrayList<String> authors;

    public DetailedBook(String book_isbn, String book_title, int book_publisher_year, int book_copies, String book_location, int book_genre, String publisher_name, ArrayList<String> authors) {
        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_publisher_year = book_publisher_year;
        this.book_copies = book_copies;
        this.book_location = book_location;
        this.book_genre = book_genre;
        this.publisher_name = publisher_name;
        this.authors=authors;;
    }

    public int getBook_genre() {
        return book_genre;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public ArrayList<String> getAuthor() {
        return authors;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public String getBook_title() {
        return book_title;
    }

    public int getBook_publisher_year() {
        return book_publisher_year;
    }

    public int getBook_copies() {
        return book_copies;
    }

    public String getBook_location() {
        return book_location;
    }

}
