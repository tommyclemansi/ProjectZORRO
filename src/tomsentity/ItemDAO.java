/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
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
		
		TypedQuery<Item> query = em.createQuery(criteria); // create a query from the criteria
		List<Item> items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 1: " + items);
		
		/*
		 * test selecting using a Predicate
		 */
		Path<Double> pricepath = myItem.get("price"); // a Path is taken from the root
		Predicate condition = cb.ge(pricepath, 11); // create a Predicate (this extends Expression)
		criteria.where(condition); // set the where clause 
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 2: " + items);

        /*
         * test selecting using a parameter
         */
		ParameterExpression<Double> param = cb.parameter(Double.class,"amount"); // amount will be used later
		Predicate condition2 = cb.ge(pricepath, param);
		criteria.where(condition2); // note here the condition2 is not appended, instead it is overwritten 
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		query.setParameter("amount", 1);
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 3: " + items);
		
		/*
		 * test applying both Predicates..
		 */
		criteria.where(cb.and(condition,condition2)); // here 2 predicates should be applied
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		query.setParameter("amount", 1);
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 4: " + items);
		
		/*
		 * test using generated metamodel
		 */
		Path<Double> pricepath2 = myItem.get(Item_.price); // here I use the metamodel class to construct the Path
        //param is already defined above, so just reusing it:
        Predicate condition3 = cb.ge(pricepath2, param); // compare path to a value, a parameter here.. 
		criteria.where(condition3); // note here the condition2 is not appended, instead it is overwritten 
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		query.setParameter("amount", 30);
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 4: " + items);
	
		/*
		 * test using generated metamodel AND JOIN
		 */
		pricepath2 = myItem.get(Item_.price); // here I use the metamodel class to construct the Path
        //param is already defined above, so just reusing it:
        condition3 = cb.ge(pricepath2, param); // compare path to a value, a parameter here.. 
		criteria.where(condition3); // note here the condition2 is not appended, instead it is overwritten 
		// this generates: 	/*
		 * test using generated metamodel
		 */
		Path<Double> pricepath2 = myItem.get(Item_.price); // here I use the metamodel class to construct the Path
       //param is already defined above, so just reusing it:
       Predicate condition3 = cb.ge(pricepath2, param); // compare path to a value, a parameter here.. 
		criteria.where(condition3); // note here the condition2 is not appended, instead it is overwritten 
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		query.setParameter("amount", 30);
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 4: " + items);
	// 	/*
		 * test using generated metamodel
		 */
		Path<Double> pricepath2 = myItem.get(Item_.price); // here I use the metamodel class to construct the Path
       //param is already defined above, so just reusing it:
       Predicate condition3 = cb.ge(pricepath2, param); // compare path to a value, a parameter here.. 
		criteria.where(condition3); // note here the condition2 is not appended, instead it is overwritten 
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		query.setParameter("amount", 30);
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 4: " + items);
	    // below generates: AND (t0.BAGNO = t1.BAG_BAGNO))
		Join <Item,Bag> jb = myItem.join(Item_.bag);// set the join on the ROOT ITEM
		// note we just set it on the root item, but actually never use JOIN
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		query.setParameter("amount", 30);
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 5: " + items);
	
		
	}

}
