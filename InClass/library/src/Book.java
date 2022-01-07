import java.util.Objects;

public class Book {
    public static void main (String[] args){
        //call new everytime in creating a new object
        Book b1 = new Book("Raqkaaq", "Discovery of java", 2022, Genre.HISTORY);
        Book b2 = new Book("Raqkaaq", "Discovery of java", 2022, Genre.HISTORY);

        boolean bool = (b1 == b2); //Only compares if is exactly the same object by address
        boolean bool2 = b1.equals(b2); //compare by value instead of by address
        System.out.println(bool);
        System.out.println(bool2);

    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if(this == o){
            return true;
        }
        if(this.getClass() != o.getClass()){
            return false;
        }
        Book b = (Book) o;
        return (this.author.equals(b.author) && this.title.equals(b.title) && (this.pubYear == b.pubYear) && this.genre.equals(b.genre));
    }

    private String author;
    private String title;
    private int pubYear;
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
