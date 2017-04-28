package sample;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * @author Boyz
 */

// ArrayList autora u jedan dugi string TODO
public class DetailedBook {
    private SimpleStringProperty book_isbn;
    private SimpleStringProperty book_title;
    private int book_publisher_year;
    private int book_copies;
    private SimpleStringProperty book_location;
    private SimpleStringProperty book_genre;
    private SimpleStringProperty publisher_name;
    private SimpleStringProperty authors;

    public DetailedBook(SimpleStringProperty book_isbn, SimpleStringProperty book_title, int book_publisher_year, int book_copies, SimpleStringProperty book_location, SimpleStringProperty book_genre, SimpleStringProperty publisher_name, SimpleStringProperty authors) {
        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_publisher_year = book_publisher_year;
        this.book_copies = book_copies;
        this.book_location = book_location;
        this.book_genre = book_genre;
        this.publisher_name = publisher_name;
        this.authors=authors;;
    }

    public SimpleStringProperty getBook_genre() {
        return book_genre;
    }

    public SimpleStringProperty getPublisher_name() {
        return publisher_name;
    }

    public SimpleStringProperty getAuthor() {
        return authors;
    }

    public SimpleStringProperty getBook_isbn() {
        return book_isbn;
    }

    public SimpleStringProperty getBook_title() {
        return book_title;
    }

    public int getBook_publisher_year() {
        return book_publisher_year;
    }

    public int getBook_copies() {
        return book_copies;
    }

    public SimpleStringProperty getBook_location() {
        return book_location;
    }

}
