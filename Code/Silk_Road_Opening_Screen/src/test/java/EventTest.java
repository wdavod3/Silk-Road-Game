import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import abstract_events.EventChoice;
import trade.Bonus;
import trade.City;
import trade.Faction;
import trade.LocationsData;
import trade.SilkRoadGame;
import trade.Trader;
import abstract_events.GameEvent;
import actual_events.GlobalCityDestroyed;
import actual_events.GlobalDrought;
import actual_events.GlobalPlentifulHarvest;
import actual_events.LocalBrokenWagon;
import actual_events.LocalItemGift;
import actual_events.LocalSandstorm;

public class EventTest {
	SilkRoadGame curGame;
	GameEvent basicEvent;
	LocationsData allCities;
	Trader p1;
	
	
	@BeforeEach
	void setup() {
		
		curGame = new SilkRoadGame();
		Faction rome = new Faction("Rome", curGame.getCity("Rome"), Bonus.Imperial_Legacy);
		
		allCities = new LocationsData("src/test/resources/samplePost.json", "src/test/resources/sampleCity.json");

		curGame.setTrader("Chris", rome);
		p1 = new Trader("Chris", rome);
	
		//Make 5 turns pass
		assertEquals(0 ,curGame.getCurrentTurn());
		curGame.increaseTurn();
		curGame.increaseTurn();
		curGame.increaseTurn();
		curGame.increaseTurn();
		curGame.increaseTurn();
		assertEquals(5 ,curGame.getCurrentTurn());
	}
	
	
	//Example with fight
	@Test
	void eventBanditFightTest() {
		assertEquals(5 ,curGame.getCurrentTurn());
		
		//Check if 5 turns have passed
		if(curGame.getCurrentTurn() % 5 == 0) {
			//get event info for UI
			curGame.getEventType();
			curGame.getEventMessage();
			
			//check if event has interactive actions for UI
			if(curGame.eventHasChoices()) {
				//Get all choices for UI buttons
				ArrayList<EventChoice> possibleChoices = curGame.getEventChoices();
				
				//player picked to fight here
				EventChoice curChoice = possibleChoices.get(0);
				assertEquals(EventChoice.Fight, curChoice);
				curGame.pickEventChoice(curChoice);
				
				//get result message string
				curGame.getEventResult();
				assertEquals( curGame.getEventResult(), "You and the bandit were evenly matched so he ran away.");
				
				assertEquals(10 ,curGame.getWallet());
			}
			//should not happen for events with choices
			else {
				curGame.pickEventChoice(EventChoice.NO_CHOICES_AVAILABLE);
				curGame.getEventResult();
				assertEquals(10 ,curGame.getWallet());
			}
		}
	}
	//Example with run
		@Test
		void eventBanditRunTest() {
			assertEquals(5 ,curGame.getCurrentTurn());
			
			//Check if 5 turns have passed
			if(curGame.getCurrentTurn() % 5 == 0) {
				//get event info for UI
				curGame.getEventType();
				curGame.getEventMessage();
				
				//check if event has interactive actions for UI
				if(curGame.eventHasChoices()) {
					//Get all choices for UI buttons
					ArrayList<EventChoice> possibleChoices = curGame.getEventChoices();
					
					//player picked to fight here
					EventChoice curChoice = possibleChoices.get(1);
					assertEquals(EventChoice.Run, curChoice);
					curGame.pickEventChoice(curChoice);
					
					//get result message string
					curGame.getEventResult();
					
					assertEquals(10 ,curGame.getWallet());
				}
				//should not happen for events with choices
				else {
					curGame.pickEventChoice(EventChoice.NO_CHOICES_AVAILABLE);
					curGame.getEventResult();
					assertEquals(10 ,curGame.getWallet());
				}
			}
		}
		
		//Drought
		@ParameterizedTest 
		@ValueSource(strings = {"silk", "tea"})
		void droughtTest(String input){
			//Before
			double oldPrice = allCities.getCity("Aksu").findItemCost(input, true);
			assertEquals(true, allCities.getCity("Aksu").itemIsForSale(input));
			
			//Event
			basicEvent = new GlobalDrought(allCities);
			basicEvent.changeData();
			
			//After
			assertEquals(oldPrice * 1.25, allCities.getCity("Aksu").findItemCost(input, true));
		}
		
		//harvest
		@ParameterizedTest 
		@ValueSource(strings = {"silk", "tea"})
		void harvestTest(String input){
			//Before
			double oldPrice = allCities.getCity("Aksu").findItemCost(input, true);
			assertEquals(true, allCities.getCity("Aksu").itemIsForSale(input));
			
			//Event
			basicEvent = new GlobalPlentifulHarvest(allCities);
			basicEvent.changeData();
			
			//After
			assertEquals(oldPrice * 0.75, allCities.getCity("Aksu").findItemCost(input, true));
		}
		
		//city destroyed
		@Test 
		void cityDestroyedTest(){
			//Before
			City curCity = allCities.getCity("defaultPlace");
			int numCities = allCities.getCitiesSize();
			assertEquals(false, curCity == null);
			assertEquals(numCities, 3);
			
			//Event
			basicEvent = new GlobalCityDestroyed(allCities);
			basicEvent.changeData();
			
			//After
			curCity = allCities.getCity("defaultPlace");
			numCities = allCities.getCitiesSize();
			assertEquals(true, curCity == null);
			assertEquals(numCities, 2);
		}
		
		//gift
		@ParameterizedTest 
		@ValueSource(ints = {0,1,2,10})
		void giftTest(int input){
			//Before
			double curGold = p1.getWallet();
			assertEquals(10.0, curGold);
			
			//Event
			for(int i = 0; i < input; ++i) {
				basicEvent = new LocalItemGift(p1);
				basicEvent.changeData();
			}
			
			//After
			curGold = p1.getWallet();
			assertEquals(10.0 + 10.0 * input, curGold);
		}
		
		//Broken wagon
		@Test
		void brokenWagonTest(){
			//Before
			int curAction = p1.getCurrentActionPoints();
			assertEquals(3, curAction);
			
			//Event
			basicEvent = new LocalBrokenWagon(p1);
			basicEvent.changeData();
			
			
			//After
			curAction = p1.getCurrentActionPoints();
			assertEquals(0, curAction);
		}
		
		//Broken wagon
		@Test
		void sandstormTest(){
			//Before
			int curAction = p1.getCurrentActionPoints();
			assertEquals(3, curAction);
			
			//Event
			basicEvent = new LocalSandstorm(p1);
			basicEvent.changeData();
			
			
			//After
			curAction = p1.getCurrentActionPoints();
			assertEquals(1, curAction);
		}
		
		
}
