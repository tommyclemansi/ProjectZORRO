/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsUnitentity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

import tomsentity.Bag;
import tomsentity.BagDAO;
import tomsentity.Employee;
import tomsentity.Item;
import tomsentity.ItemDAO;
import tomsentity.MULTIKEY;
import tomsentity.MULTIKEYDAO;
import tomsentity.Status;
import tomsentity.ParkingSpace;

public class myMain {

	public static void main(String[] args) {
		String test;
		// TODO Auto-generated method stub
		//BagDAO bd = new BagDAO();
		Bag bag = new Bag();
		bag.setBuydate(new GregorianCalendar().getTime());
		bag.setStatus(Status.OPEN);
		Item it = new Item();
		it.setDescription("tomsdescription");
		it.setPrice(10.22);
		it.setBag(bag);
		it.addKeyword("tomstest");
		it.addKeyword("tomssecondTest-- changed ");
	//	it.s
		ItemDAO ad = new ItemDAO();
		Item it2 = new Item();
		it2.setDescription("item2");
		it2.setBag(bag);
		it2.setPrice(33333.999);
		
		it2.addKeyword("tomstest2");
		it2.addKeyword("tomssecondTest2");
		/*List<Item> items = new ArrayList<Item>();
		items.add(it);
		items.add(it2);
		bag.setItems(items);*/
		// need t do it in this way: 
		// this due to the fact I used unidirectional
		// assume unidirectional can only be manytoOne
		ad.save(it);
		// this causes duplicate key... 
		/*
		 * per my understanding the item is detached.. 
		 * and so it tries to persist it again; 
		 */
		ad.save(it2);
		//Bag bag2 = bd.save(bag);
	
        System.out.println("succeeded");
        Item item = ad.findByPrimaryKey(1);
        System.out.println("ITEM result: "  + item.toString());
        
        //test3();
        //test2();
        testEmp();
	}
	
	/*
	 * test criteria api
	 */
	public static void test2()
	{
		ItemDAO ad = new ItemDAO();
		ad.testCriteria();
		ad.traverseModel();
		ItemDAO.findmyent();
	}
	
	/*
	 * test just to save an instance with multiple pk
	 */
	public static void test3()
	{
		MULTIKEYDAO ad = new MULTIKEYDAO();
	    MULTIKEY it = new MULTIKEY();
	    it.setMyID(1);
	    it.setName("Tommyclemansi");
	    it.setCountry("Swahili");
		ad.save(it);
		/*try{System.out.println("sleeping for 20 secs, check data..");
		// at this point version is 1
		TimeUnit.SECONDS.sleep(20);
		}
		catch(Exception e){e.printStackTrace();}*/
		// this does not work // it detached:
		//it.setCountry("BElgium");
		// now try to update outside a transaction:
		EntityManager em = ad.getEm();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// it needs to be merged as it is detached, so no updates are viewable.. 
		//System.out.println("before optimistic");
		//em.flush();
		//em.lock(it, LockModeType.OPTIMISTIC);
		
		/* this is optimistic read lock
		 * [EL Fine]: sql: 2014-12-15 21:27:01.048--ClientSession(33357534)--Connection(23512282)--UPDATE MULTIKEY SET COUNTRY = ?, VERSION = ? WHERE (((NAME = ?) AND (MYID = ?)) AND (VERSION = ?))
		 */
		
		//em.lock(it, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		//with this type of lock the version number is incremented regardless we maken an update to the object 
		// this is useful for cases where one to many relationship but notihing is actually 
		// changed in current entity (but maybe a foreign was added)
		/*
		 * [EL Fine]: sql: 2014-12-15 21:43:45.916--ClientSession(26213828)--Connection(32470978)--UPDATE MULTIKEY SET COUNTRY = ?, VERSION = ? WHERE (((NAME = ?) AND (MYID = ?)) AND (VERSION = ?))
	bind => [5 parameters bound]
		 */
	    //em.lock(it, lockModeType.);	
		//em.merge(it);
		System.out.println("before pessimistic");
		//em.lock(it, LockModeType.PESSIMISTIC_WRITE);
		
		/*
		 * before pessimistic
[EL Fine]: sql: 2014-12-15 22:06:23.54--ClientSession(9045504)--Connection(11934748)--SELECT NAME, MYID, COUNTRY, VERSION FROM MULTIKEY WHERE ((NAME = ?) AND (MYID = ?)) FOR UPDATE WITH RS
	bind => [2 parameters bound]
after pessimistic
[EL Fine]: sql: 2014-12-15 22:06:23.546--ClientSession(9045504)--Connection(11934748)--UPDATE MULTIKEY SET COUNTRY = ?, VERSION = ? WHERE (((NAME = ?) AND (MYID = ?)) AND (VERSION = ?))
	bind => [5 parameters bound]
		
		 *we have a version field so deferred locking still ok
		 *but better is to use em.refresh(id,LOCKMODETYPE.PESSIMISTIC_WRITE)
		 */
		
		//em.lock(it, LockModeType.PESSIMISTIC_READ);
		//results in same sql for me but not often use
		em.lock(it, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
		// also updates version field even without updating
		//it.setCountry("BElgium");
		//em.flush();
		System.out.println("after pessimistic");
		et.commit();
		//em.find(entityClass, primaryKey)
		
		
	}
	
	public static void testEmp()
	{
		Employee e = new Employee();
		e.setEmpid(1);
		e.setName("Tom");
		
		ParkingSpace park = new ParkingSpace();
		park.setParkingId(1);
		park.setParkingname("tomsparking");
		park.setEmp(e);
		// this is really required:
		e.setParking(park);
		
		/*
		 * assume above will create entity in NEW 
		 * then when persisting it should go to managed
		 * 
		 */
		MULTIKEYDAO ad = new MULTIKEYDAO();
		EntityManager em = ad.getEm();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(park);
		et.commit();
		Map props = new HashMap();
		props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //this is the default 
		//props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //bypass the cache				
		//props.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH); // explicitly refreshes
		props.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH); // default, does not refresh if available
		ParkingSpace ps = em.find(ParkingSpace.class, 1,props);
		System.out.println("space " + ps.toString());
	}

}
