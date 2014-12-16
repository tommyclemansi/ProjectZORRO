package tomsentity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.ALL;

@Entity
public class ParkingSpace {

	@Override
	public String toString() {
		return "ParkingSpace [parkingId=" + parkingId + ", parkingname="
				+ parkingname + ", emp=]";// + emp.getName() + "]";
	}
	@Id
	private int parkingId;
	private String parkingname;
	
	/*
	 * this is the inverse side
	 * this means I should specify a MappedBy
	 */
	//@OneToOne(mappedBy = "parking", cascade = PERSIST)
	//private Employee emp;
	
	@ManyToMany(mappedBy = "parkingcol")
	@JoinTable
	(name = "TEST_TABLE", 
		joinColumns = 
		@JoinColumn(name = "parkingcol_parkingId"), 
	    inverseJoinColumns = 
	    @JoinColumn(name = "empcol_empid"))
	private Collection<Employee> empcol;
	
	public Collection<Employee> getEmpcol() {
		return empcol;
	}
	public void setEmpcol(Collection<Employee> empcol) {
		this.empcol = empcol;
	}
	public int getParkingId() {
		return parkingId;
	}
	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
	}
	public String getParkingname() {
		return parkingname;
	}
	public void setParkingname(String parkingname) {
		this.parkingname = parkingname;
	}
	/*public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}*/
	
}
