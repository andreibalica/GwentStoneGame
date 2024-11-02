package Class;

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
        setMana(0);
        setDescription("");
        setColors(new ArrayList<>());
        setName("");
        setHealth(0);
        setAttackDamage(0);
        this.coordinate = new Coordinate();
        setCoordinate(-1, -1);
        canAttack = 1;
        frozen = 0;
    }

    public Card(CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.coordinate = new Coordinate();
        this.coordinate.setCoordinates(-1, -1);
        canAttack = 1;
        frozen = 0;
    }

    public int getFrozen() {
        return this.frozen;
    }

    public int getCanAttack() {
        return this.canAttack;
    }
    public void setCanAttack(int canAttack){
        this.canAttack=canAttack;
    }
    public void setFrozen(int frozen){
        this.frozen=frozen;
    }

    public void setCoordinate(int x, int y) {
        this.coordinate.setCoordinates(x, y);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getMana() {
        return this.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return this.colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void decreaseHealth(int amount) {
        this.health -= amount;
    }
    public void increaseHealth(int amount) {
        this.health += amount;
    }
    public void increaseAttackDamage(int atk) {
        this.attackDamage += atk;
    }
    public void decreaseAttackDamage(int atk){
        this.attackDamage -= atk;
        if(this.attackDamage<0)
            this.attackDamage=0;
    }
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