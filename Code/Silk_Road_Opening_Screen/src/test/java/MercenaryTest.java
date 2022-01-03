import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import combat.CombatResult;
import combat.Mercenary;
import trade.Bonus;
import trade.Faction;
import trade.Trader;


class MercenaryTest {
	static Mercenary mEqual;
	static Mercenary mBetter;
	static Mercenary mWorse;
	static Trader p1;
	Faction none = new Faction("None", null, Bonus.None);

	@BeforeEach
	void setup() {
		mEqual = new Mercenary(1,1);
		mBetter = new Mercenary(2,1);
		mWorse = new Mercenary(0,0);
		p1 = new Trader("Chris", none);
	}
	
	//check if they have the same stats
	@Test
	void equalStats() {
		p1.setLuck(0);
		assertEquals(CombatResult.Tie, mEqual.attackTrader(p1));
		assertEquals(10.0 ,p1.getWallet());
	}
	
	//check if they have the betterstats
	@Test
	void betterStats() {
		p1.setLuck(0);
		assertEquals(CombatResult.Loss, mBetter.attackTrader(p1));
		assertEquals(10.0 * (1 - mBetter.getPercentStolen()) ,p1.getWallet());
	}
	
	//check if they have the worse stats
	@Test
	void worseStats() {
		p1.setLuck(0);
		assertEquals(CombatResult.Victory, mWorse.attackTrader(p1));
		assertEquals(10.0 ,p1.getWallet());
	}
	
	//check if 100 luck
	@Test
	void bestLuck() {
		p1.setLuck(100);
		assertEquals(CombatResult.Escape, mBetter.attackTrader(p1));
		assertEquals(10.0 ,p1.getWallet());
	}
	
	//check if 50 luck, can't check
	@Test
	void mediumLuck() {
		p1.setLuck(50);
		mBetter.attackTrader(p1);
	}
}
