package classpackage;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class Hero extends Card {
    private static final int HERO_HEALTH = 30;
    private static final int HERO_ATTACKDAMAGE = 0;

    public Hero() {
        super();
        setHealth(HERO_HEALTH);
        setAttackDamage(HERO_ATTACKDAMAGE);
    }

    /**
     * @param cardinput
     */
    public Hero(final CardInput cardinput) {
        super(cardinput);
        setHealth(HERO_HEALTH);
        setAttackDamage(HERO_ATTACKDAMAGE);
    }

    /**
     * @return
     */
    @Override
    public ObjectNode toJson() {
        ObjectNode heroNode = super.toJson();
        heroNode.put("health", getHealth());
        return heroNode;
    }
}
