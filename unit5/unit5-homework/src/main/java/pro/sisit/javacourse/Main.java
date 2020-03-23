package pro.sisit.javacourse;


import pro.sisit.javacourse.PathFinder;
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

//    List<Route> routes =
//    DeliveryTask deliveryTask = new DeliveryTask("Слон", );
    public static final Transport GAZelle = new Transport("Газ-66", valueOf(1), valueOf(100), RouteType.Railway);
    public static final Transport Plane = new Transport("Самолет", valueOf(20), valueOf(150), RouteType.Air);
    public static final Transport Tanker = new Transport("Танкер", valueOf(200), valueOf(100), RouteType.Sea);
    public static final Transport Helicopter = new Transport("Вертолет", valueOf(200), valueOf(100), RouteType.Air);

    public static void main(String[] args){


        //ArrayList - т.к. его можно изменять
        List<Route> routes = new ArrayList<>(Arrays.asList( new Route(RouteType.Air, valueOf(1000)),
                                            new Route(RouteType.Railway, valueOf(2000))));
        //Задача - переслась слона, варианты путей - routes, варианты транспорта - transports
        DeliveryTask deliveryTask = new DeliveryTask("Elephant", routes, valueOf(1));
        List<Transport> transports = new ArrayList<>(Arrays.asList(GAZelle, Helicopter, Tanker, Plane));

        PathFinder pathFinder = new PathFinder();
        Transport optimalTransport0 = pathFinder.getOptimalTransport(deliveryTask, transports);
        Transport optimalTransport1 = pathFinder.getOptimalTransport(null, transports);
        Transport optimalTransport2 = pathFinder.getOptimalTransport(deliveryTask, null);

         int bb = 0;


    }

}

