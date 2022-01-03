import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import trade.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class TraderTest {
	static Trader p1;
	static Trader p2;
	static Post osh;
	static Post starting;
	static Post aksu;
	
	static City cityO;
	static City cityS;
	static City cityA;
	
	static ArrayList<String> allItems;
	static LocationsData allLoc;
	static LocationsData allLocReal;

	Faction none = new Faction("None", null, Bonus.None);
	Faction sell = new Faction("None", null, Bonus.Middlemen_of_the_Silk_Road);
	Faction build = new Faction("None", null, Bonus.Imperial_Legacy);
	Faction money = new Faction("None", null, Bonus.Economic_Giant);


	@BeforeEach
	void setup() {

		p1 = new Trader("Joe", none);
		p2 = new Trader("Chris",none);
		allLoc = new LocationsData("src/test/resources/samplePost.json", "src/test/resources/sampleCity.json");
		allLocReal = new LocationsData("src/test/resources/post.json", "src/test/resources/city.json");
		
		//allLoc.constructPost("Osh", "Osh", p1);
		//allLoc.constructPost("Aksu", "Aksu", p1);
		//allLoc.constructPost("defaultPlace", "defaultPlace", p1);
		
		osh = allLoc.getType("Osh");
		aksu = allLoc.getType("Aksu");
		starting = allLoc.getType("defaultPlace");
		
		cityO = allLoc.getCity("Osh");
		cityA = allLoc.getCity("Aksu");
		cityS = allLoc.getCity("defaultPlace");
		
	}
	
	//PostList
	@Test
	void JSONTest() {
		assertEquals(3, allLoc.getTypeSize());
		assertEquals(3, allLoc.getCitiesSize());
		
		assertEquals(8, allLocReal.getTypeSize());
		assertEquals(8, allLocReal.getCitiesSize());
		
		assertEquals(5.0, aksu.getCost());
		assertEquals("Aksu", aksu.getType());	
		//osh.printPost();
	}
	
	//trade.Trader Constructor
	@ParameterizedTest
	@ValueSource(strings = {"Christian", "Christopher", "Joel", "Will"})
	void constructorTest(String input) {
		p1 = new Trader(input, none);
		assertEquals(input, p1.getName());
		assertEquals(10.0, p1.getWallet());
	}
	
	//Check take action function
	@ParameterizedTest
	@ValueSource(ints = {3, 2, 1})
	void actionTest(int input) {
		assertEquals(3-input, p1.takeAction(input));
		assertEquals(3 - input, p1.getCurrentActionPoints());
		assertEquals(3, p1.resetActionPoints());
		assertEquals(5, p1.increaseActionPoints(2));
	}
	
//	@ParameterizedTest
//	@ValueSource(ints = {})
//	void tempTest(int input) {
//		
//		
//	}
	
	
	//Buy then sell
	@ParameterizedTest
	@ValueSource(ints = {5,1,0})
	void itemQtyTest(int input) {
		//Buy in Aksu twice
		assertEquals(0, p1.itemQty("silk"));
		p1.buyItems("silk", cityA, input);
		assertEquals(input, p1.itemQty("silk"));
		p1.buyItems("silk", cityA, input);
		assertEquals(input * 2, p1.itemQty("silk"));
		
		assertEquals(10.0 - input * 2, p1.getWallet());
		
		//Sell in Osh, profit
		p1.sellItems("silk", cityO, input * 2);
		assertEquals(0, p1.itemQty("silk"));
		
		assertEquals(1000.0 * input * 2 + 10 - input * 2, p1.getWallet());
	}
	
	//can Buy and Sell
	
	@ParameterizedTest
	@ValueSource(ints = {10,5,1})
	void canBuyAndSellTest(int input) {
		assertEquals(false, p1.canBuy("silk", cityO, input));
		assertEquals(true, p1.canBuy("silk", cityA, input));
		
		assertEquals(false, p1.canSell("silk", input));
		p1.buyItems("silk", cityA, input);
		assertEquals(true, p1.canSell("silk", input));
	}

	
	//can build and build functions
	@Test
	void canBuildPostTest() {
		//inital check
		assertEquals(false, p1.canBuild(osh));
		assertEquals(false, p1.canBuild(aksu));
		
		//buy materials needed
		p1.buyItems("silk", cityA, 1);
		p1.buyItems("tea", cityA, 2);
		assertEquals(1, p1.itemQty("silk"));
		assertEquals(2, p1.itemQty("tea"));
		assertEquals(0, p1.itemQty("spices"));
		assertEquals(0, p1.itemQty("sugar"));
		assertEquals(0, p1.itemQty("salt"));
		assertEquals(0, p1.itemQty("porcelain"));
		
		//second check
		assertEquals(false, p1.canBuild(osh));
		assertEquals(true, p1.canBuild(aksu));
		assertEquals(5.0, p1.getWallet());
		
		//build it
		p1.createPost(aksu);
		assertEquals(0, p1.itemQty("silk"));
		assertEquals(0, p1.itemQty("tea"));
		assertEquals(0, p1.itemQty("spices"));
		assertEquals(0, p1.itemQty("sugar"));
		assertEquals(0, p1.itemQty("salt"));
		assertEquals(0, p1.itemQty("porcelain"));
		
		assertEquals(0, p1.getWallet());
		
		assertEquals(true, p1.ownsPost(aksu));
	}
	//Imperial Legacy bonus
	@Test
	void postBuildBonus() {
		p1 = new Trader("Joe", build);
		
		//inital check
		assertEquals(false, p1.canBuild(osh));
		assertEquals(false, p1.canBuild(aksu));
		
		//buy materials needed
		p1.buyItems("silk", cityA, 1);
		p1.buyItems("tea", cityA, 2);
		assertEquals(1, p1.itemQty("silk"));
		assertEquals(2, p1.itemQty("tea"));
		assertEquals(0, p1.itemQty("spices"));
		assertEquals(0, p1.itemQty("sugar"));
		assertEquals(0, p1.itemQty("salt"));
		assertEquals(0, p1.itemQty("porcelain"));
		
		//second check
		assertEquals(false, p1.canBuild(osh));
		assertEquals(true, p1.canBuild(aksu));
		assertEquals(5.0, p1.getWallet());
		
		//build it
		p1.createPost(aksu);
		assertEquals(0, p1.itemQty("silk"));
		assertEquals(0, p1.itemQty("tea"));
		assertEquals(0, p1.itemQty("spices"));
		assertEquals(0, p1.itemQty("sugar"));
		assertEquals(0, p1.itemQty("salt"));
		assertEquals(0, p1.itemQty("porcelain"));
		
		assertEquals(1.25, p1.getWallet());
		
		assertEquals(true, p1.ownsPost(aksu));
		
		
	}
	//Economic Giant bonus
	@Test
	void startingWalletBonus() {
		p1 = new Trader("Joe", money);
		assertEquals(15.0, p1.getWallet());
	}
	//Middlemen of the Silk Road bonus
	@Test
	void sellBonus() {
		p1 = new Trader("Joe", sell);
		
		//Buy in Aksu twice
		assertEquals(0, p1.itemQty("silk"));
		p1.buyItems("silk", cityA, 5);
		assertEquals(5, p1.itemQty("silk"));
		p1.buyItems("silk", cityA, 5);
		assertEquals(10, p1.itemQty("silk"));
		
		assertEquals(0.0, p1.getWallet());
		
		//Sell in Osh, profit
		p1.sellItems("silk", cityO, 10);
		assertEquals(0, p1.itemQty("silk"));
		
		assertEquals(10200.0, p1.getWallet());
	}
	@Test
	void constructPost() {
		//inital check
		
		assertEquals(false, allLoc.canConstructPost("Osh", p1));
		assertEquals(false, allLoc.canConstructPost("Aksu", p1));
		
		//buy materials needed
		p1.buyItems("silk", cityA, 1);
		p1.buyItems("tea", cityA, 2);
		assertEquals(1, p1.itemQty("silk"));
		assertEquals(2, p1.itemQty("tea"));
		assertEquals(0, p1.itemQty("spices"));
		assertEquals(0, p1.itemQty("sugar"));
		assertEquals(0, p1.itemQty("salt"));
		assertEquals(0, p1.itemQty("porcelain"));
		
		//second check
		assertEquals(false, allLoc.canConstructPost("Osh", p1));
		assertEquals(true, allLoc.canConstructPost("Aksu", p1));
		assertEquals(5.0, p1.getWallet());
		
		//build it
		allLoc.constructPost("myPost", "Aksu", p1);
		allLoc.constructPost("horizon", "Aksu", p2);
		
		assertEquals(0, p1.itemQty("silk"));
		assertEquals(0, p1.itemQty("tea"));
		assertEquals(0, p1.itemQty("spices"));
		assertEquals(0, p1.itemQty("sugar"));
		assertEquals(0, p1.itemQty("salt"));
		assertEquals(0, p1.itemQty("porcelain"));
		
		assertEquals(0, p1.getWallet());
		Post curPost = allLoc.getPost("myPost");
		assertEquals("myPost",curPost.getName());
		assertEquals(true, p1.ownsPost(curPost));
		
		
		assertEquals(2, allLoc.getPostsSize());
	}
	
}
