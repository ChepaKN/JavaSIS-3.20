package pro.sisit.unit9.service;

import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Buyer;
import pro.sisit.unit9.entity.Seller;

import java.math.BigDecimal;

public interface SaleTransaction {
    void saleTransaction(Book book, Buyer buyer, Seller seller, BigDecimal price);
    BigDecimal totalCostByBook(Book book);
    BigDecimal totalCostByBuyer(Buyer buyer);
    BigDecimal totalCostBySeller(Seller seller);
}
