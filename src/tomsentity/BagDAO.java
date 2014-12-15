/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsentity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class BagDAO {

	
	
	/*
	 * private method to get an entity manager
	 */
	private EntityManager getEntityManager()
	{ // so seems we request a new one every time.. 
	 EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyJPA1");
	 return emf.createEntityManager();
	}
	
	/*
	 * attempt to generalize the method:
	 */
	public Bag save(Bag bag)
	{
	EntityManager em = getEntityManager();
	EntityTransaction et = em.getTransaction();
	et.begin();
	em.persist(bag);
	et.commit();
	return bag;
	}
	

	public Item findByPrimaryKey(int id)
	{
		return getEntityManager().find(Item.class, 1);
	}

}
