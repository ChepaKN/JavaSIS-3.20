package pro.sisit.javacourse;

import pro.sisit.javacourse.inverse.BigDecimalRange;
import pro.sisit.javacourse.inverse.InverseDeliveryTask;
import pro.sisit.javacourse.inverse.Solution;
import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
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

        //Если необходимо, возвращаем пустой список
        if(isInverseDeliveryTaskSomeNull(task)){
            return solutions;
        }

        //Список решенных задач
        List<DeliveryTask> deliveryTasks = task.getTasks();

        //Список транспорта, возможного для решения этих задач
        List<Transport> transports = task.getTransports();

        //Диапазон затрат
        BigDecimalRange range = task.getPriceRange();

        deliveryTasks
                //Здесь: имеем весь доступный и подходящий транпорт для решения задачи перевозки
                .forEach(deliveryTask -> getAvailableTransport(deliveryTask, transports, range)
                //Добавляем все возможные решения в список
                .forEach(transport -> solutions.add(new Solution(deliveryTask, transport, getTotalPriceByRoute(deliveryTask, transport)))));

        //Сортируем список
        Comparator<Solution> solutionComparator = Comparator.comparing(Solution::getPrice).reversed().
                thenComparing(solution -> solution.getDeliveryTask().getName());

        return solutions.stream()
                .sorted(solutionComparator)
                .collect(Collectors.toList());

    }

    private List<Transport> getAvailableTransport(DeliveryTask task, List<Transport> transports, BigDecimalRange range) {
        //Метод возвращает доступный для данного решения транспорт
        return transports.stream()
                .filter(transport -> filterByTransportType(task, transport))                 //Фильтрация по типу
                .filter(transport -> filterByVolume(task, transport))                        //Фильтрация по объему
                .filter(transport -> filterByTotalRoutePrice(range, task, transport))        //Фильтрация по цене
                .collect(Collectors.toList());
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

    private boolean filterByTotalRoutePrice(BigDecimalRange range, DeliveryTask deliveryTask, Transport transport){
        BigDecimal totalPrise = getTotalPriceByRoute(deliveryTask, transport);
        if(Optional.ofNullable(totalPrise).isPresent()){
            return range.inRange(totalPrise);
        }else return false;
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

    private boolean isInverseDeliveryTaskSomeNull(InverseDeliveryTask task){
        if(!Optional.ofNullable(task).isPresent()){
            return true;
        }
        if(!Optional.ofNullable(task.getTransports()).isPresent()){
            return true;
        }
        if(!Optional.ofNullable(task.getPriceRange()).isPresent()){
            return true;
        }
        if(!Optional.ofNullable(task.getTasks()).isPresent()){
            return true;
        }
        return false;
    }
}



