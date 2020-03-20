package pro.sisit.javacourse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.sisit.javacourse.inverse.BigDecimalRange;
import pro.sisit.javacourse.inverse.InverseDeliveryTask;
import pro.sisit.javacourse.inverse.Solution;
import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Transport;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static pro.sisit.javacourse.TestData.*;


public class TestInversePathFinder {

    public InversePathFinder getInversePathFinder() {
        return new InversePathFinder();
    }

    private List<Transport> getAvailableTransport() {
        return Arrays.asList(
                GAZelle,
                Plane,
                Tanker,
                Train,
                SemiTrailerTruck);
    }

    private List<DeliveryTask> getDeliveryTasks() {
        return Arrays.asList(
                ApplesDelivery,
                BreadDelivery,
                CarsDelivery,
                KingKongDelivery,
                SmartphoneDelivery,
                ExpressLetterDelivery);
    }

    @Test
    public void testValueRangeSingleSolution() {
        test(BigDecimalRange.valueRange(valueOf(30000)),
                Collections.singletonList(
                        new Solution(CarsDelivery, Train, valueOf(30000))
                )
        );
    }

    @Test
    public void testValueRangeMultipleSolutionsBySingleTransport() {
        test(BigDecimalRange.valueRange(valueOf(250000)),
                Arrays.asList(
                        new Solution(KingKongDelivery, Tanker, valueOf(250000)),
                        new Solution(CarsDelivery, Tanker, valueOf(250000)),
                        new Solution(SmartphoneDelivery, Tanker, valueOf(250000))
                )
        );
    }

    @Test
    public void testValueRangeMultipleSolutionsByMultipleTransport() {
        test(BigDecimalRange.valueRange(valueOf(1000)),
                Arrays.asList(
                        new Solution(ExpressLetterDelivery, GAZelle, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Train, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Plane, valueOf(1000))
                )
        );
    }

    @Test
    public void testValueRangeNullSolutions() {
        test(BigDecimalRange.valueRange(valueOf(1000000)),
                Collections.emptyList()
        );
    }

    @Test
    public void testInfinityRangeAllSolutions() {
        test(BigDecimalRange.infinityRange(),
                Arrays.asList(
                        new Solution(BreadDelivery, GAZelle, valueOf(100)),
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200)),
                        new Solution(BreadDelivery, Train, valueOf(700)),
                        new Solution(BreadDelivery, Plane, valueOf(2500)),
                        new Solution(ExpressLetterDelivery, GAZelle, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Plane, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Train, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, SemiTrailerTruck, valueOf(2000)),
                        new Solution(ApplesDelivery, SemiTrailerTruck, valueOf(2000)),
                        new Solution(ApplesDelivery, Plane, valueOf(35000)),
                        new Solution(CarsDelivery, Tanker, valueOf(250000)),
                        new Solution(CarsDelivery, Train, valueOf(30000)),
                        new Solution(KingKongDelivery, Tanker, valueOf(250000)),
                        new Solution(SmartphoneDelivery, Plane, valueOf(100000)),
                        new Solution(SmartphoneDelivery, Tanker, valueOf(250000))
                )
        );
    }

    @Test
    public void testSoftRange() {
        test(BigDecimalRange.softRange(valueOf(100), valueOf(1000)),
                Arrays.asList(
                        new Solution(BreadDelivery, GAZelle, valueOf(100)),
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200)),
                        new Solution(BreadDelivery, Train, valueOf(700)),
                        new Solution(ExpressLetterDelivery, GAZelle, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Plane, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Train, valueOf(1000))
                )
        );
    }

    @Test
    public void testStrictRange() {
        test(BigDecimalRange.strictRange(valueOf(100), valueOf(1000)),
                Arrays.asList(
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200)),
                        new Solution(BreadDelivery, Train, valueOf(700))
                )
        );
    }

    @Test
    public void testLeftStrictRightSoftRange() {
        test(BigDecimalRange.leftStrictRightSoftRange(valueOf(100), valueOf(1000)),
                Arrays.asList(
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200)),
                        new Solution(BreadDelivery, Train, valueOf(700)),
                        new Solution(ExpressLetterDelivery, GAZelle, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Plane, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Train, valueOf(1000))
                )
        );
    }

    @Test
    public void testLeftSoftRightStrictRange() {
        test(BigDecimalRange.leftSoftRightStrictRange(valueOf(100), valueOf(1000)),
                Arrays.asList(
                        new Solution(BreadDelivery, GAZelle, valueOf(100)),
                        new Solution(BreadDelivery, Train, valueOf(700)),
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200))
                )
        );
    }

    @Test
    public void testLeftOpenRightSoftRange() {
        test(BigDecimalRange.leftOpenRightSoftRange(valueOf(2500)),
                Arrays.asList(
                        new Solution(ApplesDelivery, SemiTrailerTruck, valueOf(2000)),
                        new Solution(BreadDelivery, GAZelle, valueOf(100)),
                        new Solution(BreadDelivery, Train, valueOf(700)),
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200)),
                        new Solution(BreadDelivery, Plane, valueOf(2500)),
                        new Solution(ExpressLetterDelivery, GAZelle, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Plane, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Train, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, SemiTrailerTruck, valueOf(2000))
                )
        );
    }

    @Test
    public void testLeftOpenRightStrictRange() {
        test(BigDecimalRange.leftOpenRightStrictRange(valueOf(2500)),
                Arrays.asList(
                        new Solution(ApplesDelivery, SemiTrailerTruck, valueOf(2000)),
                        new Solution(BreadDelivery, GAZelle, valueOf(100)),
                        new Solution(BreadDelivery, Train, valueOf(700)),
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200)),
                        new Solution(ExpressLetterDelivery, GAZelle, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Plane, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Train, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, SemiTrailerTruck, valueOf(2000))
                )
        );
    }

    @Test
    public void testLeftSoftRightOpenRange() {
        test(BigDecimalRange.leftSoftRightOpenRange(valueOf(2500)),
                Arrays.asList(
                        new Solution(ApplesDelivery, Plane, valueOf(35000)),
                        new Solution(BreadDelivery, Plane, valueOf(2500)),
                        new Solution(CarsDelivery, Tanker, valueOf(250000)),
                        new Solution(CarsDelivery, Train, valueOf(30000)),
                        new Solution(KingKongDelivery, Tanker, valueOf(250000)),
                        new Solution(SmartphoneDelivery, Plane, valueOf(100000)),
                        new Solution(SmartphoneDelivery, Tanker, valueOf(250000))
                )
        );
    }

    @Test
    public void testLeftStrictRightOpenRange() {
        test(BigDecimalRange.leftStrictRightOpenRange(valueOf(2500)),
                Arrays.asList(
                        new Solution(ApplesDelivery, Plane, valueOf(35000)),
                        new Solution(CarsDelivery, Tanker, valueOf(250000)),
                        new Solution(CarsDelivery, Train, valueOf(30000)),
                        new Solution(KingKongDelivery, Tanker, valueOf(250000)),
                        new Solution(SmartphoneDelivery, Plane, valueOf(100000)),
                        new Solution(SmartphoneDelivery, Tanker, valueOf(250000))
                )
        );
    }

    @Test
    public void testSortingDataByInfinityRange() {
        testSort(BigDecimalRange.infinityRange(),
                Arrays.asList(
                        new Solution(CarsDelivery, Tanker, valueOf(250000)),
                        new Solution(KingKongDelivery, Tanker, valueOf(250000)),
                        new Solution(SmartphoneDelivery, Tanker, valueOf(250000)),
                        new Solution(SmartphoneDelivery, Plane, valueOf(100000)),
                        new Solution(ApplesDelivery, Plane, valueOf(35000)),
                        new Solution(CarsDelivery, Train, valueOf(30000)),
                        new Solution(BreadDelivery, Plane, valueOf(2500)),
                        new Solution(ExpressLetterDelivery, SemiTrailerTruck, valueOf(2000)),
                        new Solution(ApplesDelivery, SemiTrailerTruck, valueOf(2000)),
                        new Solution(ExpressLetterDelivery, GAZelle, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Plane, valueOf(1000)),
                        new Solution(ExpressLetterDelivery, Train, valueOf(1000)),
                        new Solution(BreadDelivery, Train, valueOf(700)),
                        new Solution(BreadDelivery, SemiTrailerTruck, valueOf(200)),
                        new Solution(BreadDelivery, GAZelle, valueOf(100))
                )
        );
    }

    @Test
    public void testNullInverseDeliveryTask() {
        test(null, Collections.emptyList(), false);
    }

    @Test
    public void testNullDeliveryTasks() {
        test(new InverseDeliveryTask(null, getAvailableTransport(), BigDecimalRange.infinityRange()), Collections.emptyList(), false);
    }

    @Test
    public void testNullTransports() {
        test(new InverseDeliveryTask(getDeliveryTasks(), null, BigDecimalRange.infinityRange()), Collections.emptyList(), false);
    }

    @Test
    public void testNullPriceRange() {
        test(null, Collections.emptyList());
    }

    private void test(BigDecimalRange totalPriceRange, List<Solution> expected) {
        test(new InverseDeliveryTask(getDeliveryTasks(), getAvailableTransport(), totalPriceRange), expected, false);
    }

    private void testSort(BigDecimalRange totalPriceRange, List<Solution> expected) {
        test(new InverseDeliveryTask(getDeliveryTasks(), getAvailableTransport(), totalPriceRange), expected, true);
    }

    private void test(InverseDeliveryTask task, List<Solution> expectedSolutions, Boolean checkSort) {
        InversePathFinder inversePathFinder = getInversePathFinder();

        System.out.println(String.format("Total price range: %s",
                Optional.ofNullable(task).map(InverseDeliveryTask::getPriceRange).orElse(null)));

        List<Solution> actualSolutions = inversePathFinder.getAllSolutions(task);
        System.out.println("\nactual solutions:");
        actualSolutions.forEach(System.out::println);

        System.out.println("\nexpected solutions:");
        expectedSolutions.forEach(System.out::println);

        Assertions.assertTrue(expectedSolutions.containsAll(actualSolutions));
        Assertions.assertTrue(actualSolutions.containsAll(expectedSolutions));

        if (checkSort) {
            Assertions.assertEquals(expectedSolutions, actualSolutions);
        }
    }
}