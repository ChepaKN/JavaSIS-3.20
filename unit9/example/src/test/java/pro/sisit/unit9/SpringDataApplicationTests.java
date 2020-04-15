package pro.sisit.unit9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import pro.sisit.unit9.data.*;
import pro.sisit.unit9.entity.*;
import pro.sisit.unit9.service.SaleTransaction;
import pro.sisit.unit9.service.SaleTransactionsImpl;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private AuthorOfBookRepository authorOfBookRepository;

	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private PurchasedBookRepository purchasedBookRepository;

	@Before
	public void init() {
		Book book = new Book();
		book.setDescription("Увлекательные приключения Тома Сойера");
		book.setTitle("Приключения Тома Сойера");
		book.setYear(1876);
		bookRepository.save(book);

		Book book2 = new Book();
		book2.setTitle("Михаил Строгов");
		book2.setYear(1876);
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("Приключения Гекльберри Финна");
		book3.setYear(1884);
		bookRepository.save(book3);

		Author author = new Author();
		author.setFirstname("Марк");
		author.setLastname("Твен");
		authorRepository.save(author);

		AuthorOfBook authorOfBook = new AuthorOfBook();
		authorOfBook.setAuthor(author);
		authorOfBook.setBook(book);
		authorOfBookRepository.save(authorOfBook);

		AuthorOfBook authorOfBook2 = new AuthorOfBook();
		authorOfBook2.setAuthor(author);
		authorOfBook2.setBook(book3);
		authorOfBookRepository.save(authorOfBook2);

		Author author2 = new Author();
		author2.setFirstname("Жюль");
		author2.setLastname("Верн");
		authorRepository.save(author2);

		AuthorOfBook authorOfBook3 = new AuthorOfBook();
		authorOfBook3.setAuthor(author2);
		authorOfBook3.setBook(book2);
		authorOfBookRepository.save(authorOfBook3);

		Book book4 = new Book();
		book4.setTitle("Буратино");
		book4.setYear(1936);
		bookRepository.save(book4);

		Author author3 = new Author();
		author3.setFirstname("Алексей");
		author3.setLastname("Толстой");
		authorRepository.save(author3);

		AuthorOfBook authorOfBook4 = new AuthorOfBook();
		authorOfBook4.setAuthor(author3);
		authorOfBook4.setBook(book4);
		authorOfBookRepository.save(authorOfBook4);

		//---------- fill data to book magazine tests-------
		Buyer buyer = new Buyer();
		buyer.setName("Евгений Лукашин");
		buyer.setAddress("3-я улица строителей");
		buyerRepository.save(buyer);

		Seller seller = new Seller();
		seller.setName("Хитрый продавец");
		seller.setMagazine("BeerStore");
		sellerRepository.save(seller);

	}

	@After
	public void clearRepositories() {
		authorOfBookRepository.deleteAll();
		authorRepository.deleteAll();
		bookRepository.deleteAll();
		purchasedBookRepository.deleteAll();
		buyerRepository.deleteAll();
		sellerRepository.deleteAll();
	}

	@Test
	public void buyerSaveTest() {
		boolean founded = false;
		for (Buyer buyer : buyerRepository.findAll()) {
			if (buyer.getAddress().equals("3-я улица строителей")
					&& buyer.getName().equals("Евгений Лукашин")
					&& buyer.getId() > 0) {
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	public void sellerSaveTest(){
		boolean founded = false;
		for(Seller seller : sellerRepository.findAll()){
			if(seller.getName().equals("Хитрый продавец")
				&& seller.getMagazine().equals("BeerStore")
				&& seller.getId() > 0){
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	@Transactional
	public void findPurchasedBookByTest() {

		Buyer buyer = new Buyer();
		buyer.setName("Владимир Путин");
		buyer.setAddress("Кремль");

		Seller seller = new Seller();
		seller.setName("Дмитрий Медведев");
		seller.setMagazine("Мавзолей");

		Book targetBook = new Book();
		targetBook.setTitle("Конституция РФ");
		targetBook.setDescription("Фантастика");
		targetBook.setYear(2020);

		PurchasedBook purchasedBook = new PurchasedBook();
		purchasedBook.setBook(targetBook);
		purchasedBook.setBuyer(buyer);
		purchasedBook.setSeller(seller);
		purchasedBook.setCost(BigDecimal.valueOf(0.01));
		purchasedBookRepository.save(purchasedBook);

		assertEquals(1, purchasedBookRepository.findByBook(targetBook).size());
		assertEquals(1, purchasedBookRepository.findByBuyer(buyer).size());
		assertEquals(1, purchasedBookRepository.findBySeller(seller).size());
	}

	@Test
	@Transactional
	public void saleTransactionTest() {

		Buyer buyer = new Buyer();
		buyer.setName("Сергей Зверев");
		buyer.setAddress("Тимирязева 1");
		buyerRepository.save(buyer);

		Book book = bookRepository.findAll().get(0);

		BigDecimal price = BigDecimal.valueOf(123.45);

		SaleTransaction saleTransactions = new SaleTransactionsImpl(purchasedBookRepository);
		saleTransactions.saleTransaction(book, buyer, price);

		boolean founded = false;
		for (PurchasedBook iteratedBook : purchasedBookRepository.findAll()) {
			if (iteratedBook.getBook().getTitle().equals(book.getTitle())
					&& iteratedBook.getBuyer().getName().equals(buyer.getName())
					&& iteratedBook.getId() > 0){
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	@Transactional
	public void totalCostByBookTest() {

		SaleTransaction saleTransactions = new SaleTransactionsImpl(purchasedBookRepository);

		Book book = new Book();
		book.setTitle("Шрэк");
		book.setYear(2001);
		bookRepository.save(book);

		List<String> names = Arrays.asList("Шрэк", "Фиона", "Осел", "Дракон", "Пинокио");
		BigDecimal price = BigDecimal.valueOf(123.45);
		for(String name : names){
			Buyer buyer = new Buyer();
			buyer.setName(name);
			buyer.setAddress("Болото");
			buyerRepository.save(buyer);
			saleTransactions.saleTransaction(book, buyer, price);
		}

		assertEquals(saleTransactions.totalCostByBook(book),
				price.multiply(BigDecimal.valueOf(names.size())));
	}

	@Test
	@Transactional
	public void totalCostByBuyerTest() {

		SaleTransaction saleTransactions = new SaleTransactionsImpl(purchasedBookRepository);

		Buyer buyer = new Buyer();
		buyer.setName("Шрэк");
		buyer.setAddress("Болото");
		buyerRepository.save(buyer);

		List<String> bookTitles = Arrays.asList("Золушка", "Том и Джерри", "Серебрянное копытце", "Сын полка");
		BigDecimal price = BigDecimal.valueOf(123.45);
		for(String title : bookTitles){
			Book book = new Book();
			book.setTitle(title);
			book.setYear(2012);
			book.setDescription("Some description");
			saleTransactions.saleTransaction(book, buyer, price);
		}
		assertEquals(saleTransactions.totalCostByBuyer(buyer),
				price.multiply(BigDecimal.valueOf(bookTitles.size())));

	}

	@Test
	@Transactional
	public void totalCostBySellerTest(){
		SaleTransaction saleTransactions = new SaleTransactionsImpl(purchasedBookRepository);

		Buyer buyer = new Buyer();
		buyer.setName("Шрэк");
		buyer.setAddress("Болото");
		buyerRepository.save(buyer);

		List<String> bookTitles = Arrays.asList("Золушка", "Том и Джерри", "Серебрянное копытце", "Сын полка");
		BigDecimal price = BigDecimal.valueOf(123.45);
		for(String title : bookTitles){
			Book book = new Book();
			book.setTitle(title);
			book.setYear(2012);
			book.setDescription("Some description");
			saleTransactions.saleTransaction(book, buyer, price);
		}
		assertEquals(saleTransactions.totalCostByBuyer(buyer),
				price.multiply(BigDecimal.valueOf(bookTitles.size())));
	}


	@Test
	public void testSave() {
		boolean founded = bookRepository.findAll()
				.stream()
				.filter(book -> book.getTitle().equals("Буратино"))
				.count() == 1;
		assertTrue(founded);
	}

	@Test
	public void testFindByYear() {
		assertEquals(2, bookRepository.findByYear(1876).size());
		assertEquals(1, bookRepository.findByYear(1884).size());
		assertEquals(0, bookRepository.findByYear(2000).size());
	}

	@Test
	public void testFindAtPage() {
		PageRequest pageRequest = PageRequest.of(1, 1, Sort.Direction.ASC, "title");
		assertTrue(bookRepository.findAll(pageRequest)
				.get().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testFindSame() {
		Book book = new Book();
		book.setYear(1876);

		assertEquals(2, bookRepository.findAll(Example.of(book)).size());
	}

	@Test
	public void testFindInRange() {
		assertEquals(3, bookRepository.findAll(
				BookSpecifications.byYearRange(1800, 1900)).size());
		assertEquals(0, bookRepository.findAll(
				BookSpecifications.byYearRange(2000, 2010)).size());
	}

	@Test
	public void testFindByAuthorLastname() {
		assertTrue(bookRepository.findByAuthor("Верн")
				.stream().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testComplexQueryMethod() {
		assertEquals(4, bookRepository.complexQueryMethod().size());
	}

}
