package tomsentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.PERSIST;

@Entity
public class Employee {

	@Id
	private int empid;
    private String name;
    /*
     * one to One bidirectional
     * will make this the owning side
     * this by specifying JoinColumn
     */
    // in the table PARKING will be the joincolumn
    //Do not specify targetEntity in One on one since type is known
    @JoinColumn(name="PARKING")
    @NotNull
    @OneToOne(cascade = PERSIST, optional = false)
    private ParkingSpace parking;
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
	public ParkingSpace getParking() {
		return parking;
	}
	public void setParking(ParkingSpace parking) {
		this.parking = parking;
	}

	
}
