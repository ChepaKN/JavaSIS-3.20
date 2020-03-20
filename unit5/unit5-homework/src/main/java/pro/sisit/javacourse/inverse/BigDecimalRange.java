package pro.sisit.javacourse.inverse;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Диапазон данных типа BigDecimal
 */
@Getter
public class BigDecimalRange {

    /**
     * Значение левой границы диапазона
     */
    private final BigDecimal left;

    /**
     * Левая граница строгая?
     */
    private final boolean isLeftStrict;

    /**
     * Левая граница открытая?
     */
    private final boolean isLeftOpen;

    /**
     * Значение правой границы диапазона
     */
    private final BigDecimal right;

    /**
     * Правая граница строгая?
     */
    private final boolean isRightStrict;

    /**
     * Правая граница открытая?
     */
    private final boolean isRightOpen;

    public BigDecimalRange(BigDecimal left, boolean isLeftStrict, BigDecimal right, boolean isRightStrict) {
        this.left = left;
        this.isLeftStrict = isLeftStrict;
        this.isLeftOpen = left == null;
        this.right = right;
        this.isRightStrict = isRightStrict;
        this.isRightOpen = right == null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("\t");
        builder.append(asInterval());
        builder.append("\n");
        builder.append("\t");
        builder.append(asInequality());
        builder.append("\n");

        return builder.toString();
    }

    /**
     * Фоматирует диапазон в строку вида
     * left < X < right
     */
    public String asInequality() {
        StringBuilder builder = new StringBuilder();

        if (isLeftOpen) {
            builder.append("-INF < X ");
        } else if (isLeftStrict) {
            builder.append(left);
            builder.append(" < X ");
        } else {
            builder.append(left);
            builder.append(" <= X ");
        }

        if (isRightOpen) {
            builder.append("< +INF");
        } else if (isRightStrict) {
            builder.append("< ");
            builder.append(right);
        } else {
            builder.append("<= ");
            builder.append(right);
        }

        return builder.toString();
    }

    /**
     * Фоматирует диапазон в строку вида
     * (left; right)
     */
    public String asInterval() {
        StringBuilder builder = new StringBuilder();

        if (isLeftOpen) {
            builder.append("(-INF; ");
        } else if (isLeftStrict) {
            builder.append("(");
            builder.append(left);
            builder.append("; ");
        } else {
            builder.append("[");
            builder.append(left);
            builder.append("; ");
        }

        if (isRightOpen) {
            builder.append("+INF)");
        } else if (isRightStrict) {
            builder.append(right);
            builder.append(")");
        } else {
            builder.append(right);
            builder.append("]");
        }

        return builder.toString();
    }

    // Вспомогательные методы для создания диапазонов

    /**
     * Диапазон с одним значением
     * [value; value]
     */
    public static BigDecimalRange valueRange(BigDecimal value) {
        return new BigDecimalRange(value, false, value, false);
    }

    /**
     * Нестрогий диапазон
     * [left; right]
     */
    public static BigDecimalRange softRange(BigDecimal left, BigDecimal right) {
        return new BigDecimalRange(left, false, right, false);
    }

    /**
     * Строгий диапазон
     * (left; right)
     */
    public static BigDecimalRange strictRange(BigDecimal left, BigDecimal right) {
        return new BigDecimalRange(left, true, right, true);
    }

    /**
     * Левая граница строгая, правая - нестрогая
     * (left; right]
     */
    public static BigDecimalRange leftStrictRightSoftRange(BigDecimal left, BigDecimal right) {
        return new BigDecimalRange(left, true, right, false);
    }

    /**
     * Левая граница нестрогая, правая - строгая
     * [left; right)
     */
    public static BigDecimalRange leftSoftRightStrictRange(BigDecimal left, BigDecimal right) {
        return new BigDecimalRange(left, false, right, true);
    }

    /**
     * Левая граница открыта, правая - нестрогая
     * (-INF; right]
     */
    public static BigDecimalRange leftOpenRightSoftRange(BigDecimal right) {
        return new BigDecimalRange(null, true, right, false);
    }

    /**
     * Левая граница открыта, правая - строгая
     * (-INF; right)
     */
    public static BigDecimalRange leftOpenRightStrictRange(BigDecimal right) {
        return new BigDecimalRange(null, true, right, true);
    }

    /**
     * Левая граница нестрогая, правая - открыта
     * [left; +INF)
     */
    public static BigDecimalRange leftSoftRightOpenRange(BigDecimal left) {
        return new BigDecimalRange(left, false, null, true);
    }

    /**
     * Левая граница строгая, правая - открыта
     * (left; +INF)
     */
    public static BigDecimalRange leftStrictRightOpenRange(BigDecimal left) {
        return new BigDecimalRange(left, true, null, true);
    }

    /**
     * Бесконечный диапазон
     * (-INF; +INF)
     */
    public static BigDecimalRange infinityRange() {
        return new BigDecimalRange(null, true, null, true);
    }
}