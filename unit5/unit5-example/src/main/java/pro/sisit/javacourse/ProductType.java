package pro.sisit.javacourse;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Тип продукта
 */
@Getter
@AllArgsConstructor
public enum ProductType {
    Dessert("Dessert", "Десерт"),
    IceCream("IceCream", "Мороженное"),
    Candy("Candy", "Конфеты"),
    Chocolate("Chocolate", "Шоколад"),
    Soda("Soda", "Газировка");

    private final String name;

    private final String localName;
}
