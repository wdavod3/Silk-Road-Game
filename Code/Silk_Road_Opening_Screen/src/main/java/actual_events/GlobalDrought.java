package actual_events;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.GlobalEvent;
import trade.LocationsData;

public class GlobalDrought extends GlobalEvent{

	public GlobalDrought(LocationsData cities) {
		super(EventType.Drought, "A drought has started.", cities);
		
		//Add Choices
		addChoice(EventChoice.NO_CHOICES_AVAILABLE);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		adjustPrices(0.25);
		setResult("The price of goods has increased by 25%.");
	}

	

}
