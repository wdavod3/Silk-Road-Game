package actual_events;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.GlobalEvent;
import trade.LocationsData;

public class GlobalPlentifulHarvest extends GlobalEvent{

	public GlobalPlentifulHarvest(LocationsData cities) {
		super(EventType.Harvest, "There was a good harvest that increased the supply of goods.", cities);
		
		//Add Choices
		addChoice(EventChoice.NO_CHOICES_AVAILABLE);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		adjustPrices(-0.25);
		setResult("The price of goods has decreased by 25%.");
	}

	

}
