package pro.sisit.javacourse.data;

import lombok.Data;
import pro.sisit.javacourse.Producer;
import pro.sisit.javacourse.Product;
import pro.sisit.javacourse.ProductType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Тестовый продукт (обертка-композиция)
 */
@Data
public class TestProduct {

    /**
     * Продукт
     */
    private final Product product;

    /**
     * Рассчитанная доля НДС в цене продукта
     */
    private final BigDecimal tax;

    public TestProduct(long id, String name, ProductType productType, BigDecimal price, Producer producer) {
        this.product = new Product(id, name, productType, producer, price);
        this.tax = calculateTax(price);
    }

    private BigDecimal calculateTax(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(20.00)).divide(BigDecimal.valueOf(120.00), RoundingMode.HALF_EVEN);
    }
}
