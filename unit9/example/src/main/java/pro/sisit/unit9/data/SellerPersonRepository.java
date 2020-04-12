package pro.sisit.unit9.data;

import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.SellerPerson;

import java.util.List;

public interface SellerPersonRepository {
    List<SellerPerson> findByBook(Book book);
}
