package Class;

import fileio.ActionsInput;
import fileio.Coordinates;

public class Action {
    private String command;
    private int handIdx;
    private Coordinate cardAttacker;
    private Coordinate cardAttacked;
    private int affectedRow;
    private int playerIdx;
    private int x;
    private int y;

    public Action(ActionsInput actionsInput) {
        this.command = actionsInput.getCommand();
        this.handIdx = actionsInput.getHandIdx();

        Coordinates cardAttackerInput = actionsInput.getCardAttacker();
        if (cardAttackerInput != null) {
            this.cardAttacker = new Coordinate(cardAttackerInput);
        }

        Coordinates cardAttackedInput = actionsInput.getCardAttacked();
        if (cardAttackedInput != null) {
            this.cardAttacked = new Coordinate(cardAttackedInput);
        }

        this.affectedRow = actionsInput.getAffectedRow();
        this.playerIdx = actionsInput.getPlayerIdx();
        this.x = actionsInput.getX();
        this.y = actionsInput.getY();
    }
    public String getCommand() {
        return command;
    }
    public int getPlayerIdx() {
        return playerIdx;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getHandIdx() {
        return handIdx;
    }
    public Coordinate getCardAttacker() {
        return this.cardAttacker;
    }
    public Coordinate getCardAttacked() {
        return this.cardAttacked;
    }
}