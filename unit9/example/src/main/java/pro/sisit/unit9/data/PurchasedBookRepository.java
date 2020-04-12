package pro.sisit.unit9.data;

import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.BuyerPerson;
import pro.sisit.unit9.entity.PurchasedBook;
import pro.sisit.unit9.entity.SellerPerson;

import java.util.List;

public interface PurchasedBookRepository extends CrudRepository<PurchasedBook, Long> {
    List<PurchasedBook> findByBuyerPerson(BuyerPerson buyer);
    List<PurchasedBook> findBySellerPerson(SellerPerson sellerPerson);
    List<PurchasedBook> findByBook(Book book);
}
