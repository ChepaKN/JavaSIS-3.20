package pro.sisit.javacourse;

import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import javax.crypto.spec.PSource;
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

        if (deliveryTask == null) return null;
        if (transports == null) return null;

        //Тип маршрута, разрешенный для данного груза
        List<RouteType> availableRoutesType = deliveryTask.getRoutes()
                .stream()
                .map(Route::getType)
                .collect(Collectors.toList());

//        List<Optional<Transport>> listT =  transports.stream()
//                .map(Optional::of)
//                .filter(transport -> availableRoutesType.contains(transport.getType()))                 //Фильтрация по типу
//                .filter(transport -> transport.getVolume().compareTo(deliveryTask.getVolume()) >= 0)    //Фильтрация по объему
//                .collect(Collectors.toList());

        //Скопируем тот транспорт, который подходит для  данного груза
        List<Transport> availableTransport = transports.stream()
                .filter(transport -> availableRoutesType.contains(transport.getType()))                 //Фильтрация по типу
                .filter(transport -> transport.getVolume().compareTo(deliveryTask.getVolume()) >= 0)    //Фильтрация по объему
                .collect(Collectors.toList());

        //Не нашлось подходящего транспорта
        if(availableTransport.isEmpty()) return null;

        //Упакуем в map, чтобы удабно было узнать длину маршрута для заданного типа по ключу RouteType
        Map<RouteType, BigDecimal> routesMap = new HashMap<>();
        for (Route r : deliveryTask.getRoutes()) {
            routesMap.put(r.getType(), r.getLength());
        }

        //Отсортируем коллекцию по критерию "lenght * price"
        List<Transport> sortedTransport = availableTransport.stream()
                .sorted(Comparator.comparing(o -> o.getPrice().multiply(routesMap.get(o.getType()))))
                .collect(Collectors.toList());

        //todo: Прикрутить Optional...

        return  sortedTransport.get(0);
    }
}
