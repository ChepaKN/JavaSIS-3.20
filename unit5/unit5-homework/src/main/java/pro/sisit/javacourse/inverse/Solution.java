package pro.sisit.javacourse.inverse;

import lombok.Data;
import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;

/**
 * Решение задачи доставки груза.
 * Композиция задачи и выбранного типа транспорта.
 */
@Data
public class Solution {

    /**
     * Решаемая задача
     */
    private final DeliveryTask deliveryTask;

    /**
     * Транспорт для решения
     */
    private final Transport transport;

    /**
     * Итоговая стоимость
     */
    private final BigDecimal price;

    @Override
    public String toString() {
        return String.format("\tSolution of delivery task '%s' with transport '%s' by price: '%s'",
                deliveryTask.getName(), transport.getName(), price);
    }
}
