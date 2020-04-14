package pro.sisit.unit9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.data.PurchasedBookRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Buyer;
import pro.sisit.unit9.entity.PurchasedBook;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SaleTransactionsImpl implements SaleTransaction{

    private final PurchasedBookRepository purchasedBookRepository;

    @Override
    public void saleTransaction(Book book, Buyer buyer, BigDecimal price){

        PurchasedBook purchasedBook = new PurchasedBook();
        purchasedBook.setBook(book);
        purchasedBook.setBuyer(buyer);
        purchasedBook.setCost(price);
        purchasedBookRepository.save(purchasedBook);

    }

    @Override
    public BigDecimal totalCostByBook (Book book) {
        List<PurchasedBook> purchasedBooks = purchasedBookRepository.findByBook(book);
        return purchasedBookRepository.findByBook(book)
                .stream()
                .map(PurchasedBook::getCost)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public BigDecimal totalCostByBuyer (Buyer buyer) {
        return purchasedBookRepository.findByBuyer(buyer).stream()
                .map(PurchasedBook::getCost)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

}