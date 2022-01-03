package trade;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Post {
	private String name;
	private String type;
	
	private HashMap<String, Integer> itemsToBuild;
	private Double costToBuild;
	
	//default constructor
	Post(){
		//name = "Default";
		itemsToBuild =  new HashMap<String, Integer>();
		costToBuild = 100.0;
	}
	
	//copy constructor
	Post(String newName, Post postType){
		name = newName;
		type = postType.getType();
		itemsToBuild = postType.getMaterials();
//		itemsToBuild = new HashMap<String, Integer>();
//		HashMap<String, Integer> oldData = postType.getMaterials();
//		for(Map.Entry<String, Integer> entry : oldData.entrySet()) {
//			String i = entry.getKey();
//			Integer c = entry.getValue();
//			itemsToBuild
//			//System.out.println("Item: " + i + " = " + c);
//		};
		costToBuild = postType.getCost();
	}
	
	//creates post with name and cost from JSON object
	Post(JSONObject curPost){
		//get space for structures
		itemsToBuild =  new HashMap<String, Integer>();
		
		//Parse the JSON object
		type = (String) curPost.get("name");
		costToBuild = (Double) curPost.get("buildCost");
		
		//parse the build list
		JSONObject buildList = (JSONObject) curPost.get("buildMaterials");
		JSONArray buildItem = (JSONArray) buildList.get("items");
		JSONArray buildQty = (JSONArray) buildList.get("qty");
		for(int i = 0; i < buildItem.size(); ++i) {
			itemsToBuild.put((String )buildItem.get(i), (int)(long) buildQty.get(i));
		}
	}
	
	//get functions for private variables
	public HashMap<String, Integer> getMaterials() {
		return itemsToBuild;
	}
	public Double getCost(){
		return costToBuild;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	
	//print functions, used for debugging
	public void printPost() {
		System.out.println("Name: " + name);
		System.out.println("Type: " + type);
		System.out.println("Cost: " + costToBuild);
		
		System.out.println("Build Items and Qty: ");
		for(Map.Entry<String, Integer> entry : itemsToBuild.entrySet()) {
			String i = entry.getKey();
			Integer c = entry.getValue();
			System.out.println("Item: " + i + " = " + c);
		};
	}
	
}