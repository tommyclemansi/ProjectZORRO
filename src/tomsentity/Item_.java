package tomsentity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-09T13:26:49.062+0100")
@StaticMetamodel(Item.class)
public class Item_ {
	public static volatile SingularAttribute<Item, Integer> ItemNO;
	public static volatile SingularAttribute<Item, String> description;
	public static volatile SingularAttribute<Item, Double> price;
	public static volatile ListAttribute<Item, String> keywords;
	public static volatile SingularAttribute<Item, Bag> bag;
}
