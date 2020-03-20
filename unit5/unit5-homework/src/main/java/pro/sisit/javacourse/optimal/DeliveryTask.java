package pro.sisit.javacourse.optimal;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Задача по доставке груза из одной точки в другую
 */
@Data
public class DeliveryTask {

    /**
     * Название груза
     */
    private final String name;

    /**
     * Список доступных маршрутов для доставки
     */
    private final List<Route> routes;

    /**
     * Объем груза
     */
    private final BigDecimal volume;
}
