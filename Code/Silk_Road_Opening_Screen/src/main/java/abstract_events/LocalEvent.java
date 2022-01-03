package abstract_events;

import trade.Trader;

public abstract class LocalEvent extends GameEvent {
	Trader player;

	public LocalEvent(EventType type, String message, Trader p1) {
		super(type, message);
		player = p1;
	}
	
	//use up points to get to wanted value
	public void setActionPoints(int val) {
		
		int cur = player.getCurrentActionPoints();
		
		player.takeAction(cur - val);
	}
	
	//Get free gold
	public void receiveGold(double val) {
		player.adjustMoney(val);
	}
	
	public Trader getTrader() {
		return player;
	}
}
