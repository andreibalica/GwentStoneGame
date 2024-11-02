package Class;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

import java.util.Objects;

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

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinate(Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
    }
    public Coordinate(Coordinates coordinatesInput) {
        this.x = coordinatesInput.getX();
        this.y = coordinatesInput.getY();
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean isTypeOne(Minion minion) {
        String name = minion.getName();
        return name.equals("Goliath") || name.equals("Miraj") || name.equals("Warden") || name.equals("The Ripper");
    }

    private boolean isTypeTwo(Minion minion) {
        String name = minion.getName();
        return name.equals("The Cursed One") || name.equals("Berserker") || name.equals("Sentinel") || name.equals("Discipline");
    }

    public void MinionPlacement(int handIdx, GameTable table) {
        Minion minion = getMinionFromHand(handIdx, table);
        Coordinate placement = getPlacementCoordinate(minion, table);
        this.x = placement.getX();
        this.y = placement.getY();
    }

    private Minion getMinionFromHand(int handIdx, GameTable table) {
        if (table.getCurrentPlayerTurn() == 1) {
            return table.getPlayerOne().getHand().getCards().get(handIdx);
        } else {
            return table.getPlayerTwo().getHand().getCards().get(handIdx);
        }
    }

    private Coordinate getPlacementCoordinate(Minion minion, GameTable table) {
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

    private Coordinate findEmptySlotInRow(GameTable table, int row) {
        for (int j = 0; j < table.getTable().get(row).size(); j++) {
            if (table.getTable().get(row).get(j) == null) {
                return new Coordinate(row, j);
            }
        }
        return new Coordinate(-1, -1);
    }
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode coordNode = mapper.createObjectNode();

        coordNode.put("x", this.x);
        coordNode.put("y",this.y);

        return coordNode;
    }

}
