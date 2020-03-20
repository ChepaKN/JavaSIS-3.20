package pro.sisit.javacourse.optimal;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Транспорт для доставки груза
 */
@Data
public class Transport {

    /**
     * Название
     */
    private final String name;
    /**
     * Допустимый объем груза
     */
    private final BigDecimal volume;
    /**
     * Стоимость перевозки груза на единицу длины маршрута
     */
    private final BigDecimal price;
    /**
     * Тип маршрута, по которому может перемещаться транспорт
     */
    private final RouteType type;
}
