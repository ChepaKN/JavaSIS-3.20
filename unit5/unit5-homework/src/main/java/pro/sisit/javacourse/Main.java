package pro.sisit.javacourse;


import pro.sisit.javacourse.PathFinder;
import pro.sisit.javacourse.inverse.BigDecimalRange;
import pro.sisit.javacourse.inverse.InverseDeliveryTask;
import pro.sisit.javacourse.inverse.Solution;
import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.valueOf;

public class Main {

    public static final Transport GAZelle = new Transport("Газ-66", valueOf(1), valueOf(100), RouteType.Railway);
    public static final Transport Plane = new Transport("Самолет", valueOf(20), valueOf(99), RouteType.Air);
    public static final Transport Tanker = new Transport("Танкер", valueOf(200), valueOf(100), RouteType.Sea);
    public static final Transport Helicopter = new Transport("Вертолет", valueOf(200), valueOf(77), RouteType.Air);

    public static void main(String[] args){

    List<Route> routes = Arrays.asList( new Route(RouteType.Air, valueOf(1)),
                                        new Route(RouteType.Road, valueOf(2)));

    DeliveryTask deliveryTask1 = new DeliveryTask("Elephant", routes, valueOf(1));
    DeliveryTask deliveryTask2 = new DeliveryTask("Water", routes, valueOf(2));
    List<Transport> transports = Arrays.asList(GAZelle, Helicopter, Tanker, Plane);
    BigDecimalRange bigDecimalRange = new BigDecimalRange(valueOf(10), true, valueOf(100), true);
    InverseDeliveryTask inverseDeliveryTask = new InverseDeliveryTask(Arrays.asList(deliveryTask1, deliveryTask2), transports, bigDecimalRange);

    InversePathFinder inversePathFinder = new InversePathFinder();
    List<Solution> allSolutions = inversePathFinder.getAllSolutions(inverseDeliveryTask);





//        List<Route> routes = Arrays.asList( new Route(RouteType.Road, valueOf(1000)),
//                                            new Route(RouteType.Road, valueOf(2000)));
//        //Задача - переслась слона, варианты путей - routes, варианты транспорта - transports
//        DeliveryTask deliveryTask = new DeliveryTask("Elephant", routes, valueOf(1));
//        List<Transport> transports = Arrays.asList(GAZelle, Helicopter, Tanker, Plane);
//
//        PathFinder pathFinder = new PathFinder();
//        Transport optimalTransport0 = pathFinder.getOptimalTransport(deliveryTask, transports);
////        optimalTransport0.getVolume();
////        Transport optimalTransport1 = pathFinder.getOptimalTransport(null, transports);
//        Transport optimalTransport2 = pathFinder.getOptimalTransport(deliveryTask, null);
////
//         int bb = 0;


    }

}

