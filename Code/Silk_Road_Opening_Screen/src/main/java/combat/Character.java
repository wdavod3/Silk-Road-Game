package combat;

public class Character {
	private Integer attack;
	private Integer defense;
	
	public Character(Integer atk, Integer def){
		attack = atk;
		defense = def;
	}
	
	//get attack
	public Integer getAttack() {
		return attack;
	}
	
	//get defense
	public Integer getDefense(){
		return defense;
	}
	
	//increase attack
	public void increaseAttack(int atk) {
		attack += atk;
		if (attack > 100) {
			attack = 100;
		}	
	}
	
	//increase defense
	public void increaseDefense(int def) {
		defense += def;
		if (defense > 100) {
			defense = 100;
		}	
	}

}
