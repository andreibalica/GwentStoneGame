package classpackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int attackDamage;
    private Coordinate coordinate;
    private int canAttack;
    private int frozen;

    public Card() {
        this.mana = 0;
        this.description = "";
        this.colors = new ArrayList<>();
        this.name = "";
        this.health = 0;
        this.attackDamage = 0;
        this.coordinate = new Coordinate(-1, -1);
        canAttack = 1;
        frozen = 0;
    }

    /**
     * @param card
     */
    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.coordinate = new Coordinate();
        this.coordinate.setCoordinate(-1, -1);
        canAttack = 1;
        frozen = 0;
    }

    /**
     * @param amount
     */
    public void decreaseHealth(final int amount) {
        this.health -= amount;
    }

    /**
     * @param amount
     */
    public void increaseHealth(final int amount) {
        this.health += amount;
    }

    /**
     * @param atk
     */
    public void increaseAttackDamage(final int atk) {
        this.attackDamage += atk;
    }

    /**
     * @param atk
     */
    public void decreaseAttackDamage(final int atk) {
        this.attackDamage -= atk;
        if (this.attackDamage < 0) {
            this.attackDamage = 0;
        }
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * @param health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * @return
     */
    public int getAttackDamage() {
        return this.attackDamage;
    }

    /**
     * @param damage
     */
    public void setAttackDamage(final int damage) {
        this.attackDamage = damage;
    }

    /**
     * @return
     */
    public int getMana() {
        return this.mana;
    }

    /**
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * @return
     */
    public ArrayList<String> getColors() {
        return this.colors;
    }

    /**
     * @param colors
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * @param coordinate
     */
    public void setCoordinate(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * @return
     */
    public int getCanAttack() {
        return this.canAttack;
    }

    /**
     * @param canAttack
     */
    public void setCanAttack(final int canAttack) {
        this.canAttack = canAttack;
    }

    /**
     * @return
     */
    public int getFrozen() {
        return this.frozen;
    }

    /**
     * @param frozen
     */
    public void setFrozen(final int frozen) {
        this.frozen = frozen;
    }

    /**
     * @return
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode cardNode = mapper.createObjectNode();

        cardNode.put("mana", this.mana);
        cardNode.put("name", this.name);
        cardNode.put("description", this.description);

        ArrayNode colorsArray = mapper.createArrayNode();
        if (this.colors != null) {  // Null-check to avoid NullPointerException
            for (String color : this.colors) {
                colorsArray.add(color);
            }
        }
        cardNode.set("colors", colorsArray);

        return cardNode;
    }
}
