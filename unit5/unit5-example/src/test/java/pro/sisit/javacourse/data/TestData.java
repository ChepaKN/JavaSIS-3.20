package pro.sisit.javacourse.data;

import pro.sisit.javacourse.Producer;
import pro.sisit.javacourse.Product;
import pro.sisit.javacourse.ProductType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Тестовые данные для юнит-тестов
 */
public class TestData {

    private static List<Producer> testProducerData;

    private static List<Product> productData;

    private static List<TestProduct> testProductData;

    private static final Map<ProductType, List<TestProduct>> testProductDataMap = new HashMap<>();

    public static List<Product> getProducts() {
        if (productData == null) {
            productData = getTestProducts()
                    .stream()
                    .map(TestProduct::getProduct)
                    .collect(Collectors.toList());
        }

        return productData;
    }

    private static List<TestProduct> getTestProducts() {
        if (testProductData == null) {
            testProductData = Arrays
                    .stream(ProductType.values())
                    .map(TestData::getTestProducts)
                    .reduce(new ArrayList<>(),
                            (totalList, currentList) -> {
                                totalList.addAll(currentList);
                                return totalList;
                            });
        }

        return testProductData;
    }

    public static List<Product> getProducts(ProductType productType) {
        return getTestProducts(productType)
                .stream()
                .map(TestProduct::getProduct)
                .collect(Collectors.toList());
    }

    public static Product getRandomProduct() {
        int size = getProducts().size();
        Random random = new Random();
        int randomIndex = random.nextInt(size - 1);

        return getProducts().get(randomIndex);
    }

    public static long getNotExistProductId() {
        List<Long> existed = getProducts()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        Random random = new Random();
        long result;

        while (true) {
            Long randomId = random.nextLong();
            if (!existed.contains(randomId)) {
                result = randomId;
                break;
            }
        }

        return result;
    }

    private static List<TestProduct> getTestProducts(ProductType productType) {
        if (!testProductDataMap.containsKey(productType)) {
            testProductDataMap.put(productType, initTestData(productType));
        }

        return testProductDataMap.get(productType);
    }

    public static List<Producer> getProducers() {
        if (testProducerData == null) {
            testProducerData = initTestProducersData();
        }

        return testProducerData;
    }

    public static Producer getProducer(String name) {
        return getProducers()
                .stream()
                .filter(producer -> producer.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Производитель '%s' не найден.", name)));
    }

    public static BigDecimal getTotalTax() {
        return testProductData
                .stream()
                .map(TestProduct::getTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static List<TestProduct> initTestCandyData() {
        Producer kraskon = getProducer("Краскон");
        Producer russia = getProducer("Россия");

        return Arrays.asList(
                initTestProduct(1, "Саянита", ProductType.Candy, BigDecimal.valueOf(50.00), kraskon),
                initTestProduct(2, "Саянские", ProductType.Candy, BigDecimal.valueOf(70.00), kraskon),
                initTestProduct(3, "Красноярские столбы", ProductType.Candy, BigDecimal.valueOf(100.00), kraskon),
                initTestProduct(4, "Багира", ProductType.Candy, BigDecimal.valueOf(110.00), kraskon),
                initTestProduct(11, "Родные просторы", ProductType.Candy, BigDecimal.valueOf(120.00), russia)
        );
    }

    private static List<TestProduct> initTestChocolateData() {
        List<Producer> producers = getProducers();
        Producer kraskon = getProducer("Краскон");
        Producer russia = getProducer("Россия");

        return Arrays.asList(
                initTestProduct(5, "Шоколад темный", ProductType.Chocolate, BigDecimal.valueOf(70.00), kraskon),
                initTestProduct(6, "Шоколад молочный", ProductType.Chocolate, BigDecimal.valueOf(70.00), kraskon),
                initTestProduct(7, "Дуэт в карамельном", ProductType.Chocolate, BigDecimal.valueOf(80.00), russia),
                initTestProduct(8, "Дуэт в молочном", ProductType.Chocolate, BigDecimal.valueOf(80.00), russia),
                initTestProduct(9, "Российский темный шоколад", ProductType.Chocolate, BigDecimal.valueOf(75.00), russia),
                initTestProduct(10, "Российский темный шоколад", ProductType.Chocolate, BigDecimal.valueOf(75.00), russia)
        );
    }

    private static List<TestProduct> initTestSodaData() {
        List<Producer> producers = getProducers();
        Producer cocaCola = getProducer("Coca-cola");
        Producer ayan = getProducer("Аян");

        return Arrays.asList(
                initTestProduct(12, "Sprite", ProductType.Soda, BigDecimal.valueOf(60.00), cocaCola),
                initTestProduct(13, "Лимонад", ProductType.Soda, BigDecimal.valueOf(35.00), ayan),
                initTestProduct(14, "Fanta", ProductType.Soda, BigDecimal.valueOf(60.00), cocaCola),
                initTestProduct(15, "Тепсей", ProductType.Soda, BigDecimal.valueOf(40.00), ayan)
        );
    }

    private static List<TestProduct> initTestIceCreamData() {
        return Collections.emptyList();
    }

    private static List<TestProduct> initTestDessertData() {
        return Collections.emptyList();
    }

    private static List<Producer> initTestProducersData() {
        return Arrays.asList(
                new Producer(1, "Краскон"),
                new Producer(2, "Coca-cola"),
                new Producer(3, "Россия"),
                new Producer(4, "Аян"));
    }

    private static List<TestProduct> initTestData(ProductType productType) {
        switch (productType) {
            case Soda:
                return initTestSodaData();
            case Candy:
                return initTestCandyData();
            case Dessert:
                return initTestDessertData();
            case IceCream:
                return initTestIceCreamData();
            case Chocolate:
                return initTestChocolateData();
            default:
                throw new RuntimeException("Product type '" + productType.name() + "' not supported!");
        }
    }

    private static TestProduct initTestProduct(long id, String name, ProductType productType, BigDecimal price, Producer producer) {
        return new TestProduct(id, name, productType, price, producer);
    }
}