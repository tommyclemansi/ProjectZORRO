/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsentity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MULTIKEYDAO {
	private static EntityManager em=null;
	//static must be after em else NPE
	static 
	{ // when class is loaded create the entitymanager
		 em = Persistence.createEntityManagerFactory("MyJPA1").createEntityManager();
	}
	
	
	
	public static EntityManager getEm() {
		return em;
	}



	public MULTIKEY save(MULTIKEY item)
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
}
