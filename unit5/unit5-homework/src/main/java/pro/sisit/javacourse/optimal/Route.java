package pro.sisit.javacourse.optimal;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Маршрут
 */
@Data
public class Route {

    /**
     * Тип маршрута
     */
    private final RouteType type;

    /**
     * Длина пути
     */
    private final BigDecimal length;
}
