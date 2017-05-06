package dataobjects;

/**
 * @author Boyz
 */
public class Author_Book {
    public int getBook_id() {
        return book_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public Author_Book(int book_id, int author_id) {

        this.book_id = book_id;
        this.author_id = author_id;
    }

    private int book_id;
    private int author_id;
}
