package pro.sisit.unit9.data;

import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.AuthorOfBook;

public interface AuthorOfBookRepository extends CrudRepository<AuthorOfBook, Long> {
}
