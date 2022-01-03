package trade;

import java.util.ArrayList;
import java.util.HashMap;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.GameEvent;
import actual_events.LocalBanditAttack;

//import opening.Trader;

public class SilkRoadGame {
	public Trader curPlayer;
	private LocationsData postsAndCities;
	
	private Integer numTurns;
	private ArrayList<GameEvent> allEvents;
	private GameEvent curEvent;
	
	//Constructor
	public SilkRoadGame() {
		numTurns = 0;
		postsAndCities = new LocationsData("src/main/resources/3Posts.json","src/main/resources/3Cities.json");
		allEvents = new ArrayList<GameEvent>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//																										  //
	//											   Event Functions											  //
	//																										  //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////Getters for UI////////////////////////////////////////////////
	
	//Get current turn number
	public Integer getCurrentTurn() {
		return numTurns;
	}
	//Get event type
	public EventType getEventType() {
		return curEvent.getType();
	}
	//Get event message
	public String getEventMessage() {
		return curEvent.getMessage();
	}
	//Get event result
	public String getEventResult() {
		return curEvent.getResult();
	}
	//Get choices available for event
	public ArrayList<EventChoice> getEventChoices() {
		return curEvent.getChoices();
	}
	////////////////////////////////////////////////Checkers////////////////////////////////////////////////////
	
	//Check if event has choices
	public Boolean eventHasChoices() {
		return curEvent.hasChoices();
	}
	
	////////////////////////////////////////////Action Functions////////////////////////////////////////////////
	
	//Increase Turn by one and return
	public Integer increaseTurn() {
		++numTurns;
		
		//get Event every 5 turns
		if(numTurns % 5 == 0) {
//change this after adding more events
			curEvent = allEvents.get(0);
		}
		return numTurns;
	}
	
	//Pick how to handle event
	public void pickEventChoice(EventChoice choice) {
		curEvent.setCurrentChoice(choice);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	  																									  //
	// 												City Functions                                            //
	//																										  //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Returns city from locations data
	public City getCity(String inName) {
		
		return postsAndCities.getCity(inName);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//																										  //
	//											   Trader Functions											  //
	//																										  //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////Getters for UI////////////////////////////////////////////////
	
	//Set the trader to make a new one here
//	public void setTrader(String name, Bonus type) {
//		curPlayer = new Trader(name, Bonus.Imperial_Legacy);
//		allEvents.add(new LocalBanditAttack(curPlayer));
//	}

	public void setTrader(String name, Faction faction) {
		curPlayer = new Trader(name, faction);
		allEvents.add(new LocalBanditAttack(curPlayer));
	}

	//Get Trader's Name
	public String getTraderName() {
		return curPlayer.getName();
	}
	//Get Trader Type
//	public Bonus getTraderType() {
//		return curPlayer.getTraderType();
//	}


	//Get current Action Points
	public Integer getCurrentActionPoints() {
		return curPlayer.getCurrentActionPoints();
	}
	//Get current gold value
	public Double getWallet() {
		return curPlayer.getWallet();
	}
	//Get all inventory as a HashMap
	public HashMap<String, Integer> getInventory(){
		return curPlayer.getInventory();
	}

	////////////////////////////////////////////Action Functions////////////////////////////////////////////////
	
	//Buy Item with selected quantity
	public boolean buyItems(String item, City curCity, Integer qty) {
		if(curPlayer.canBuy(item, curCity, qty)){
			curPlayer.buyItems(item, curCity, qty);
			curPlayer.takeAction(1);
			return true;
		}
		else{
			return false;
		}
	}
	//Sell Item with selected quantity
	public boolean sellItems(String item, City curCity, Integer qty) {
		if(curPlayer.canSell(item, qty)){
			curPlayer.sellItems(item, curCity, qty);
			curPlayer.takeAction(1);
			return true;
		}
		else{
			return false;
		}
	}
	//Create a post and assign to Trader
	public Post constructPost(String newName, String type) {
		return postsAndCities.constructPost(newName, type, curPlayer);
	}
	//Takes action with inputed value
	public Integer takeAction(Integer actionVal) {
		return curPlayer.takeAction(actionVal);
	}
	//resets action points to Trader's max
	public void endTurn() {
		//Process Barbarian Turns

		//Reset Player Action Points
		curPlayer.resetActionPoints();
	}
}
