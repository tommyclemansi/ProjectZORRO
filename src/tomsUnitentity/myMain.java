/*******************************************************************************
 * Copyright (c) 2014 Tom Cleymans
 *******************************************************************************/
package tomsUnitentity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import tomsentity.Bag;
import tomsentity.BagDAO;
import tomsentity.Item;
import tomsentity.ItemDAO;
import tomsentity.Status;

public class myMain {

	public static void main(String[] args) {
		String test;
		// TODO Auto-generated method stub
		//BagDAO bd = new BagDAO();
		Bag bag = new Bag();
		bag.setBuydate(new GregorianCalendar().getTime());
		bag.setStatus(Status.OPEN);
		Item it = new Item();
		it.setDescription("tomsdescription");
		it.setPrice(10.22);
		it.setBag(bag);
		it.addKeyword("tomstest");
		it.addKeyword("tomssecondTest");
	//	it.s
		ItemDAO ad = new ItemDAO();
		Item it2 = new Item();
		it2.setDescription("item2");
		it2.setBag(bag);
		it2.setPrice(33333.999);
		
		it2.addKeyword("tomstest2");
		it2.addKeyword("tomssecondTest2");
		/*List<Item> items = new ArrayList<Item>();
		items.add(it);
		items.add(it2);
		bag.setItems(items);*/
		// need t do it in this way: 
		// this due to the fact I used unidirectional
		// assume unidirectional can only be manytoOne
		ad.save(it);
		// this causes duplicate key... 
		/*
		 * per my understanding the item is detached.. 
		 * and so it tries to persist it again; 
		 */
		ad.save(it2);
		//Bag bag2 = bd.save(bag);
	
        System.out.println("succeeded");
        Item item = ad.findByPrimaryKey(1);
        System.out.println("ITEM result: "  + item.toString());
	}
	
	
	

}
