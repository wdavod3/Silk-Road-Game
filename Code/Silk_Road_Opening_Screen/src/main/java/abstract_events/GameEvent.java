package abstract_events;

import java.util.ArrayList;

public abstract class GameEvent {
	private String eventMessage;
	private String eventResult;
	private EventType eventType;
	
	private ArrayList<EventChoice> eventChoices;
	private EventChoice curChoice;
	
	public GameEvent(EventType type, String message) {
		eventMessage = message;
		eventType = type;
		eventChoices = new ArrayList<EventChoice>();
	}
	
	//Example of in game
	public void eventTemplateMethod() {
		//check for input
		if(hasChoices()) {
			//get info
			
			
			
		}
		else {
			//execute dat change
			changeData();
		}
	}
	
	
	public abstract void changeData();
	
	public String getMessage() {
		return eventMessage;
	}
	
	public EventType getType() {
		return eventType;
	}
	
	public Boolean hasChoices() {
		if(eventChoices.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<EventChoice> getChoices() {
		return eventChoices;
	}
	
	public void addChoice(EventChoice mess) {
		eventChoices.add(mess);
	}
	
	public EventChoice getCurrentChoice() {
		return curChoice;
	}
	public void setCurrentChoice(EventChoice choice) {
		curChoice = choice;
		changeData();
	}
	
	
	public void setResult(String result) {
		eventResult = result;
	}
	
	public String getResult() {
		return eventResult;
	}
	
	
	
}
