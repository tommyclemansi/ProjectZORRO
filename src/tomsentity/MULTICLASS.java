/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsentity;

import java.io.Serializable;

import javax.persistence.Id;

public class MULTICLASS implements Serializable{
	
	/*
	 * this must match the names forming PK of MULTIKEY
	 * must implement equals and hashcode
	 */

	private static final long serialVersionUID = 1L;

	public MULTICLASS() {
		super();
	}
	
	private int myID;
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
		MULTICLASS other = (MULTICLASS) obj;
		if (myID != other.myID)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public int getMyID() {
		return myID;
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

	private String name;
	
}
