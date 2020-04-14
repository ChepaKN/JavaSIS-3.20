package pro.sisit.unit9.service;

import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Buyer;

import java.math.BigDecimal;

public interface SaleTransaction {
    void saleTransaction(Book book, Buyer buyer, BigDecimal price);
    BigDecimal totalCostByBook(Book book);
    BigDecimal totalCostByBuyer(Buyer buyer);
}
