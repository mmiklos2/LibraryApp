package dataobjects;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Boyz
 */

// ArrayList autora u jedan dugi string TODO
public class DetailedBook {
    private SimpleStringProperty book_isbn;
    private SimpleStringProperty book_title;
    private SimpleStringProperty book_publisher_year;
    private SimpleStringProperty book_copies;
    private SimpleStringProperty book_location;
    private SimpleStringProperty book_genre;
    private SimpleStringProperty publisher_name;
    private SimpleStringProperty authors;
    private SimpleStringProperty dateDue;

    public DetailedBook(SimpleStringProperty book_isbn, SimpleStringProperty book_title, int book_publisher_year, int book_copies, SimpleStringProperty book_location, SimpleStringProperty book_genre, SimpleStringProperty publisher_name, SimpleStringProperty authors, SimpleStringProperty dateDue) {
        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_publisher_year = new SimpleStringProperty(Integer.toString(book_publisher_year));
        this.book_copies = new SimpleStringProperty(Integer.toString(book_copies));
        this.book_location = book_location;
        this.book_genre = book_genre;
        this.publisher_name = publisher_name;
        this.authors = authors;
        this.dateDue = dateDue;
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

    public SimpleStringProperty getBook_publisher_year() {
        return book_publisher_year;
    }

    public SimpleStringProperty getBook_copies() {
        return book_copies;
    }

    public SimpleStringProperty getBook_location() {
        return book_location;
    }

    public void printInfo() {
        System.out.println("AUTHOR:  " + " PUBLISHER:  " + " TITLE:  " + " GENRE: " + " PUBLISHER YEAR: " + " COPIES: " + "  LOCATION:  " + " ISBN:  ");
        System.out.println(getAuthor().getValue() + "   " + getPublisher_name().getValue() + "   " + getBook_title().getValue() + "   " + getBook_genre().getValue() + "   " + getBook_publisher_year().getValue() + "   " + getBook_copies().getValue() + "   " + getBook_location().getValue() + " " + getBook_isbn().getValue());

    }

    public SimpleStringProperty getDateDue() {
        return dateDue;
    }


}
