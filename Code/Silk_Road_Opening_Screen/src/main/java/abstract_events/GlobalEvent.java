package abstract_events;
import trade.LocationsData;

public abstract class GlobalEvent extends GameEvent {
	LocationsData citiesInfo;

	public GlobalEvent( EventType type, String message, LocationsData cities) {
		super(type, message);
		citiesInfo = cities;
	}
	
	//adjust prices everywhere by percentage
	public void adjustPrices(double percent) {
		citiesInfo.adjustAllPrices(percent);
	}
	
	//remove city from locations
	public String removeCity() {
		return citiesInfo.deleteCity();
	}
}
