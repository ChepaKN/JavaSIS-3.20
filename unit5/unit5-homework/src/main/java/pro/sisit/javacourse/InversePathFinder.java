package pro.sisit.javacourse;

import pro.sisit.javacourse.inverse.BigDecimalRange;
import pro.sisit.javacourse.inverse.InverseDeliveryTask;
import pro.sisit.javacourse.inverse.Solution;
import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InversePathFinder {

    /**
     * Принимает на вход InverseDeliveryTask task - обратную задачу доставки груза.
     * Мы должны по переданному в ней списку возможных задач, списку доступного транспорта,
     * а также диапазону цены определить что за задачи доставки груза могли быть решены.
     * Возвращает список решений задачи доставки груза (Solution),
     * удовлетворяющий параметру переданному параметру task:
     * 1. Транспорт должен быть доступен для решения данной задачи
     * 2. Стоимость решения задачи должна входить в диапазон цены пераметра task
     * 3. Также возвращаемый список должен быть осортирован по двум значениям:
     * - сначала по итоговой стоимости решения задачи - по убыванию
     * - затем, если цены одинаковы - по наименованию задачи доставки по алфавиту - по возрастанию
     * Если task равна null, то функция должна вернуть пустой список доступных решений.
     * Если внутри параметра task данные по возможным задачам, доступному транспорту либо по итоговой цене равны null,
     * то функция должна вернуть пустой список доступных решений.
     */
    public List<Solution> getAllSolutions(InverseDeliveryTask task) {
        // ToDo: realize me!

        List<Solution> solutions = new ArrayList<>();
        if ((task == null) || (task.getTasks() == null) || (task.getTransports() == null) || (task.getPriceRange() == null)){
            return solutions;
        }

        //Список решенных задач
        List<DeliveryTask> deliveryTasks = task.getTasks();

        //Список транспорта, возможного для решения этих
        List<Transport> transports = task.getTransports();

        //Диапазон затрат
        BigDecimalRange range = task.getPriceRange();

        //todo: как бы тут прикрутить стрим...
        for (DeliveryTask deliveryTask : deliveryTasks) {

            Map<RouteType, BigDecimal> routesMap = routesToMap(deliveryTask);
            List<Transport> availableTransport = getAvailableTransport(deliveryTask, transports, range);

            //Добавим в коллекцию все решения
            if (availableTransport.size() > 0) {
                for (Transport t : availableTransport) {
                    solutions.add(new Solution(deliveryTask, t, t.getPrice().multiply(routesMap.get(t.getType()))));
                }
            }
        }

        //Сортируем список
        Comparator<Solution> solutionComparator = Comparator.comparing(Solution::getPrice).reversed().
                thenComparing(solution -> solution.getDeliveryTask().getName());

        solutions = solutions.stream()
                .sorted(solutionComparator)
                .collect(Collectors.toList());

        return solutions;

    }

    private Map<RouteType, BigDecimal> routesToMap(DeliveryTask deliveryTask){
            //Упакуем в map, чтобы удабно было узнать длину маршрута для заданного типа по ключу RouteType
            Map<RouteType, BigDecimal> routesMap = new HashMap<>();
            for (Route r : deliveryTask.getRoutes()) {
                routesMap.put(r.getType(), r.getLength());
            }
            return routesMap;
    }


    private List<RouteType> getAvailableRoutesType(DeliveryTask deliveryTask) {
        return deliveryTask.getRoutes()
                .stream()
                .map(Route::getType)
                .collect(Collectors.toList());
    }


    private List<Transport> getAvailableTransport(DeliveryTask task, List<Transport> transports, BigDecimalRange range) {

        List<RouteType> availableRoutesType = getAvailableRoutesType(task);
        Map<RouteType, BigDecimal> routesMap = routesToMap(task);

        return transports.stream()
                .filter(transport -> availableRoutesType.contains(transport.getType()))                                     //Фильтрация по типу
                .filter(transport -> transport.getVolume().compareTo(task.getVolume()) >= 0)                                //Фильтрация по объему
                .filter(transport -> range.inRange(transport.getPrice().multiply(routesMap.get(transport.getType()))))      //Фильтрация по цене
                .collect(Collectors.toList());

    }
}



