/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsentity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-09T13:26:49.021+0100")
@StaticMetamodel(Bag.class)
public class Bag_ {
	public static volatile SingularAttribute<Bag, Integer> BagNo;
	public static volatile SingularAttribute<Bag, Date> Buydate;
	public static volatile SingularAttribute<Bag, Status> status;
}
