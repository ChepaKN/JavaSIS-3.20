package pro.sisit.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pro.sisit.javacourse.data.TestData.*;


public class ProductsServiceTest {

    @Test
    public void testFilterByType() {
        runTest("test_filterByType");

        ProductType productType = ProductType.Candy;
        List<Product> products = ProductsService.filterByType(getProducts(), productType);

        System.out.println("Продукты:");
        products.forEach(product -> System.out.println(product.toString()));

        Assert.assertEquals(getProducts(productType), products);
    }

    @Test
    public void testGetUniqueSortedProducers() {
        runTest("test_getUniqueSortedProducers");

        List<Producer> uniqueProducers = ProductsService.getUniqueSortedProducers(getProducts());

        System.out.println("Производители:");
        uniqueProducers.forEach(producer -> System.out.println(producer.getName()));

        Assert.assertEquals(
                getProducers()
                        .stream()
                        .sorted(Comparator.comparing(Producer::getName))
                        .collect(Collectors.toList()),
                uniqueProducers);
    }

    @Test
    public void testGetTotalTax() {
        runTest("test_getTotalTax");

        BigDecimal totalTax = ProductsService.getTotalTax(getProducts());
        System.out.printf("Общая сумма НДС: '%s'", totalTax);

        Assert.assertEquals(getTotalTax(), totalTax);
    }

    @Test
    public void testGetExistingProductProducerNameByIdUnsafe() {
        runTest("test_getExistingProductProducerNameByIdUnsafe");

        Product product = getRandomProduct();

        String producerName = ProductsService.getProductProducerNameByIdUnsafe(
                getProducts(), product.getId());

        System.out.printf("Продукт с ID: '%s'\n", product.getId());
        System.out.printf("Наименование производителя: '%s'", producerName);

        Assert.assertEquals(product.getProducer().getName(), producerName);
    }

    @Test
    public void testGetNotExistingProductProducerNameByIdUnsafe() {
        runTest("test_getNotExistingProductProducerNameByIdUnsafe");

        try {
            ProductsService.getProductProducerNameByIdUnsafe(
                    getProducts(), getNotExistProductId());

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertEquals(NullPointerException.class, ex.getClass());
        }
    }

    @Test
    public void testGetExistingProductProducerNameById() {
        runTest("test_getExistingProductProducerNameById");

        Product product = getRandomProduct();
        String producerName = ProductsService.getProductProducerNameById(
                getProducts(), product.getId());

        System.out.printf("Продукт с ID: '%s'\n", product.getId());
        System.out.printf("Наименование производителя: '%s'", producerName);

        Assert.assertEquals(product.getProducer().getName(), producerName);
    }

    @Test
    public void testGetNotExistingProductProducerNameById() {
        runTest("test_getNotExistingProductProducerNameById");

        long notExistProductId = getNotExistProductId();
        try {
            ProductsService.getProductProducerNameById(
                    getProducts(), notExistProductId);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertEquals(RuntimeException.class, ex.getClass());
            Assert.assertEquals(
                    String.format("Товар с идентификатором '%s' не найден", notExistProductId),
                    ex.getMessage());
        }
    }

    @Test
    public void testGetExistingProductProducerNameByIdSoft() {
        runTest("test_getExistingProductProducerNameByIdSoft");

        Product product = getRandomProduct();
        String producerName = ProductsService.getProductProducerNameByIdSoft(
                getProducts(), product.getId());

        System.out.printf("Продукт с ID: '%s'\n", product.getId());
        System.out.printf("Наименование производителя: '%s'", producerName);

        Assert.assertEquals(product.getProducer().getName(), producerName);
    }

    @Test
    public void testGetNotExistingProductProducerNameByIdSoft() {
        runTest("test_getNotExistingProductProducerNameByIdSoft");

        long notExistProductId = getNotExistProductId();
        String producerName = ProductsService.getProductProducerNameByIdSoft(
                getProducts(), notExistProductId);

        System.out.printf("Продукт с ID: '%s'\n", notExistProductId);
        System.out.printf("Наименование производителя: '%s'", producerName);

        Assert.assertEquals("Неизвестный производитель", producerName);
    }

    @Test
    public void testFindExistingProductByIdUnsafe() {
        runTest("test_findExistingProductByIdUnsafe");

        Assert.assertNotNull(
                ProductsService.findProductByIdUnsafe(
                        getProducts(), getRandomProduct().getId()));
    }

    @Test
    public void testFindNotExistingProductByIdUnsafe() {
        runTest("test_findNotExistingProductByIdUnsafe");

        Assert.assertNull(
                ProductsService.findProductByIdUnsafe(
                        getProducts(), getNotExistProductId()));
    }

    @Test
    public void testFindExistingProductById() {
        runTest("test_FindExistingProductById");

        Optional<Product> productOptional = ProductsService.findProductById(
                getProducts(), getRandomProduct().getId());

        Assert.assertEquals(Optional.class, productOptional.getClass());
        Assert.assertTrue(productOptional.isPresent());
    }

    @Test
    public void testFindNotExistingProductById() {
        runTest("test_FindNotExistingProductById");

        Optional<Product> productOptional = ProductsService.findProductById(
                getProducts(), getNotExistProductId());

        Assert.assertEquals(Optional.class, productOptional.getClass());
        Assert.assertFalse(productOptional.isPresent());
    }

    @Test
    public void testExtractProductProducerName() {
        runTest("test_extractProductProducerName");

        Product product = getRandomProduct();
        String producerName = ProductsService.extractProductProducerName(product);

        Assert.assertEquals(product.getProducer().getName(), producerName);
    }

    private void runTest(String testName) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_YELLOW = "\u001B[33m";

        System.out.println(ANSI_YELLOW + testName + ANSI_RESET);
    }
}