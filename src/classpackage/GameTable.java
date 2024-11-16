package classpackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class GameTable {
    private final ArrayList<ArrayList<Minion>> table;
    private final Player playerOne;
    private final Player playerTwo;
    private int currentPlayerTurn;
    private int round;
    private int turn;
    private int herodeath;
    private int affectedrow;
    private final int numRows = 4;
    private final int numCols = 5;
    private final int maxRound = 10;

    /**
     * @param playerOne
     * @param playerTwo
     * @param currentPlayerTurn
     */
    public GameTable(final Player playerOne, final Player playerTwo, final int currentPlayerTurn) {
        this.table = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            ArrayList<Minion> row = new ArrayList<>();
            for (int j = 0; j < numCols; j++) {
                row.add(null);
            }
            this.table.add(row);
        }
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayerTurn = currentPlayerTurn;
        this.round = 1;
        this.turn = 0;
        this.herodeath = 0;
        this.affectedrow = -1;
    }

    /**
     * @return
     */
    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    /**
     * @return
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     * @return
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * @return
     */
    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    /**
     * @param currentPlayerTurn
     */
    public void setCurrentPlayerTurn(final int currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    /**
     * @return
     */
    public int getRound() {
        return round;
    }

    /**
     * @param round
     */
    public void setRound(final int round) {
        this.round = round;
    }

    /**
     * @return
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @param turn
     */
    public void setTurn(final int turn) {
        this.turn = turn;
    }

    /**
     * @return
     */
    public int getHerodeath() {
        return herodeath;
    }

    /**
     * @param herodeath
     */
    public void setHerodeath(final int herodeath) {
        this.herodeath = herodeath;
    }

    /**
     * @return
     */
    public int getAffectedrow() {
        return affectedrow;
    }

    /**
     * @param affectedrow
     */
    public void setAffectedrow(final int affectedrow) {
        this.affectedrow = affectedrow;
    }

    /**
     *
     */
    public void resetTurn() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (this.table.get(i).get(j) != null) {
                    this.table.get(i).get(j).setCanAttack(1);
                }
            }
        }
    }

    /**
     *
     */
    public void resetFrozenplayerOne() {
        for (int i = 2; i < numRows; i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (this.table.get(i).get(j) != null) {
                    this.table.get(i).get(j).setFrozen(0);
                }
            }
        }
    }

    /**
     *
     */
    public void resetFrozenplayerTwo() {
        for (int i = 0; i < numRows - 2; i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (this.table.get(i).get(j) != null) {
                    this.table.get(i).get(j).setFrozen(0);
                }
            }
        }
    }

    /**
     *
     */
    public void startRound() {
        this.round++;
        this.turn = 0;
        if (this.round > maxRound) {
            this.playerOne.addMana(maxRound);
            this.playerTwo.addMana(maxRound);
        } else {
            this.playerOne.addMana(this.round);
            this.playerTwo.addMana(this.round);
        }
        this.playerOne.addHand();
        this.playerTwo.addHand();
        this.playerOne.getHero().setCanAttack(1);
        this.playerTwo.getHero().setCanAttack(1);
        resetTurn();
    }

    /**
     *
     */
    public void endTurn() {
        this.turn++;
        if (this.currentPlayerTurn == 1) {
            this.currentPlayerTurn = 2;
            resetFrozenplayerOne();
        } else {
            this.currentPlayerTurn = 1;
            resetFrozenplayerTwo();
        }
        if (this.turn == 2) {
            startRound();
        }
    }

    /**
     * @param playerIdx
     * @return
     */
    public int getManaPlayer(final int playerIdx) {
        if (playerIdx == 1) {
            return this.playerOne.getMana();
        }
        return this.playerTwo.getMana();
    }

    /**
     * @param playerIdx
     * @return
     */
    public Deck getHandPlayer(final int playerIdx) {
        if (playerIdx == 1) {
            return this.playerOne.getHand();
        }
        return this.playerTwo.getHand();
    }

    /**
     * @param playerIdx
     * @return
     */
    public Hero getHeroPlayer(final int playerIdx) {
        if (playerIdx == 1) {
            return this.playerOne.getHero();
        }
        return this.playerTwo.getHero();
    }

    /**
     * @param playerIdx
     * @return
     */
    public Deck getPlayerDeck(final int playerIdx) {
        if (playerIdx == 1) {
            return this.playerOne.getDeck();
        }
        return this.playerTwo.getDeck();
    }

    /**
     * @param handIdx
     * @return
     */
    public boolean verificationMana(final int handIdx) {
        if (this.currentPlayerTurn == 1) {
            return this.playerOne.getHand().getCards().get(handIdx).getMana()
                    <= this.playerOne.getMana();
        }
        return this.playerTwo.getHand().getCards().get(handIdx).getMana()
                <= this.playerTwo.getMana();
    }

    /**
     * @param handIdx
     * @return
     */
    public boolean verificationHand(final int handIdx) {
        if (this.currentPlayerTurn == 1) {
            return 0 <= handIdx && handIdx < this.playerOne.getHand().getCards().size();
        }
        return 0 <= handIdx && handIdx < this.playerTwo.getHand().getCards().size();
    }

    /**
     * @param coord
     * @return
     */
    public boolean verificationMinion(final Coordinate coord) {
        return this.table.get(coord.getX()).get(coord.getY()) != null;
    }

    /**
     * @param handIdx
     * @return
     */
    public boolean verificationTable(final int handIdx) {
        Coordinate coordinate;
        if (this.currentPlayerTurn == 1) {
            coordinate = this.playerOne.getHand().getCards().get(handIdx).getCoordinate();
        } else {
            coordinate = this.playerTwo.getHand().getCards().get(handIdx).getCoordinate();
        }

        coordinate.minionPlacement(handIdx, this);

        return coordinate.getX() >= 0 && coordinate.getX() < this.numRows && coordinate.getY() >= 0 && coordinate.getY() < this.numCols;
    }

    /**
     * @param handIdx
     */
    public void placeCard(final int handIdx) {
        if (this.currentPlayerTurn == 1) {
            Minion minion = new Minion(this.playerOne.getHand().getCards().get(handIdx));
            minion.getCoordinate().minionPlacement(handIdx, this);

            this.table.get(minion.getCoordinate().getX()).set(minion.getCoordinate().getY(),
                    minion);
            this.playerOne.removeMana(minion.getMana());
            this.playerOne.removeHand(handIdx);
        } else {
            Minion minion = new Minion(this.playerTwo.getHand().getCards().get(handIdx));
            minion.getCoordinate().minionPlacement(handIdx, this);

            this.table.get(minion.getCoordinate().getX()).set(minion.getCoordinate().getY(),
                    minion);
            this.playerTwo.removeMana(minion.getMana());
            this.playerTwo.removeHand(handIdx);
        }
    }

    /**
     * @param coordatk
     * @param coorddef
     * @return
     */
    public boolean attackFriend(final Coordinate coordatk, final Coordinate coorddef) {
        int rowAtk = coordatk.getX();
        int rowDef = coorddef.getX();
        return (rowAtk < 2 && rowDef < 2) || (rowAtk >= 2 && rowDef >= 2);
    }

    /**
     * @return
     */
    public boolean attackFriendRow() {
        int rowfreind1, rowfreind2;
        if (currentPlayerTurn == 2) {
            rowfreind1 = 0;
            rowfreind2 = 1;
        } else {
            rowfreind1 = 3;
            rowfreind2 = 2;
        }
        return this.affectedrow == rowfreind1 || this.affectedrow == rowfreind2;
    }

    /**
     * @param coordatk
     * @return
     */
    public boolean attackAgain(final Coordinate coordatk) {
        Minion minion = this.table.get(coordatk.getX()).get(coordatk.getY());
        return minion.getCanAttack() == 0;
    }

    /**
     * @param coorddef
     * @return
     */
    private boolean isTank(final Coordinate coorddef) {
        Minion minion = this.table.get(coorddef.getX()).get(coorddef.getY());
        String name = minion.getName();
        return name.equals("Goliath") || name.equals("Warden");
    }

    /**
     * @param coordatk
     * @param coorddef
     * @return
     */
    public boolean attackMinionWithTankActive(final Coordinate coordatk,
                                              final Coordinate coorddef) {
        if (!isTank(coorddef)) {
            int isATank = 0;
            for (int i = 1; i < numRows - 1; i++) {
                for (int j = 0; j < numCols; j++) {
                    Coordinate coordinate = new Coordinate(i, j);
                    if (this.table.get(coordinate.getX()).get(coordinate.getY()) != null) {
                        if (!attackFriend(coordatk, coordinate) && isTank(coordinate)) {
                            isATank++;
                        }
                    }
                }
            }
            return isATank != 0;
        }
        return false;
    }

    /**
     * @return
     */
    public ArrayNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode tableJson = mapper.createArrayNode();

        for (ArrayList<Minion> minions : this.table) {
            ArrayNode rowJson = mapper.createArrayNode();
            for (Card card : minions) {
                if (card != null) {
                    rowJson.add(card.toJson());
                }
            }
            tableJson.add(rowJson);
        }

        return tableJson;
    }

    /**
     * @param coordatk
     * @return
     */
    public boolean isFrozen(final Coordinate coordatk) {
        if (this.table.get(coordatk.getX()).get(coordatk.getY()) != null) {
            return this.table.get(coordatk.getX()).get(coordatk.getY()).getFrozen() == 1;
        }
        return false;
    }

    /**
     * @param miniondeath
     */
    public void removeTableMinion(final Minion miniondeath) {
        this.table.get(miniondeath.getCoordinate().getX()).remove(miniondeath.getCoordinate().getY());
        this.table.get(miniondeath.getCoordinate().getX()).add(null);
    }

    /**
     * @param minionatk
     * @param miniondef
     */
    public void damageMinion(final Minion minionatk, final Minion miniondef) {
        miniondef.decreaseHealth(minionatk.getAttackDamage());
        if (miniondef.getHealth() < 1) {
            removeTableMinion(miniondef);
        }
    }

    /**
     * @param coordatk
     * @param coorddef
     */
    public void attackNowMinion(final Coordinate coordatk, final Coordinate coorddef) {
        Minion minionatk = this.table.get(coordatk.getX()).get(coordatk.getY());
        Minion miniondef = this.table.get(coorddef.getX()).get(coorddef.getY());
        minionatk.setCanAttack(0);
        damageMinion(minionatk, miniondef);
    }

    /**
     * @param miniondef
     */
    public void abilityDisciple(final Minion miniondef) {
        miniondef.increaseHealth(2);
    }

    /**
     * @param miniondef
     */
    public void abilityTheRipper(final Minion miniondef) {
        miniondef.decreaseAttackDamage(2);
    }

    /**
     * @param minionatk
     * @param miniondef
     */
    public void abilityMiraj(final Minion minionatk, final Minion miniondef) {
        int auxhealth = minionatk.getHealth();
        minionatk.setHealth(miniondef.getHealth());
        miniondef.setHealth(auxhealth);
    }

    /**
     * @param miniondef
     */
    public void abilityTheCursedOne(final Minion miniondef) {
        int auxhealthatk = miniondef.getHealth();
        miniondef.setHealth(miniondef.getAttackDamage());
        miniondef.setAttackDamage(auxhealthatk);
        if (miniondef.getHealth() < 1) {
            removeTableMinion(miniondef);
        }
    }

    /**
     * @param coordatk
     * @param coorddef
     */
    public void abilityNowMinion(final Coordinate coordatk, final Coordinate coorddef) {
        Minion minionatk = this.table.get(coordatk.getX()).get(coordatk.getY());
        Minion miniondef = this.table.get(coorddef.getX()).get(coorddef.getY());
        minionatk.setCanAttack(0);
        switch (minionatk.getName()) {
            case "Disciple":
                abilityDisciple(miniondef);
                break;
            case "The Ripper":
                abilityTheRipper(miniondef);
                break;
            case "Miraj":
                abilityMiraj(minionatk, miniondef);
                break;
            case "The Cursed One":
                abilityTheCursedOne(miniondef);
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    public void abilityLordRoyce() {
        for (int j = 0; j < numCols; j++) {
            if (table.get(affectedrow).get(j) != null) {
                table.get(affectedrow).get(j).setFrozen(1);
            }
        }
    }

    /**
     *
     */
    public void abilityEmpressThorina() {
        int maxvalue = -1;
        int maxidx = 0;
        for (int j = 0; j < numCols; j++) {
            if (table.get(affectedrow).get(j) != null) {
                if (table.get(affectedrow).get(j).getHealth() >= maxvalue) {
                    maxvalue = table.get(affectedrow).get(j).getHealth();
                    maxidx = j;
                }
            }
        }
        removeTableMinion(table.get(affectedrow).get(maxidx));
    }

    /**
     *
     */
    public void abilityKingMudface() {
        for (int j = 0; j < numCols; j++) {
            if (table.get(affectedrow).get(j) != null) {
                table.get(affectedrow).get(j).increaseHealth(1);
            }
        }
    }

    /**
     *
     */
    public void abilityGeneralKocioraw() {
        for (int j = 0; j < numCols; j++) {
            if (table.get(affectedrow).get(j) != null) {
                table.get(affectedrow).get(j).increaseAttackDamage(1);
            }
        }
    }

    /**
     * @param hero
     */
    public void abilityNowHero(final Hero hero) {
        hero.setCanAttack(0);
        switch (hero.getName()) {
            case "Lord Royce":
                abilityLordRoyce();
                break;
            case "Empress Thorina":
                abilityEmpressThorina();
                break;
            case "King Mudface":
                abilityKingMudface();
                break;
            case "General Kocioraw":
                abilityGeneralKocioraw();
                break;
            default:
                break;
        }
    }

    /**
     * @param coordatk
     * @return
     */
    public boolean attackHeroWithTankActive(final Coordinate coordatk) {
        int isATank = 0;
        for (int i = 1; i < numRows - 1; i++) {
            for (int j = 0; j < numCols; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if (this.table.get(coordinate.getX()).get(coordinate.getY()) != null) {
                    if (!attackFriend(coordatk, coordinate) && isTank(coordinate)) {
                        isATank++;
                    }
                }
            }
        }
        return isATank != 0;
    }

    /**
     * @return
     */
    public int getHeroDeath() {
        return this.herodeath;
    }

    /**
     * @param minionatk
     * @param hero
     */
    public void damageHero(final Minion minionatk, final Hero hero) {
        hero.decreaseHealth(minionatk.getAttackDamage());
        if (hero.getHealth() < 1) {
            this.herodeath = 1;
        }
    }

    /**
     * @param coordatk
     */
    public void attackNowHero(final Coordinate coordatk) {
        Hero hero;
        if (this.currentPlayerTurn == 1) {
            hero = this.playerTwo.getHero();
        } else {
            hero = this.playerOne.getHero();
        }
        Minion minionatk = this.table.get(coordatk.getX()).get(coordatk.getY());
        minionatk.setCanAttack(0);
        damageHero(minionatk, hero);
    }

    /**
     *
     */
    public void decreaseManaAfterAbilityHero() {
        if (this.currentPlayerTurn == 1) {
            this.playerOne.removeMana(this.playerOne.getHero().getMana());
        } else {
            this.playerTwo.removeMana(this.playerTwo.getHero().getMana());
        }
    }

    /**
     * @return
     */
    public ArrayNode freezetoJson() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode tableJson = mapper.createArrayNode();

        for (ArrayList<Minion> minions : this.table) {
            for (Card card : minions) {
                if (card != null && card.getFrozen() == 1) {
                    tableJson.add(card.toJson());
                }
            }
        }

        return tableJson;
    }
}
