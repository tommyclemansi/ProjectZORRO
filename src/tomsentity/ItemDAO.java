/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsentity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Cache;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

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
		/*
		 * note here that myItem must be compatible with the type used 
		 * in CriteriaQuery
		 * select
CriteriaQuery<T> select(Selection<? extends T> selection)
Specify the item that is to be returned in the query result. Replaces the previously specified selection(s), if any. 
Note: Applications using the string-based API may need to specify the type of the select item when it results from a get or join operation and the query result type is specified. 


		 */
		
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
		    // below generates: AND (t0.BAGNO = t1.BAG_BAGNO))
		Join <Item,Bag> jb = myItem.join(Item_.bag);// set the join on the ROOT ITEM
		// note we just set it on the root item, but actually never use JOIN
		query = em.createQuery(criteria);// again create the query based on the criteriaQuery
		query.setParameter("amount", 30);
		items = query.getResultList(); // get the result as a list
		System.out.println("criteria result 5: " + items);
	
		/*
		 * Tuple query test.. 
		 * a Tuple is a pair of name, value pairs, similar to a Map
		 */
		CriteriaQuery<Tuple> tuple = cb.createTupleQuery();
		Root<Item> rootItem = tuple.from(Item.class);
		// either use it in this way: 
		tuple.select(cb.tuple( rootItem.get(Item_.ItemNO), rootItem.get(Item_.price)).alias("itemNumberwithprice"));
		// or use a multiselect (specify multiple values
		// here I use 3:
		//tuple.multiselect(rootItem.get(Item_.ItemNO),rootItem.get(Item_.keywords),rootItem.get(Item_.price));
	    TypedQuery<Tuple> mq = em.createQuery(tuple);
	    List<Tuple> mylist = mq.getResultList();
	    System.out.println("result of Tuple Query 1 : " + mylist);
        for (Tuple x : mylist)
          {System.out.println(x.get(0) +" " + x.get(1));}
	    tuple.multiselect(rootItem.get(Item_.ItemNO),rootItem.get(Item_.keywords),rootItem.get(Item_.price));
	    mq = em.createQuery(tuple);
	    mylist = mq.getResultList();
	     for (Tuple x : mylist)
         {System.out.println(x.get(0) +" " + x.get(1) + " " + x.get(2));}
	    System.out.println("result of Tuple Query 2 : " + mylist);

	    /*
	     * but also possible in another way:
	     */
	    CriteriaQuery<Object []> arrquery = cb.createQuery(Object[].class);
		rootItem = arrquery.from(Item.class);
		arrquery.multiselect(rootItem.get(Item_.ItemNO),rootItem.get(Item_.keywords),rootItem.get(Item_.price)); 
	    TypedQuery<Object[]> myq = em.createQuery(arrquery);
	    List<Object[]> ml = myq.getResultList();
	    System.out.println("result of generic query");
	    for (Object x : ml)
	    {  System.out.println(x.getClass() + "string: " + x.toString());
	      
	       if (x instanceof Double)
	    	   System.out.println((Double)x);
	    }
	    
	    
	    
	}

	public static void traverseModel()
	{System.out.println("dumping metamodel classes:");
	Metamodel mm = em.getMetamodel();
	Set<EntityType<?>> myset = mm.getEntities();
	for(EntityType<?> mytype : myset)
	{
	System.out.println(mytype.getClass().toString() + " "+ mytype.getName());	
	}

	}
	
	public static void findmyent()
	{System.out.println("before searching my entity");
	Item it =em.find(Item.class, 1); 
	System.out.println("the price for this item is: "+ it.getPrice());
	/*
	 * note no queries are executed, as it is in the cache.. 
	 * 
	 */
	System.out.println("after searching my entity");
	Cache ca = em.getEntityManagerFactory().getCache();	
    System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
	ca.evictAll();
	
	System.out.println("evicted cache, before searching my entity");
	System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
	it =em.find(Item.class, 1); 
	System.out.println("the price for this item is: "+ it.getPrice());
	/*
	 * note no queries are executed, as it is in the cache.. 
	 * issue is that this is an extended context.. 
	 * so only refresh will cause it to be refreshed I guess. 
	 */
	System.out.println("after searching my entity");
	System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
	em.close();
	// now using a brand new entitymanager
	 em = Persistence.createEntityManagerFactory("MyJPA1").createEntityManager();
	 System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
		it =em.find(Item.class, 1); 
		System.out.println("the price for this item is: "+ it.getPrice());
		System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
		
	// another test, new factory, but try to bypass cache:
		System.out.println("new test");
		em.close();
		// now using a brand new entitymanager
		 em = Persistence.createEntityManagerFactory("MyJPA1").createEntityManager();
		 System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
			it =em.find(Item.class, 1); 
			System.out.println("the price for this item is: "+ it.getPrice());
			System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
	System.out.println("yet another test using a query.. ");
	/*
	 * this also does not go to the database		 
	 */
	Query qu =  em.createQuery("select i from Item i where i.ItemNO='1'");
			 Item itret = (Item) qu.getSingleResult();
			 System.out.println("the price for this item is: "+ it.getPrice());
				System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
		 
				//yet another test:
				Map props = new HashMap();
				//props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE); //this is the default 
				//props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //bypass the cache				
				//props.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH); // explicitly refreshes
				props.put("javax.persistence.cache.storeMode", CacheStoreMode.USE); // default, does not refresh if available
				it =em.find(Item.class, 1,props);
				System.out.println("item in factory cache: " + ca.contains(Item.class, 1));
	}
	
	
	
}
