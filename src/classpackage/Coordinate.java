package classpackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public class Coordinate {
    private int x, y;
    private static final int PLAYER_ONE_MINION_TYPE_ONE_ROW = 2;
    private static final int PLAYER_ONE_MINION_TYPE_TWO_ROW = 3;
    private static final int PLAYER_TWO_MINION_TYPE_ONE_ROW = 1;
    private static final int PLAYER_TWO_MINION_TYPE_TWO_ROW = 0;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * @param x
     * @param y
     */
    public Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param coord
     */
    public Coordinate(final Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
    }

    /**
     * @param coordinatesInput
     */
    public Coordinate(final Coordinates coordinatesInput) {
        this.x = coordinatesInput.getX();
        this.y = coordinatesInput.getY();
    }

    /**
     * @param x
     * @param y
     */
    public void setCoordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * @return
     */
    public int getY() {
        return y;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    /**
     * @param minion
     * @return
     */
    private boolean isTypeOne(final Minion minion) {
        String name = minion.getName();
        return name.equals("Goliath") || name.equals("Miraj") || name.equals("Warden")
                || name.equals("The Ripper");
    }

    /**
     * @param handIdx
     * @param table
     */
    public void minionPlacement(final int handIdx, final GameTable table) {
        Minion minion = getMinionFromHand(handIdx, table);
        Coordinate placement = getPlacementCoordinate(minion, table);
        this.x = placement.getX();
        this.y = placement.getY();
    }

    /**
     * @param handIdx
     * @param table
     * @return
     */
    private Minion getMinionFromHand(final int handIdx, final GameTable table) {
        if (table.getCurrentPlayerTurn() == 1) {
            return table.getPlayerOne().getHand().getCards().get(handIdx);
        } else {
            return table.getPlayerTwo().getHand().getCards().get(handIdx);
        }
    }

    /**
     * @param minion
     * @param table
     * @return
     */
    private Coordinate getPlacementCoordinate(final Minion minion, final GameTable table) {
        int row;
        if (table.getCurrentPlayerTurn() == 1) {
            if (isTypeOne(minion)) {
                row = PLAYER_ONE_MINION_TYPE_ONE_ROW;
            } else {
                row = PLAYER_ONE_MINION_TYPE_TWO_ROW;
            }
        } else {
            if (isTypeOne(minion)) {
                row = PLAYER_TWO_MINION_TYPE_ONE_ROW;
            } else {
                row = PLAYER_TWO_MINION_TYPE_TWO_ROW;
            }
        }
        return findEmptySlotInRow(table, row);
    }

    /**
     * @param table
     * @param row
     * @return
     */
    private Coordinate findEmptySlotInRow(final GameTable table, final int row) {
        for (int j = 0; j < table.getTable().get(row).size(); j++) {
            if (table.getTable().get(row).get(j) == null) {
                return new Coordinate(row, j);
            }
        }
        return new Coordinate(-1, -1);
    }

    /**
     * @return
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode coordNode = mapper.createObjectNode();

        coordNode.put("x", this.x);
        coordNode.put("y", this.y);

        return coordNode;
    }

}
