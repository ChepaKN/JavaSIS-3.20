package pro.sisit.javacourse;

import lombok.Data;

/**
 * Производитель продукта
 */

@Data
public class Producer {

    /**
     * Уникальный идентификатор
     */
    private final long id;

    /**
     * Наименование
     */
    private final String name;
}
