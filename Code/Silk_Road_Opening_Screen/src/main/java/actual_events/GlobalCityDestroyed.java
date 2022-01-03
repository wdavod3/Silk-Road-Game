package actual_events;

import abstract_events.EventChoice;
import abstract_events.EventType;
import abstract_events.GlobalEvent;
import trade.LocationsData;

public class GlobalCityDestroyed extends GlobalEvent{

	public GlobalCityDestroyed(LocationsData cities) {
		super(EventType.CityDestroyed, "A city has been attacked and destroyed.", cities);
		
		//Add Choices
		addChoice(EventChoice.NO_CHOICES_AVAILABLE);
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		String temp = removeCity();
		setResult("You can no longer trade at " + temp + ".");
	}
}
