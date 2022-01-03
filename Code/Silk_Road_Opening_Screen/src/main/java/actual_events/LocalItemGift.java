package actual_events;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.LocalEvent;
import trade.Trader;

public class LocalItemGift extends LocalEvent{

	public LocalItemGift(Trader p1) {
		super(EventType.ItemGift, "Another traveler gave you a gift.", p1);
		
		//Add Choices
		addChoice(EventChoice.NO_CHOICES_AVAILABLE);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		receiveGold(10.0);
		setResult("Your recieved 10 pieces of gold.");
	}

}
