package tomsUnitentity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tomsentity.Status;
import tomsentity.Item;
import tomsentity.ItemDAO;
import tomsentity.Bag;

import java.util.List;
import java.util.GregorianCalendar;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author tcleyman
 *
 */
public class myUnitTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tomsentity.ItemDAO#save(tomsentity.Item)}.
	 */
	@Test
	public final void testSave() {
		Bag bag = new Bag();
		bag.setBuydate(new GregorianCalendar().getTime());
		bag.setStatus(Status.OPEN);
		Item it = new Item();
		it.setDescription("tomsdescription");
		it.setPrice(10.22);
		it.setBag(bag);
		it.addKeyword("tomstest");
		it.addKeyword("tomssecondTest");
		ItemDAO ad = new ItemDAO();
		Item it2 = new Item();
		it2.setDescription("item2");
		it2.setBag(bag);
		it2.setPrice(33333.999);
		it2.addKeyword("tomstest2");
		it2.addKeyword("tomssecondTest2");
		ad.save(it);
		ad.save(it2);
		System.out.println("succeeded");
		Item item = ad.findByPrimaryKey(1);
		System.out.println("ITEM result: " + item.toString());
		assertTrue("item should NOT have same number",
				it.getItemNO() != it2.getItemNO());
	}

	@Test
	public final void testNamedQ() {
		ItemDAO it = new ItemDAO();
		List items = it.testNamedQ();
		

		assertTrue("item should NOT have same number", !items.isEmpty());
	}

	@Test
	public void testCriteria()
	{
		ItemDAO it = new ItemDAO();
		 it.testCriteria();
	}
	
	/**
	 * Test method for {@link tomsentity.ItemDAO#findByPrimaryKey(int)}.
	 */
	@Test
	public final void testFindByPrimaryKey() {

		fail("Not yet implemented"); // TODO
	}

}
