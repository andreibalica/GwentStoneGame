package classpackage;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class Minion extends Card {

    public Minion() {
        super();
    }

    /**
     * @param card
     */
    public Minion(final CardInput card) {
        super(card);
    }

    /**
     * @param minion
     */
    public Minion(final Minion minion) {
        super();
        copyMinionProperties(minion);
    }

    /**
     * @param minion
     */
    private void copyMinionProperties(final Minion minion) {
        this.setName(minion.getName());
        this.setMana(minion.getMana());
        this.setDescription(minion.getDescription());
        this.setColors(minion.getColors());
        this.setAttackDamage(minion.getAttackDamage());
        this.setHealth(minion.getHealth());
    }

    /**
     * @return
     */
    @Override
    public ObjectNode toJson() {
        ObjectNode minionNode = super.toJson();
        minionNode.put("attackDamage", getAttackDamage());
        minionNode.put("health", getHealth());

        return minionNode;
    }
}
