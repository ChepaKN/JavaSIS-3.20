package pro.sisit.unit9.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pro.sisit.unit9.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>,
        PagingAndSortingRepository<Book, Long>,
        JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book>,
        BookComplexQueryRepository {

    List<Book> findByYear(Integer year);

    @Query("select aob.book from "
            + "AuthorOfBook aob "
            + "join aob.author "
            + "where aob.author.lastname = ?1")
    List<Book> findByAuthor(String lastname);

}
