package tomsentity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.PERSIST;

import javax.persistence.JoinTable;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Employee2 {

	@Id
	private int empid;
    private String name;
    
    //using OneToMany as Item2 is an Entity
    // here an intermediate table will be generated
    // this contains employee as pk
    // + Item (a bit strange)
    // then the first value is the value being used 
    /*@OneToMany
    @MapKeyColumn(name="testtommy")//testtommy is the column name of the valu
    //@MapKey(name="ItemNO")  this assumes 
    private java.util.Map<String,Item2> mymap;
    */
    @OneToMany
    //@MapKeyColumn(name="testtommy")//testtommy is the column name of the valu
    @MapKey(name="ItemNO")  // in this case the item number is stored in the join column, no add data stored.. 
    private java.util.Map<String,Item2> mymap;
    // in this case the Item number in the table will be a Integer
    // so this means that our String is automagically converted.. 
    
    /*
     * one to One bidirectional
     * will make this the owning side
     * this by specifying JoinColumn
     */
    // in the table PARKING will be the joincolumn
    //Do not specify targetEntity in One on one since type is known
   
    //@JoinColumn(name="PARKING")
    //@NotNull
    //@OneToOne(cascade = PERSIST, optional = false)
    //private ParkingSpace parking;
    
    public java.util.Map<String, Item2> getMymap() {
		return mymap;
	}
	public void setMymap(java.util.Map<String, Item2> mymap) {
		this.mymap = mymap;
	}
	/*
     * test for ManyToMany
     * without joincolumn (but still owning side)
     */
    public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
