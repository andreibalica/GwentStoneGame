package classpackage;

import fileio.ActionsInput;
import fileio.Coordinates;

public final class Action {
    private final String command;
    private final int handIdx;
    private Coordinate cardAttacker;
    private Coordinate cardAttacked;
    private final int affectedRow;
    private final int playerIdx;
    private final int x;
    private final int y;

    /**
     * Initializes an action with the provided input data.
     *
     * @param actionsInput the input data for the action
     */
    public Action(final ActionsInput actionsInput) {
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

    /**
     * Gets the command associated with the action.
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the index of the card in hand.
     *
     * @return the hand index
     */
    public int getHandIdx() {
        return handIdx;
    }

    /**
     * Gets the coordinate of the attacking card.
     *
     * @return the attacking card's coordinate
     */
    public Coordinate getCardAttacker() {
        return cardAttacker;
    }

    /**
     * Sets the coordinate of the attacking card.
     *
     * @param cardAttacker the coordinate of the attacking card
     */
    public void setCardAttacker(final Coordinate cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    /**
     * Gets the coordinate of the attacked card.
     *
     * @return the attacked card's coordinate
     */
    public Coordinate getCardAttacked() {
        return cardAttacked;
    }

    /**
     * Sets the coordinate of the attacked card.
     *
     * @param cardAttacked the coordinate of the attacked card
     */
    public void setCardAttacked(final Coordinate cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    /**
     * Gets the affected row by the action.
     *
     * @return the affected row index
     */
    public int getAffectedRow() {
        return affectedRow;
    }

    /**
     * Gets the index of the player executing the action.
     *
     * @return the player index
     */
    public int getPlayerIdx() {
        return playerIdx;
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
}
