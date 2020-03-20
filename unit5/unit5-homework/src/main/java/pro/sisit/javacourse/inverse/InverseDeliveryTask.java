package pro.sisit.javacourse.inverse;

import lombok.Data;
import pro.sisit.javacourse.optimal.Transport;
import pro.sisit.javacourse.optimal.DeliveryTask;

import java.util.List;

/**
 * Обратная задача по доставке груза
 */
@Data
public class InverseDeliveryTask {

    /**
     * Список задач по доставке груза, которые могли быть решены
     */
    private final List<DeliveryTask> tasks;

    /**
     * Виды транспорта, которые могли быть использованы для решения задач доставки
     */
    private final List<Transport> transports;

    /**
     * Диапазон цены решенной задачи
     */
    private final BigDecimalRange priceRange;
}