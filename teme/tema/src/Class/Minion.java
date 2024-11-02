package Class;


import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class Minion extends Card {

    public Minion(){
        super();
    }

    public Minion(CardInput card) {
        super(card);
    }
    public Minion(Minion minion) {
        super();
        this.setName(minion.getName());
        this.setMana(minion.getMana());
        this.setDescription(minion.getDescription());
        this.setColors(minion.getColors());
        this.setAttackDamage(minion.getAttackDamage());
        this.setHealth(minion.getHealth());
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode minionNode = super.toJson();
        minionNode.put("attackDamage", getAttackDamage());
        minionNode.put("health", getHealth());

        return minionNode;
    }
}
