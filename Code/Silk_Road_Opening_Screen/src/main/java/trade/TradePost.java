

//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//
//public class trade.Post {
//	private String name;
//	private HashMap<String, Double> sellPrices;
//	private HashMap<String, Double> buyPrices;
//	
//	private HashMap<String, Integer> itemsToBuild;
//	private Double costToBuild;
//	
//	//default constructor
//	trade.Post(){
//		//name = "Default";
//		sellPrices =  new HashMap<String, Double>();
//		buyPrices =  new HashMap<String, Double>();
//		itemsToBuild =  new HashMap<String, Integer>();
//		costToBuild = 100.0;
//	}
//	
//	//creates post with name and cost from JSON object
//	trade.Post(JSONObject curPost){
//		//get space for structures
//		sellPrices =  new HashMap<String, Double>();
//		buyPrices =  new HashMap<String, Double>();
//		itemsToBuild =  new HashMap<String, Integer>();
//		
//		//Parse the JSON object
//		name = (String) curPost.get("name");
//		costToBuild = (Double) curPost.get("buildCost");
//		
//		//parse the sell prices
//		JSONObject sellList = (JSONObject) curPost.get("canSell");
//		JSONArray sellItem = (JSONArray) sellList.get("items");
//		JSONArray sellCost = (JSONArray) sellList.get("cost");
//		for(int i = 0; i < sellItem.size(); ++i) {
//			sellPrices.put((String )sellItem.get(i), (Double) sellCost.get(i) );
//		}
//		
//		//parse the buy prices
//		JSONObject buyList = (JSONObject) curPost.get("canBuy");
//		JSONArray buyItem = (JSONArray) buyList.get("items");
//		JSONArray buyCost = (JSONArray) buyList.get("cost");
//		for(int i = 0; i < buyItem.size(); ++i) {
//			buyPrices.put((String )buyItem.get(i), (Double) buyCost.get(i) );
//		}
//		
//		//parse the build list
//		JSONObject buildList = (JSONObject) curPost.get("buildMaterials");
//		JSONArray buildItem = (JSONArray) buildList.get("items");
//		JSONArray buildQty = (JSONArray) buildList.get("qty");
//		for(int i = 0; i < buildItem.size(); ++i) {
//			itemsToBuild.put((String )buildItem.get(i), (int)(long) buildQty.get(i));
//		}
//	}
//	
//	//returns cost of sellable item at this post 
//	public Double findItemCost(String item, Boolean isBuying) {
//		Double cost;
//		
//		//check which list we want
//		if(isBuying == true) {
//			cost = buyPrices.get(item);
//		}
//		else {
//			cost = sellPrices.get(item);
//		}
//		
//		//if item not available
//		if(cost == null){
//			return 0.0;
//		}
//		return cost;
//	}
//	
//	//check if this item is for sale here
//	public Boolean itemIsForSale(String item) {
//		if(buyPrices.get(item) == null) {
//			return false;
//		}
//		return true;
//	}
//	
//	//get functions for private variables
//	public HashMap<String, Integer> getMaterials() {
//		return itemsToBuild;
//	}
//	public HashMap<String, Double> getSellList() {
//		return sellPrices;
//	}
//	public HashMap<String, Double> getBuyList() {
//		return buyPrices;
//	}
//	
//	public Double getCost(){
//		return costToBuild;
//	}
//	public String getName() {
//		return name;
//	}
//	
//	//print functions, used for debugging
//	public void printPost() {
//		System.out.println("Name: " + name);
//		System.out.println("Cost: " + costToBuild);
//		
//		System.out.println("Sell Items and Cost: ");
//		for(Map.Entry<String, Double> entry : sellPrices.entrySet()) {
//			String i = entry.getKey();
//			Double c = entry.getValue();
//			System.out.println("Item: " + i + " = " + c);
//		};
//		
//		System.out.println("Buy Items and Cost: ");
//		for(Map.Entry<String, Double> entry : buyPrices.entrySet()) {
//			String i = entry.getKey();
//			Double c = entry.getValue();
//			System.out.println("Item: " + i + " = " + c);
//		};
//		
//		System.out.println("Build Items and Qty: ");
//		for(Map.Entry<String, Integer> entry : itemsToBuild.entrySet()) {
//			String i = entry.getKey();
//			Integer c = entry.getValue();
//			System.out.println("Item: " + i + " = " + c);
//		};
//	}
//	
//}

