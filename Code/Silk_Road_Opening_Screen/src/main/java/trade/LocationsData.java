package trade;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import trade.City;
import trade.Post;

public class LocationsData{
	
	private HashMap<String, Post> typeOfPosts;
	public HashMap<String, City> allCities;
	public HashMap<String, Post> constructedPosts;
	
	
	public LocationsData(){
		//allPosts = new HashMap<String, Post>();
	}
	
	private void parsePostObject(JSONObject post) {
		//get post object from list
		JSONObject curPost = (JSONObject) post.get("post");
		Post temp = new Post(curPost);
		typeOfPosts.put(temp.getType(), temp);
	}
	private void parseCityObject(JSONObject city) {
		//get post object from list
		JSONObject curCity = (JSONObject) city.get("city");
		City temp = new City(curCity);
		allCities.put(temp.getName(), temp);
	}
	
	
	public LocationsData(String postFile, String cityFile){
		constructedPosts = new HashMap<String, Post>();
		typeOfPosts = new HashMap<String, Post>();
		allCities = new HashMap<String, City>();
		//code written with help from howtodohjava.com
		
		//parser object
		JSONParser myParser = new JSONParser();
		
		//parse post file
		try(FileReader myReader = new FileReader(postFile)){
			//read file
			Object obj = myParser.parse(myReader);
			JSONArray postList = (JSONArray) obj;
			
			//iterate over the array of posts
			for(int i = 0; i < postList.size(); ++i) {
				parsePostObject((JSONObject) postList.get(i));
			}
		}
		catch(Exception e){
			//error handling
			//allPosts.put("error", new trade.Post());
			e.printStackTrace();
		}
		
		//parse city file
		myParser = new JSONParser();
		try(FileReader myReader = new FileReader(cityFile)){
			//read file
			Object obj = myParser.parse(myReader);
			JSONArray cityList = (JSONArray) obj;
			
			//iterate over the array of posts
			for(int i = 0; i < cityList.size(); ++i) {
				parseCityObject((JSONObject) cityList.get(i));
			}
		}
		catch(Exception e){
			//error handling
			//allPosts.put("error", new trade.Post());
			e.printStackTrace();
			
		}
		
	}
	
	public Post getPost(String inName) {
		return constructedPosts.get(inName);
	}
	public Integer getPostsSize() {
		return constructedPosts.size();
	}
	public Integer getTypeSize() {
		return typeOfPosts.size();
	}
	public Post getType(String inName) {
		return typeOfPosts.get(inName);
	}
	
	public City getCity(String inName) {
		return allCities.get(inName);
	}
	public Integer getCitiesSize() {
		return allCities.size();
	}
	public Boolean canConstructPost(String type, Trader p1) {
		return p1.canBuild(typeOfPosts.get(type));
	}
	
	public Post constructPost(String newName, String type, Trader p1) {
		Post curPost = typeOfPosts.get(type);
		Post builtPost = new Post(newName, curPost);
		//curPost.printPost();
		
		//builtPost.printPost();
		constructedPosts.put(newName, builtPost);
		//System.out.println("Before: " + p1.getWallet());
		p1.createPost(builtPost);
		//System.out.println("After: " + p1.getWallet());
		return builtPost;
	}
	public void adjustAllPrices(double percent){
		for(Map.Entry<String, City> entry : allCities.entrySet()) {
			City c = entry.getValue();
			c.adjustPrices(percent);
		};
	}
	
	public String deleteCity() {
		//delete first in map
		 Map.Entry<String,City> oldCity = allCities.entrySet().iterator().next();
		 String key = oldCity.getKey();
		 allCities.remove(key);
		 
		 return key;
	}
	
}
