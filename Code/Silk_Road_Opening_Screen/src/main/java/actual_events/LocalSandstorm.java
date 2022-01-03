package actual_events;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.LocalEvent;
import trade.Trader;

public class LocalSandstorm extends LocalEvent{

	public LocalSandstorm(Trader p1) {
		super(EventType.Sandstorm, "You got Caught in a Sandstorm.", p1);
		
		//Add Choices
		addChoice(EventChoice.NO_CHOICES_AVAILABLE);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		setActionPoints(1);
		setResult("Actions Points reduced to 1 for this turn.");
	}

}
