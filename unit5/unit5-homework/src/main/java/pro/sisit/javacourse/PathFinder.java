package pro.sisit.javacourse;

import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {

    /**
     * Возвращает оптимальный транспорт для переданной задачи.
     * Если deliveryTask равна null, то оптимальеый транспорт тоже равен null.
     * Если список transports равен null, то оптимальеый транспорт тоже равен null.
     */
    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        // ToDo: realize me!

        Comparator<Transport> transportComparator =  Comparator.comparing(transport -> getTotalPriceByRoute(deliveryTask, transport));
        return Optional.ofNullable(deliveryTask)
                .flatMap(data -> Optional.ofNullable(transports))
                .flatMap(data -> getAvailableTransport(deliveryTask, data)
                        .stream()
                        .min(transportComparator))
                .orElse(null);
    }

    private List<Transport> getAvailableTransport(DeliveryTask deliveryTask, List<Transport> transports){
        return
        transports.stream()
                .filter(transport -> filterByTransportType(deliveryTask, transport))     //Фильтрация по типу
                .filter(transport -> filterByVolume(deliveryTask, transport))           //Фильтрация по объему
                .collect(Collectors.toList());
    }

    private boolean filterByTransportType(DeliveryTask deliveryTask, Transport transport){
        return
        deliveryTask.getRoutes()
                .stream()
                .map(Route::getType)
                .collect(Collectors.toList())
                .contains(transport.getType());
    }

    private boolean filterByVolume(DeliveryTask deliveryTask, Transport transport){
        return transport.getVolume().compareTo(deliveryTask.getVolume()) >= 0;
    }

    private BigDecimal getTotalPriceByRoute(DeliveryTask deliveryTask, Transport transport){
        return
                deliveryTask.getRoutes()
                        .stream()
                        .filter(route -> route.getType().equals(transport.getType()))
                        .findAny()
                        .map(route -> route.getLength().multiply(transport.getPrice()))
                        .orElse(null);
    }

}

