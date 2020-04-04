package pro.sisit.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pro.sisit.adapter.impl.CSVAdapter;
import pro.sisit.model.Book;
import  pro.sisit.model.*;

// TODO: 2. Описать тестовые кейсы

public class CSVAdapterTest {

    public CSVAdapterTest() throws IOException {
    }

    @Before
    public void createFile() throws IOException {
//    public void createFile() throws IOException, IllegalAccessException {
        // TODO: создать и заполнить csv-файл для сущности Author
        // TODO: создать и заполнить csv-файл для сущности Book
//Author
        File fileAuthors = Paths.get("authors.csv").toFile();
        fileAuthors.createNewFile();
        Author author1 = new Author("Булгаков", "Нью-йорк");
        Author author2 = new Author("Салтыков-Щедрин", "Бруклин");

        IOAdapter<Author> authorIOAdapter;
        try (BufferedReader readerAuthor = new BufferedReader(new FileReader(fileAuthors));
             BufferedWriter writerAuthor = new BufferedWriter(new FileWriter(fileAuthors))) {

            authorIOAdapter = new CSVAdapter<>(Author.class, readerAuthor, writerAuthor, ";");
            authorIOAdapter.append(author1);
            authorIOAdapter.append(author2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


//Book
        File fileBooks = Paths.get("books.csv").toFile();
        fileBooks.createNewFile();
        Book book1 = new Book("Бумер", "Троицкий", "Драма/Криминал", "978-5-224-04839-7");
        Book book2 = new Book("Унесенные ветром", "Маргарет Митчел", "Роман-эпопея", "5-17-041108-1");

        try(BufferedReader readerBook= new BufferedReader(new FileReader(fileBooks));
            BufferedWriter writerBook = new BufferedWriter(new FileWriter(fileBooks))) {

            IOAdapter<Book> bookIOAdapter = new CSVAdapter<>(Book.class, readerBook, writerBook, ";");
            bookIOAdapter.append(book1);
            bookIOAdapter.append(book2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // * По желанию можете придумать и свои сущности
    }

    @After
    public void deleteFile() {
        // TODO: удалить файлы после тестирования
        Paths.get("authors.csv").toFile().delete();
        Paths.get("books.csv").toFile().delete();
    }

    @Test
    public void testRead() {

       Path bookFilePath = Paths.get("books.csv");

       try( BufferedReader bookReader = new BufferedReader(new FileReader(bookFilePath.toFile()));
            BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookFilePath.toFile(), true))) {

           CSVAdapter<Book> bookCsvAdapter =
                   new CSVAdapter(Book.class, bookReader, bookWriter, ";");

           Book book1 = bookCsvAdapter.read(0);
           assertEquals("Троицкий", book1.getAuthor());
           assertEquals("Бумер", book1.getName());
           assertEquals("978-5-224-04839-7", book1.getIsbn());
           assertEquals("Драма/Криминал", book1.getGenre());

           Book expectedBook0 = new Book(
                   "Бумер",
                   "Троицкий",
                   "Драма/Криминал",
                   "978-5-224-04839-7");

           Book actualBook0 = bookCsvAdapter.read(0);
           assertEquals(expectedBook0, actualBook0);
       } catch (IOException | IllegalAccessException | InstantiationException e) {
           e.printStackTrace();
       }

           // TODO: написать тесты для проверки сущности автора
           Path authorFilePath = Paths.get("authors.csv");

           try( BufferedReader authorReader = new BufferedReader(new FileReader(authorFilePath.toFile()));
                BufferedWriter authorWriter = new BufferedWriter(new FileWriter(authorFilePath.toFile(), true))) {

                CSVAdapter<Author> authorCsvAdapter = new CSVAdapter(Author.class, authorReader, authorWriter, ";");

                Author author1 = authorCsvAdapter.read(0);

                assertEquals("Булгаков", author1.getName());
                assertEquals("Нью-йорк", author1.getBirthPlace());

                Author expectedAuthor = new Author("Булгаков", "Нью-йорк");
                Author actualAuthor = authorCsvAdapter.read(0);
                assertTrue(expectedAuthor.equals(actualAuthor));

            } catch (IOException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

    }


    @Test
    public void testAppend()  {

        Path bookFilePath = Paths.get("books.csv");

        try( BufferedReader bookReader = new BufferedReader(new FileReader(bookFilePath.toFile()));
             BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookFilePath.toFile(), true))) {

            CSVAdapter<Book> bookCsvAdapter = new CSVAdapter(Book.class, bookReader, bookWriter, ";");

            Book newBook = new Book(
                    "Чертоги разума. Убей в себе идиота!",
                    "Андрей Курпатов",
                    "Психология",
                    "978-5-906902-91-7");

            int bookIndex = bookCsvAdapter.append(newBook);
            Book bookAtIndex = bookCsvAdapter.read(bookIndex);
            assertEquals(newBook, bookAtIndex);

        } catch (IllegalAccessException | IOException | InstantiationException e) {
            e.printStackTrace();
        }
        // TODO: написать тесты для проверки сущности автора

        Path authorFilePath = Paths.get("authors.csv");

        try (BufferedReader authorReader = new BufferedReader(new FileReader(authorFilePath.toFile()));
             BufferedWriter authorWriter = new BufferedWriter(new FileWriter(authorFilePath.toFile(), true))) {

            CSVAdapter<Author> authorCsvAdapter = new CSVAdapter(Author.class, authorReader, authorWriter, ";");

            Author author = new Author("Дарья Донцова", "Воркута");

            int authorIndex = authorCsvAdapter.append(author);
            Author authorAtIndex = authorCsvAdapter.read(authorIndex);
            assertEquals(author, authorAtIndex);
        }catch (IllegalAccessException | IOException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
