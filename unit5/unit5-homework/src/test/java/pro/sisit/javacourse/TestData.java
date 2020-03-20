package pro.sisit.javacourse;

import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import java.util.Arrays;

import static java.math.BigDecimal.valueOf;


public class TestData {

    public static final Transport GAZelle = new Transport("Газ-66", valueOf(1), valueOf(1), RouteType.Road);
    public static final Transport Plane = new Transport("Самолет", valueOf(20), valueOf(50), RouteType.Air);
    public static final Transport Tanker = new Transport("Танкер", valueOf(200), valueOf(100), RouteType.Sea);
    public static final Transport Train = new Transport("Поезд", valueOf(100), valueOf(10), RouteType.Railway);
    public static final Transport SemiTrailerTruck = new Transport("Фура", valueOf(5), valueOf(2), RouteType.Road);
    public static final Transport BelAZ = new Transport("БелАЗ-75600", valueOf(10000), valueOf(50), RouteType.Road);
    public static final Transport Submarine = new Transport("Подводная лодка", valueOf(5000), valueOf(100), RouteType.Sea);

    public static final DeliveryTask KingKongDelivery =
            new DeliveryTask(
                    "Кинг-Конг",
                    Arrays.asList(
                            new Route(RouteType.Air, valueOf(2000)),
                            new Route(RouteType.Sea, valueOf(2500))
                    ),
                    valueOf(150)
            );

    public static final DeliveryTask BreadDelivery =
            new DeliveryTask(
                    "Хлеб",
                    Arrays.asList(
                            new Route(RouteType.Air, valueOf(50)),
                            new Route(RouteType.Road, valueOf(100)),
                            new Route(RouteType.Railway, valueOf(70))
                    ),
                    valueOf(1)
            );

    public static final DeliveryTask CarsDelivery =
            new DeliveryTask(
                    "Автомобили 'BMW X5'",
                    Arrays.asList(
                            new Route(RouteType.Air, valueOf(2000)),
                            new Route(RouteType.Sea, valueOf(2500)),
                            new Route(RouteType.Road, valueOf(4000)),
                            new Route(RouteType.Railway, valueOf(3000))
                    ),
                    valueOf(100));


    public static final DeliveryTask SmartphoneDelivery =
            new DeliveryTask(
                    "Смартфоны 'Apple IPhone 11'",
                    Arrays.asList(
                            new Route(RouteType.Air, valueOf(2000)),
                            new Route(RouteType.Sea, valueOf(2500)),
                            new Route(RouteType.Road, valueOf(4000))
                    ),
                    valueOf(20)
            );

    public static final DeliveryTask ApplesDelivery =
            new DeliveryTask(
                    "Яблоки 'Грени Смит'",
                    Arrays.asList(
                            new Route(RouteType.Road, valueOf(1000)),
                            new Route(RouteType.Air, valueOf(700))
                    ),
                    valueOf(5)
            );

    public static final DeliveryTask ExpressLetterDelivery =
            new DeliveryTask(
                    "Очень важное почтовое письмо",
                    Arrays.asList(
                            new Route(RouteType.Road, valueOf(1000)),
                            new Route(RouteType.Railway, valueOf(100)),
                            new Route(RouteType.Air, valueOf(20))
                    ),
                    valueOf(1)
            );
    
    public static final DeliveryTask NuclearWeaponDelivery =
        new DeliveryTask(
            "Ядерное оружие",
            Arrays.asList(
                new Route(RouteType.Road, valueOf(1000)),
                new Route(RouteType.Sea, valueOf(400))
            ),
            valueOf(5000)
        );
}
