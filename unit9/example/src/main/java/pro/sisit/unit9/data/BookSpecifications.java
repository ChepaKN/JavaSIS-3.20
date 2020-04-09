package pro.sisit.unit9.data;

import org.springframework.data.jpa.domain.Specification;
import pro.sisit.unit9.entity.Book;

public class BookSpecifications {
    public static Specification<Book> yearLessThan(int year) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("year"), year);
    }

    public static Specification<Book> yearGreaterThan(int year) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("year"), year);
    }

    public static Specification<Book> byYearRange(int startYear, int finishYear) {
        return yearLessThan(finishYear).and(yearGreaterThan(startYear));
    }
}
