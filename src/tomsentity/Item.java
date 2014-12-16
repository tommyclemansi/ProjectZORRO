/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsentity;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.EAGER;

/**
 * Entity implementation class for Entity: Item
 *
 */
@Entity

@Table(name = "ITEM")
public class Item implements Serializable {
    /*
     * // provider must assign pk for entity using db identity column
     */
	@GeneratedValue(strategy=SEQUENCE, generator = "ITEMSEQ")
	@Id    	
	@SequenceGenerator(name = "ITEMSEQ")
	private Integer ItemNO;
	private String description;
	private Double price;
	
	
	/*
	 * this seems to generate a table:
	 * ITEMS_KEYWORDS
	 * this contains values 
	 * 
	 * ITEMID KEYWORDS
	 * 
	 */
	
	@ElementCollection(fetch = EAGER)
	private List<String> keywords = new ArrayList<String>();
	
	@Override
	public String toString() {
		return "Item [ItemNO=" + ItemNO + ", description=" + description
				+ ", price=" + price + ", keywords=" + keywords + ", bag="
				+ bag + "]";
	}


	public List<String> getKeywords() {
		//return keywords;
		return Collections.unmodifiableList(keywords);// not sure why
	}


	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
	public void addKeyword(String keyword)
	{keywords.add(keyword);}

	// actually targetEntity is not needed here, due to the fact that Bag is the Class name
	// so it knows that information
	// question is, will this persist the Bag, when calling save on the bag?
	// this is now the owning side, since I removed the annot on other side
	// JoinColumn can be used here due to the fact that the FK is on this table
	//@JoinColumn(name="BAG_BAGNO")
	@JoinColumn(name="BAGGY")
	@ManyToOne(targetEntity = tomsentity.Bag.class, cascade = ALL)
	//@Column(name="BAGGY")
	private Bag bag;
	/*
	[EL Warning]: 2014-12-04 12:35:17.374--UnitOfWork(24138443)--java.lang.IllegalStateException: During synchronization a new object was found through a relationship that was not marked cascade PERSIST: tomsentity.Item@13db7a7.
	Exception in thread "main" javax.persistence.RollbackException: java.lang.IllegalStateException: During synchronization a new object was found through a relationship that was not marked cascade PERSIST: tomsentity.Item@13db7a7.
		at org.eclipse.persistence.internal.jpa.transaction.EntityTransactionImpl.commit(EntityTransactionImpl.java:157)
		at tomsentity.BagDAO.save(BagDAO.java:30)
		at tomsUnitentity.myMain.main(myMain.java:30)
	Caused by: java.lang.IllegalStateException: During synchronization a new object was found through a relationship that was not marked cascade PERSIST: tomsentity.Item@13db7a7.
		at org.eclipse.persistence.internal.sessions.RepeatableWriteUnitOfWork.discoverUnregisteredNewObjects(RepeatableWriteUnitOfWork.java:310)
		at org.eclipse.persistence.internal.sessions.UnitOfWorkImpl.calculateChanges(UnitOfWorkImpl.java:723)
		at org.eclipse.persistence.internal.sessions.UnitOfWorkImpl.commitToDatabaseWithChangeSet(UnitOfWorkImpl.java:1516)
		at org.eclipse.persistence.internal.sessions.RepeatableWriteUnitOfWork.commitRootUnitOfWork(RepeatableWriteUnitOfWork.java:277)
		at org.eclipse.persistence.internal.sessions.UnitOfWorkImpl.commitAndResume(UnitOfWorkImpl.java:1169)
		at org.eclipse.persistence.internal.jpa.transaction.EntityTransactionImpl.commit(EntityTransactionImpl.java:132)
		... 2 more*/
	public Bag getBag() {
		return bag;
	}


	public void setBag(Bag bag) {
		this.bag = bag;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

	public Item() {
		super();
	}   
	

	public Integer getItemNO() {
		return this.ItemNO;
	}
	
	/*
	 * 
	 * [EL Info]: connection: 2014-12-04 09:47:56.738--ServerSession(26975285)--file:/C:/Users/tcleyman/workspace/MyJPA1/build/classes/_MyJPA1 login successful
Exception in thread "main" java.lang.IllegalArgumentException: Object: [tomsentity.Item@e45fa2, tomsentity.Item@e45fa2] is not a known entity type.
	at org.eclipse.persistence.internal.sessions.UnitOfWorkImpl.registerNewObjectForPersist(UnitOfWorkImpl.java:4228)
	at org.eclipse.persistence.mappings.ObjectReferenceMapping.cascadeRegisterNewIfRequired(ObjectReferenceMapping.java:983)
	at org.eclipse.persistence.mappings.ObjectReferenceMapping.cascadeRegisterNewIfRequired(ObjectReferenceMapping.java:959)
	at org.eclipse.persistence.internal.descriptors.ObjectBuilder.cascadeRegisterNewForCreate(ObjectBuilder.java:2542)
	at org.eclipse.persistence.internal.sessions.UnitOfWorkImpl.registerNewObjectForPersist(UnitOfWorkImpl.java:4241)
	at org.eclipse.persistence.internal.jpa.EntityManagerImpl.persist(EntityManagerImpl.java:496)
	at tomsentity.BagDAO.save(BagDAO.java:29)
	at tomsUnitentity.myMain.main(myMain.java:30)

	 * 
	 * 
	 * 
	 */

	public void setItemNO(Integer ItemNO) {
		System.out.println("printing item number: " + ItemNO);
		this.ItemNO = ItemNO;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
   
}
