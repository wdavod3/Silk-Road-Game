package actual_events;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.LocalEvent;
import combat.Mercenary;
import trade.Trader;

public class LocalBanditAttack extends LocalEvent{
	Mercenary bandit;
	
	public LocalBanditAttack(Trader p1) {
		super(EventType.BanditAttack, "You see a band of bandits ahead. What would you like to do?", p1);
		
		bandit = new Mercenary(1, 1);
		
		//Add Choices
		addChoice(EventChoice.Fight);
		
		addChoice(EventChoice.Run);
	}

	@Override
	public void changeData() {
		
		switch(getCurrentChoice()) {
		case Fight:
			switch(bandit.attackTrader(getTrader())){
			case Escape:
				setResult("Your luck let you escape.");
				break;
			case Loss:
				setResult("You were overpowered and lost 25% of your gold.");
				break;
			case Tie:
				setResult("You and the bandit were evenly matched so he ran away.");
				break;
			case Victory:
				setResult("You beat the bandit and made it out safely.");
				break;
			default:
				setResult("Error, no combat result.");
				break;
				
			}
			break;
		case Run:
			setResult("You ran away successfully!");
			//Run successful
			break;
		default:
			break;
		}
	}
}
