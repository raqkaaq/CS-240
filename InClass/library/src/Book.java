import java.util.Objects;

public class Book {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pubYear == book.pubYear && Objects.equals(author, book.author) && Objects.equals(title, book.title) && genre == book.genre;
    }

    @Override
    public int hashCode() {
        return (this.author.hashCode() + this.title.hashCode()) * pubYear + genre.hashCode();
        //Doesnt particularly matter the operations used, as long as each value is used, and you get more or less random numbers
    }

    public static void main (String[] args){
        //call new everytime in creating a new object
        Book b1 = new Book("Raqkaaq", "Discovery of java", 2022, Genre.HISTORY);
        Book b2 = new Book("Raqkaaq", "Discovery of java", 2022, Genre.HISTORY);

        boolean bool = (b1 == b2); //Only compares if is exactly the same object by address
        boolean bool2 = b1.equals(b2); //compare by value instead of by address
        System.out.println(bool);
        System.out.println(bool2);
        System.out.println(b2.getClass());

    }
    public static int nextID = 0;
    private static int getNextID(){
        return ++nextID;
    }
    private int id;
    private String author;
    private String title;
    private int pubYear;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", pubYear=" + pubYear +
                ", genre=" + genre +
                '}';
    }

    private Genre genre;

    public Book(String author, String title, int pubYear, Genre genre) {
        this.author = author;
        this.title = title;
        this.pubYear = pubYear;
        this.genre = genre;
    } //in constructors the conventions is to use the names for the variables as the same names as the members, then use this. to access the member

    /*
        From class Object:
            override toString()
            override Equals()
     */


    public Book(String author, String title) {
        this(author, title, 0, Genre.UNKNOWN);
    }

    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPubYear() {
        return pubYear;
    }

    public void setPubYear(int pubYear) {
        this.pubYear = pubYear;
    }
//anything in java.lang package you can you without importing, usually everything else has to be imported

}
