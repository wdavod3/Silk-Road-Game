package trade;
import combat.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Trader extends Character{
	private String companyName;
	private Integer maxTravelDistance;
	private Integer curActionPoints;
	private Integer maxActionPoints;
	private Double wallet;
	private Faction faction;

	/* STAT EXPLANATION
	*  Luck decreases chance of bad events and increases chance of good events
	*  Barter improves the value distribution on buying and selling good
	*  Charisma increases chance to succeed in social interactions
	*  I.E. Convincing a group of bandits to leave you alone
	*  Attack increases damage given, percentage based
	*  Defense decreases damage taken, percentage based
	*  Stats range from 0 to 100
	*  Once a stat reaches 100, chance based events using that stat always succeed
	*  100 Luck means all events are good events
	* */

	// RPG Stats
	private Integer luck;
	private Integer barter;
	private Integer charisma;
//	private Integer attack;
//	private Integer defense;


	// RPG Stat Getters
	public Integer getLuck() {
		return luck;
	}

	public Integer getBarter() {
		return barter;
	}

	public Integer getCharisma() {
		return charisma;
	}

	// RPG Stat Setters
	public void setLuck(Integer luck) {
		if (luck > 100) {
			this.luck = 100;
			return;
		}
		this.luck = luck;
	}

	public void setBarter(Integer barter) {
		if (barter > 100) {
			this.barter = 100;
			return;
		}
		this.barter = barter;
	}

	public void setCharisma(Integer charisma) {
		if (charisma > 100) {
			this.charisma = 100;
			return;
		}
		this.charisma = charisma;
	}

	private HashMap<String, Integer> inventory;
	private ArrayList<String> ownedPosts;
	//private ArrayList<String> travelHistory;
	
//	//default constructor
//	Trader(){
//		super(1,1);
//		barter = 1;
//		charisma = 1;
//		luck = 1;
//
//		companyName = "Default";
//		traderType = Bonus.None;
//		maxTravelDistance = 1;
//		curActionPoints = 3;
//		maxActionPoints = 3;
//		wallet = 100.00;
//		hiredMercs = 0;
//
//		inventory = new HashMap<String, Integer>();
//		ownedPosts = new ArrayList<String>();
//		travelHistory = new ArrayList<String>();
//	}

	//constructor with given company name and starting owned post, and bonus type
	public Trader(String inputName, Faction faction){
		super(1,1);
		barter = 1;
		charisma = 1;
		luck = 1;
		
		companyName = inputName;
		maxTravelDistance = 1;
		curActionPoints = 3;
		maxActionPoints = 3;
		wallet = 10.0;
		//hiredMercs = 0;
		this.faction = faction;
		//Check for more staring money bonus
		if(faction.getBonusType() == Bonus.Economic_Giant) {
			wallet = wallet * 1.5;
		}
		
		inventory = new HashMap<String, Integer>();
		ownedPosts = new ArrayList<String>();
		//travelHistory = new ArrayList<String>();
	}
	
//	//constructor with given company name, initial starting amount, starting post, and bonus type
//	Trader(String inputName, Double startingMoney,  Bonus traderBonusType){
//		super(1,1);
//		barter = 1;
//		charisma = 1;
//		luck = 1;
//
//		companyName = inputName;
//		maxTravelDistance = 1;
//		curActionPoints = 3;
//		maxActionPoints = 3;
//		wallet = startingMoney;
//		hiredMercs = 0;
//		traderType = traderBonusType;
//
//		//Check for more staring money bonus
//		if(traderType == Bonus.Economic_Giant) {
//			wallet = wallet * 1.5;
//		}
//
//		inventory = new HashMap<String, Integer>();
//		ownedPosts = new ArrayList<String>();
//		travelHistory = new ArrayList<String>();
//	}

//	//constructor with given company name, initial starting amount, starting post, bonus type, and stats
//	Trader(String inputName, Double startingMoney,  Bonus traderBonusType,
//		   Integer luck, Integer barter, Integer charisma, Integer attack, Integer defense){
//		// Set Stats
//		super(attack, defense);
//		setBarter(barter);
//		setCharisma(charisma);
//		setLuck(luck);
//
//		companyName = inputName;
//		maxTravelDistance = 1;
//		curActionPoints = 3;
//		maxActionPoints = 3;
//		wallet = startingMoney;
//		hiredMercs = 0;
//		traderType = traderBonusType;
//
//		//Check for more staring money bonus
//		if(traderType == Bonus.Economic_Giant) {
//			wallet = wallet * 1.5;
//		}
//
//		inventory = new HashMap<String, Integer>();
//		ownedPosts = new ArrayList<String>();
//		travelHistory = new ArrayList<String>();
//
//	}
//
	
	//get private variables
	public String getName() {
		return companyName;
	}
	
	public Double getWallet() {
		return wallet;
	}
	public Integer getMaxTravelDist() {
		return maxTravelDistance;
	}
	public Integer getCurrentActionPoints() {
		return curActionPoints;
	}
//	public Integer getHiredMercs() {
//		return hiredMercs;
//	}
	public HashMap<String, Integer> getInventory(){
		return inventory;
	}
	
//	public Faction getTraderType() {
//		return traderType;
//	}

	public Faction getFaction(){
		return faction;
	}

	//User takes an action and returns remaining amount
	public Integer takeAction(Integer actionVal) {
		curActionPoints -= actionVal;
		return curActionPoints;
	}
	
	//reset action points to max value
	public Integer resetActionPoints() {
		curActionPoints = maxActionPoints;
		return curActionPoints;
	}
	
	//increase max action points by value
	public Integer increaseActionPoints(Integer increaseVal) {
		maxActionPoints += increaseVal;
		return maxActionPoints;
	}
	
//	//Hires given mercenaries and returns current amount
//	public Integer hireMerc(Integer count, Double costPer) {
//		hiredMercs += count;
//		wallet-= (costPer * count);
//		return hiredMercs;
//	}
	
	//returns true if trader owns post
	public Boolean ownsPost(Post search) {
		return (ownedPosts.contains(search.getName()));
	}
	
//	//marks post as visited
//		public void visitPost(Post post) {
//		travelHistory.add(post.getName());
//	}
//	
//	//returns true if trader has visited post
//		public Boolean hasVisited(Post search) {
//		return (travelHistory.contains(search.getName()));
//	}
	
	//check amount of item in inventory
	public Integer itemQty(String search) {
		Integer res = inventory.get(search);
		//none in inventory
		if(res == null) {
			return 0;
		}
		
		return res;
	}
	//broken
	//returns total price of selected items
	private Double getItemTotalCost(String item, City curCity, Integer qty, Boolean isBuying) {
		//get cost of all items;
		Double itemCost= curCity.findItemCost(item, isBuying);
		
		return itemCost * qty;
	}
	
	//buys selected item quantity at price for current post
	public Double buyItems(String item, City curCity, Integer qty) {
		//update wallet
		Double total = getItemTotalCost(item, curCity, qty, true);
		wallet -= total;
		
		//add items to inventory
		Integer curQty = inventory.get(item);
		if(curQty == null) {
			inventory.put(item, qty);
		}
		else {
			inventory.put(item, curQty + qty);
		}
		
		return wallet;
	}
	
	//sells selected item quantity at price for current post
	public Double sellItems(String item, City curCity, Integer qty) {
		//update wallet
		Double total = getItemTotalCost(item, curCity, qty, false);
		
		//Check for 5% increase bonus
		
		if(faction.getBonusType() == Bonus.Middlemen_of_the_Silk_Road) {
			total = total * 1.02;
		}
		
		wallet += total;
		
		//remove items from inventory
		Integer curQty = inventory.get(item);
		if(curQty == null) {
			//shouldn't happen, not sure where error handling will happen
		}
		inventory.put(item, curQty - qty);
		
		return wallet;
	}
	
	//removes item quantity from inventory
	private void removeItem(String item, Integer count) {
		Integer curQty = inventory.get(item);
		if(curQty == null) {
			curQty = 0;
		}
		curQty -= count;
		inventory.put(item, curQty);
		
	}
	
	
	
	//Creates post and uses up money and materials
	public void createPost(Post curPost) {
		//get info
		Double cost = curPost.getCost();
		HashMap<String, Integer> checklist = curPost.getMaterials();
		
		//Check for 25% off bonus
		if(faction.getBonusType() == Bonus.Imperial_Legacy) {
			cost = cost * 0.75;
		}
		//subtract materials;
		wallet -= cost;
		checklist.forEach((i,c) -> removeItem(i,c));
		
		
		
		//add item to posts owned
		ownedPosts.add(curPost.getName());
	}
	
	//check if you can build post
	public Boolean canBuild(Post curPost){
		//get cost/materials for post
		Double cost = curPost.getCost();
		HashMap<String, Integer> checklist = curPost.getMaterials();
		
		//check money
		if(cost > wallet) {
			return false;
		}
		
		//check materials
		for(Map.Entry<String, Integer> entry : checklist.entrySet()) {
			String i = entry.getKey();
			Integer c = entry.getValue();
			if(canSell(i,c) == false) {
				return false;
			}
		};
		return true;
	}
	
	//check if you can buy items
	public Boolean canBuy(String item, City curCity, Integer qty) {
		if(curCity.itemIsForSale(item) == false) {
			return false;
		}
		
		
		Double total = getItemTotalCost(item, curCity, qty, true);
		if(total > wallet) {
			return false;
		}

		if(curActionPoints == 0){
			return false;
		}
		return true;
	}
	
	//check if you can sell items
	public Boolean canSell(String item, Integer qty){
		Integer curQty = inventory.get(item);
		if(curQty == null || curQty < qty) {
			return false;
		}

		if(curActionPoints == 0){
			return false;
		}

		return true;
	}
	
	//lose money by a percentage
	public void loseMoney(Double percentLose) {
		Double goldLoss = wallet * percentLose;
		wallet -= goldLoss;
	}
	
	//adjust money by this value
	public void adjustMoney(Double value) {
		wallet += value;
	}
	
	
	
}
