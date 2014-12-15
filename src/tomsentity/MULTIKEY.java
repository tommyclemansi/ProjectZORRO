package tomsentity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: MULTIKEY
 *
 */

/*
 * implemented here also toString & hashcode but not needed at all
 *  Other approach is to create MULTICLASS as embeddable class
 *  then use EmbeddedId in this class
 *  (normal Embedded)
 */
@Entity
@IdClass(MULTICLASS.class)
public class MULTIKEY implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public MULTIKEY() {
		super();
	}
	
	/* here I use 2 Ids.. 
	 * 
	 */
	@Id
	private int myID;
	@Id
	private String name;
	private String country;
	/*
	 * test optimistic locking
	 */
    @Version
	private int version;
	public int getMyID() {
		return myID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + myID;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MULTIKEY other = (MULTIKEY) obj;
		if (myID != other.myID)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public void setMyID(int myID) {
		this.myID = myID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
   
}
