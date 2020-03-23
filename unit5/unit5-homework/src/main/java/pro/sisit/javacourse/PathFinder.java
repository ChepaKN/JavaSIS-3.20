package pro.sisit.javacourse;

import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import javax.crypto.spec.PSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PathFinder {

    /**
     * Возвращает оптимальный транспорт для переданной задачи.
     * Если deliveryTask равна null, то оптимальеый транспорт тоже равен null.
     * Если список transports равен null, то оптимальеый транспорт тоже равен null.
     */
    //Return optional!
    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        // ToDo: realize me!
        try {

            if(deliveryTask == null) return null;
            if(transports == null) return null;

            //Тип транспорта, разрешенный для данного груза
            List<RouteType> availableRoutesType =  deliveryTask.getRoutes()
                                                                .stream()
                                                                .map(Route::getType)
                                                                .collect(Collectors.toList());



            //todo: как бы  переделать на стрим...
            //Скопируем тот транспорт, который подходит для  данного груза
            List<Transport> availableTransport = new ArrayList<>();
            for(Transport t : transports){
                if(availableRoutesType.contains(t.getType())){
                    availableTransport.add(t);
                }
            }

            //Не нашлось подходящего транспорта
            if(availableTransport.isEmpty()) return null;

            //Отсортируем коллекцию по цене
            List<Transport> sortedTransport = availableTransport.stream()
                    .sorted(Comparator.comparing(Transport::getPrice))
                    .collect(Collectors.toList());

            return sortedTransport.get(0);


        }catch (Exception e){
        }
        return null;
    }
}
