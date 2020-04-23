package pro.sisit.unit9.data;

import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Buyer;
import pro.sisit.unit9.entity.PurchasedBook;
import pro.sisit.unit9.entity.Seller;

import java.util.List;

public interface PurchasedBookRepository extends CrudRepository<PurchasedBook, Long> {

    List<PurchasedBook> findByBook(Book book);
    List<PurchasedBook> findByBuyer(Buyer buyer);
    List<PurchasedBook> findBySeller(Seller seller);
}
