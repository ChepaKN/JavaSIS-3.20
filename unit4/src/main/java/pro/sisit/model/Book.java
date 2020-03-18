package pro.sisit.model;

import pro.sisit.adapter.CSV_converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book implements CSV_converter {

    private String name;
    private String author;
    private String genre;
    private String isbn;

    public  Book(){

    }

    public Book(String name, String author, String genre, String isbn) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return getName().equals(book.getName()) &&
                getAuthor().equals(book.getAuthor()) &&
                getGenre().equals(book.getGenre()) &&
                getIsbn().equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getGenre(), getIsbn());
    }

    @Override
    public String toString(){
        return  name +"\t"+  author +"\t"+ genre +"\t"+ isbn;
    }

    @Override
    public List<String> fromFieldsToCSV() {
        List<String> fields = new ArrayList<>();
        fields.add(name);
        fields.add(author);
        fields.add(genre);
        fields.add(isbn);
        return fields;
    }

    @Override
    public void fromCSVToFields(List<String> fromCSV) {
        name    = fromCSV.get(0);
        author  = fromCSV.get(1);
        genre   = fromCSV.get(2);
        isbn    = fromCSV.get(3);
    }

}
