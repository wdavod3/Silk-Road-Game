package actual_events;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.LocalEvent;
import trade.Trader;

public class LocalBrokenWagon extends LocalEvent{

	public LocalBrokenWagon(Trader p1) {
		super(EventType.BrokenWagon, "Your wagon broke down.", p1);
		
		//Add Choices
		addChoice(EventChoice.NO_CHOICES_AVAILABLE);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		setActionPoints(0);
		setResult("You can't move for 1 turn.");
	}

}
