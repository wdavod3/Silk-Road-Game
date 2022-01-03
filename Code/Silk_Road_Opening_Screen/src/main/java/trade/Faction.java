package trade;

public class Faction {
    public String name;
    public City capitalCity;
    public Bonus bonusType;

    public Faction(String name, City capitalCity, Bonus bonusType){
        this.name = name;
        this.capitalCity = capitalCity;
        this.bonusType = bonusType;
    }

    public Bonus getBonusType() {
        return bonusType;
    }

    public String getName() {
        return name;
    }

    public City getCapitalCity() {
        return capitalCity;
    }
}

