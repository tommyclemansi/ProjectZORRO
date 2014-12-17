/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsentity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.TemporalType.TIME;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.EnumType.STRING;
//stupidchange
/**
 * Entity implementation class for Entity: Bag
 *
 */
@Entity
@Table(name = "BAG")
public class Bag implements Serializable {

	
	/*
	 * IDENTITY:
	 * This will enable the Entity to leverage on the AUTO_INCREMENT feature in automatically generating a sequential number as primary key when inserted into the database.
Databases which are compatible:

    MySQL
    Microsoft SQL Server
    IBM DB2 ver 7.1 and later
    
    (AUTO_INCREMENT)

- See more at: http://www.developerscrappad.com/408/java/java-ee/ejb3-jpa-3-ways-of-generating-primary-key-through-generatedvalue/#sthash.mSWWEjei.dpuf
	 
	 *SEQUENCE for when you have a DB sequence 
	 *also define a SequenceGenerator 
	 *Oracle DB
PostgreSQL
IBM DB2 ver 7.2

TABLE: use a Database table to store sequence (if you want to be compatible) 
@TableGenerator(name="justauniquenameapp_users", calalog="my_table",schema="")
// @TableGenerator( name = "appSeqStore", table = "APP_SEQ_STORE", pkColumnName = "APP_SEQ_NAME", pkColumnValue = "APP_USERS.APP_USERS_PK", valueColumnName = "APP_SEQ_VALUE", initialValue = 1, allocationSize = 1 ) - See more at: http://www.developerscrappad.com/408/java/java-ee/ejb3-jpa-3-ways-of-generating-primary-key-through-generatedvalue/#sthash.mSWWEjei.dpuf
then a table app_users will be used, catalog is just a varchar 
http://www.developerscrappad.com/408/java/java-ee/ejb3-jpa-3-ways-of-generating-primary-key-through-generatedvalue/
	auto: this means the provider will choose for you
	 *
	 */
	   
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "BAGSEQ")
	@SequenceGenerator(name = "BAGSEQ")
	private int BagNo;
	
	/**
	 * @return
	 * @see java.lang.Enum#name()
	 */
	public final String name() {
	System.out.println("test");
		return status.name();
	}

	@Temporal(TIME)
	private Date Buydate;
	
	
	/*
	 * this generates a STATUS field of type VARCHAR2
	 * 
	 */
	
	/**
	 * @param arg0
	 * @deprecated
	 * @see java.util.Date#setHours(int)
	 */
	public void setHours(int arg0) {
		Buydate.setHours(arg0);
	}

	@Enumerated(STRING) // means it will store it as a varchar iso integer
	private Status status;
	
	//@ManyToOne( targetEntity = tomsentity.Item.class, cascade = ALL)
	// @JoinColumn(name="ID")
	// One bag has many Items so above is wrong.. 
	// unidirectional, no code changes required for target;.  
	//@OneToMany(cascade = ALL)//mappedby to specify the field of the other side..
	// but maybe this is wrong as.. actually in db itself the fk should be in the Item Class
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	//@JoinColumn(columnDefinition = "ItemNo") 
    // will just remove this part... 
	//private List<Item> items;
	/*
	 * [EL Info]: connection: 2014-12-04 09:50:19.786--ServerSession(20207733)--file:/C:/Users/tcleyman/workspace/MyJPA1/build/classes/_MyJPA1 login successful
     Exception in thread "main" java.lang.IllegalArgumentException: Object: [tomsentity.Item@deb1ed, tomsentity.Item@deb1ed] is not a known entity type.
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
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bag() {
		super();
	}   
	public int getBagNo() {
		
		return this.BagNo;
	}

	public void setBagNo(int BagNo) {
		System.out.println("printing item number: " + BagNo);
		this.BagNo = BagNo;
	}   
	public Date getBuydate() {
		return this.Buydate;
	}

	public void setBuydate(Date Buydate) {
		this.Buydate = Buydate;
	}   
	/*public List getItems() {
		return this.items;
	}

	public void setItems(List items) {
		this.items = items;
	}*/
   
	
	//@PrePersist
	// not possible in SE
}
