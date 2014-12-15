package tomsentity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ItemDAO {


	private static EntityManager em=null;
	//static must be after em else NPE
	static 
	{ // when class is loaded create the entitymanager
		 em = Persistence.createEntityManagerFactory("MyJPA1").createEntityManager();
	}
	/*
	 * private method to get an entity manager
	 */

	/*private EntityManager getEntityManager()
	{ // so seems we request a new one every time..
		//better to store it as private, then the objects shouldn't be detached;. 
	 EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyJPA1");
	 return emf.createEntityManager();
	}*/
	
	
	public void checkCache()
	{
	Cache ca = em.getEntityManagerFactory().getCache();	
    ca.evictAll();
	}
	/*
	 * attempt to generalize the method:
	 */
	public Item save(Item item)
	{
	//EntityManager em = getEntityManager();
	EntityTransaction et = em.getTransaction();
	//UserTransaction ut = em.getTransaction();
	et.begin();
	//
	em.persist(item);
	et.commit();
	return item;
	}
	
	public Object save (Object o)
	{   
		//EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(o);
		et.commit();
		return o;
	}
	
	
	public Item search(int id)
	{
		Map<String,String> test = new HashMap<String,String>();
		
		Item it = em.find(Item.class, 1);
		          
		    
		return it;
	}
	
	public Item findByPrimaryKey(int id)
	{
		//return getEntityManager().find(Item.class, 1);
		return em.find(Item.class, 1);
	}
	
	
	public List testNamedQ()
	{
       Query qu =  em.createQuery("select i from Item i");// note here must be uppercase
       List item = qu.getResultList();
     
       System.out.println(item.toString());
	return item;
	}
	
	public void testCriteria()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder(); // first get criteriaBuilder 
		CriteriaQuery<Item> criteria = cb.createQuery(Item.class); // this returns a criteria
		// note above this is typed
		Root<Item> myItem = criteria.from(Item.class); // from Item (Root extends from)
		criteria.select(myItem);// here select an item to be returned 
		//cb.gt(arg0, arg1)
		//Predicate condition = cb.gt(item.get(Item_.price), 20); // price greater then 20 
		//ci.where(condition);
		
		TypedQuery<Item> query = em.createQuery(criteria);
		List<Item> items = query.getResultList();
		System.out.println("criteria result: " + items);
		
		//Path<String> cheap = rootitem.<String>get("price");
		//Predicate condition = cb., arg1)
		//query.where(condition);
		//em.createQuery.. 
		
		
	}

}
