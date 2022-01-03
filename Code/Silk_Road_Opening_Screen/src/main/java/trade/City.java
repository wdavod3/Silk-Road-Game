package trade;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class City {
	private String name;
	private HashMap<String, Double> sellPrices;
	private HashMap<String, Double> buyPrices;
	public Integer[] location = new Integer[2];

	//default constructor
	City(){
		//name = "Default";
		sellPrices =  new HashMap<String, Double>();
		buyPrices =  new HashMap<String, Double>();
	}
	
	//creates post with name and cost from JSON object
	City(JSONObject curPost){
		//get space for structures
		sellPrices =  new HashMap<String, Double>();
		buyPrices =  new HashMap<String, Double>();
		
		//Parse the JSON object
		name = (String) curPost.get("name");
		JSONArray jsonLocation = (JSONArray) curPost.get("location");

		for (int i = 0; i < 2; i++) {
			Long l = (Long) jsonLocation.get(i);
			location[i] = l.intValue();
		}
		//parse the sell prices
		JSONObject sellList = (JSONObject) curPost.get("canSell");
		JSONArray sellItem = (JSONArray) sellList.get("items");
		JSONArray sellCost = (JSONArray) sellList.get("cost");
		for(int i = 0; i < sellItem.size(); ++i) {
			sellPrices.put((String )sellItem.get(i), (Double) sellCost.get(i) );
		}


		//parse the buy prices
		JSONObject buyList = (JSONObject) curPost.get("canBuy");
		JSONArray buyItem = (JSONArray) buyList.get("items");
		JSONArray buyCost = (JSONArray) buyList.get("cost");
		for(int i = 0; i < buyItem.size(); ++i) {
			buyPrices.put((String )buyItem.get(i), (Double) buyCost.get(i) );
		}
	}
	
	//returns cost of sellable item at this post 
	public Double findItemCost(String item, Boolean isBuying) {
		Double cost;
		
		//check which list we want
		if(isBuying == true) {
			cost = buyPrices.get(item);
		}
		else {
			cost = sellPrices.get(item);
		}
		
		//if item not available
		if(cost == null){
			return 0.0;
		}
		return cost;
	}
	
	//check if this item is for sale here
	public Boolean itemIsForSale(String item) {
		if(buyPrices.get(item) == null) {
			return false;
		}
		return true;
	}
	
	//get functions for private variables
	public HashMap<String, Double> getSellList() {
		return sellPrices;
	}
	public HashMap<String, Double> getBuyList() {
		return buyPrices;
	}
	public String getName() {
		return name;
	}
	
	//print functions, used for debugging
	public void printPost() {
		System.out.println("Name: " + name);
		
		System.out.println("Sell Items and Cost: ");
		for(Map.Entry<String, Double> entry : sellPrices.entrySet()) {
			String i = entry.getKey();
			Double c = entry.getValue();
			System.out.println("Item: " + i + " = " + c);
		};
		
		System.out.println("Buy Items and Cost: ");
		for(Map.Entry<String, Double> entry : buyPrices.entrySet()) {
			String i = entry.getKey();
			Double c = entry.getValue();
			System.out.println("Item: " + i + " = " + c);
		};
	}

	@Override
	public String toString() {
		return "City{" +
				"name='" + name + '\'' +
				", sellPrices=" + sellPrices +
				", buyPrices=" + buyPrices +
				'}';
	}

	
	public void adjustPrices(double percent) {
		
		//adjust buy prices
		for(Map.Entry<String, Double> entry : buyPrices.entrySet()) {
			Double c = entry.getValue();
			c = c + c * percent;
			entry.setValue(c);
		};
		
		//adjust sell prices
		for(Map.Entry<String, Double> entry : sellPrices.entrySet()) {
			Double c = entry.getValue();
			c = c + c * percent;
			entry.setValue(c);
		};
		
		
		
	}
}
