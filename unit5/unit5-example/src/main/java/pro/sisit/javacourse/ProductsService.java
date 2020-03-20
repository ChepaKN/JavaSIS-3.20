package pro.sisit.javacourse;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с продуктами
 */
public class ProductsService {

    public static void main(String[] args) {
    }

    /**
     * Фильтрует переданный список продуктов по переданному типу.
     */
    public static List<Product> filterByType(List<Product> products, ProductType type) {
        return products.stream()
                       .filter(product -> product.getType().equals(type))
                       .collect(Collectors.toList());
    }

    /**
     * Получает отсортированный список уникальных производителей переданных продуктов.
     */
    public static List<Producer> getUniqueSortedProducers(List<Product> products) {
        return products.stream()
                       .map(Product::getProducer)
                       .sorted(Comparator.comparing(Producer::getName))
                       .distinct()
                       .collect(Collectors.toList());
    }

    /**
     * Рассчитывает долю НДС для списка продуктов.
     * Долю НДС для одного продукта считаем по формуле: цена * 20 / 120,
     * при этом при делении используется банковское округление.
     */
    public static BigDecimal getTotalTax(List<Product> products) {
        return products.stream()
                       .map(Product::getPrice)
                       .map(price -> price.multiply(BigDecimal.valueOf(20.00))
                                          .divide(BigDecimal.valueOf(120.00), BigDecimal.ROUND_HALF_EVEN))
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Получает наименование производителя продукта по переданному продукту.
     * Вспомогательный метод.
     */
    public static String extractProductProducerName(Product product) {
        return product.getProducer().getName();
    }

    /**
     * Выполняет поиск продукта в переданном списке по переданному идентификатору продукта.
     * Если продукт найден, то вернет его, иначе вернет null.
     * Метод - в императивном стиле без использования стримов.
     */
    public static Product findProductByIdUnsafe(List<Product> products, long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    /**
     * Определяет наименование производителя продукта по переданному идентификатору продукта
     */
    public static String getProductProducerNameByIdUnsafe(List<Product> products, long id) {
        return extractProductProducerName(
            findProductByIdUnsafe(products, id)
        );
    }

    /**
     * Выполняет поиск продукта в переданном списке по переданному идентификатору продукта
     */
    public static Optional<Product> findProductById(List<Product> products, long id) {
        return products.stream()
                       .filter(product -> product.getId() == id)
                       .findFirst();
    }

    /**
     * Определяет наименование производителя продукта по переданному идентификатору продукта.
     * Если продукт не найден выбросит RuntimeException с сообщением
     * Товар с идентификатором '%s' не найден
     */
    public static String getProductProducerNameById(List<Product> products, long id) {
        return findProductById(products, id)
            .map(ProductsService::extractProductProducerName)
            .orElseThrow(() -> new RuntimeException(
                String.format("Товар с идентификатором '%s' не найден", id)));
    }

    /**
     * Определяет наименование производителя продукта по переданному идентификатору продукта
     * Если продукт не найден вернет 'Неизвестный производитель'.
     */
    public static String getProductProducerNameByIdSoft(List<Product> products, long id) {
        return findProductById(products, id)
            .map(ProductsService::extractProductProducerName)
            .orElse("Неизвестный производитель");
    }
}
