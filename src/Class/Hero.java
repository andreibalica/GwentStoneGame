package Class;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class Hero extends Card{

    public Hero() {
        super();
        setHealth(30);
        setAttackDamage(0);
    }
    public Hero(CardInput Cardinput) {
        super(Cardinput);
        setHealth(30);
        setAttackDamage(0);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode heroNode = super.toJson();
        heroNode.put("health", getHealth());
        return heroNode;
    }
}