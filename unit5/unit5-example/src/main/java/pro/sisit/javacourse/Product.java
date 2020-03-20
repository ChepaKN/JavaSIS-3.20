package pro.sisit.javacourse;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Продукт
 */
@Data
public class Product {

    /**
     * Уникальный идентификатор
     */
    private final long id;

    /**
     * Наименование продукта
     */
    private final String name;

    /**
     * Тип продукта
     */
    private final ProductType type;

    /**
     * Производитель
     */
    private final Producer producer;

    /**
     * Цена за 1шт
     */
    private final BigDecimal price;

    @Override
    public String toString() {
        return String.format(
                "Продукт('%s' \\ '%s'): '%s', цена: '%s';",
                getProducer().getName(), getType().getLocalName(),
                getName(), getPrice()
        );
    }

}
