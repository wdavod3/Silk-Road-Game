package combat;

import java.util.concurrent.ThreadLocalRandom;

import trade.Trader;

public class Mercenary extends Character{
	// RPG Stats
	private Double percentStolen;
	
	public Mercenary(Integer atk, Integer def){
		super(atk, def);
		percentStolen = 0.25;
	}
	
	//returns true if successful attack
	public CombatResult attackTrader(Trader player){
		
		//consider luck first
		Integer luck = player.getLuck();
		Integer random = ThreadLocalRandom.current().nextInt(1, (100) + 1);
		System.out.println("Random Number: " + random + " Your Luck: " + luck);
		if(luck >= random) {
			System.out.println("Escaped with Luck");
			return CombatResult.Escape;
		}
		
		//calculate damage with attack and damage
		Integer mercDamageDealt = getAttack() - player.getDefense();
		Integer playerDamageDealt = player.getAttack() - getDefense();
		
		//non negative attacks
		if(mercDamageDealt < 0) {
			mercDamageDealt = 0;;
		}
		if(playerDamageDealt < 0) {
			playerDamageDealt = 0;;
		}
		System.out.println("merc: " + mercDamageDealt + " trader: " + playerDamageDealt);
		//Player deals more or equal damage
		if(playerDamageDealt == mercDamageDealt) {
			return CombatResult.Tie;
		}
		else if(playerDamageDealt > mercDamageDealt) {
			return CombatResult.Victory;
		}
		
		player.loseMoney(percentStolen);
		return CombatResult.Loss;
	}
	
	public Double getPercentStolen() {
		return percentStolen;
	}
}
