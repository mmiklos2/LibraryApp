package sample;

public class Books {
    private int book_id;
    private String book_isbn;
    private String book_title;
    private int book_publisher_id;
    private int book_publisher_year;
    private int book_copies;
    private String book_location;
    private int book_genre_id;

    public Books() {

    }

    public Books(int book_id, String book_isbn, String book_title, int book_publisher_id, int book_publisher_year, int book_copies, String book_location, int book_genre_id) {
        this.book_id = book_id;
        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_publisher_id = book_publisher_id;
        this.book_publisher_year = book_publisher_year;
        this.book_copies = book_copies;
        this.book_location = book_location;
        this.book_genre_id = book_genre_id;
    }

    public Books(String book_isbn, String book_title, int book_publisher_id, int book_publisher_year, int book_copies, String book_location, int book_genre_id) {

        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_publisher_id = book_publisher_id;
        this.book_publisher_year = book_publisher_year;
        this.book_copies = book_copies;
        this.book_location = book_location;
        this.book_genre_id = book_genre_id;
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

    public int getBook_genre_id() {
        return book_genre_id;
    }

    public void setBook_genre_id(int book_gener_id) {
        this.book_genre_id = book_gener_id;
    }
}
