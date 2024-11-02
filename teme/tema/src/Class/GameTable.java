package Class;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.Collections;
import Class.Minion;

public class GameTable {
    private ArrayList<ArrayList<Minion>> table;
    private Player playerOne;
    private Player playerTwo;
    private int currentPlayerTurn;
    private int round;
    private int turn;
    private final int numRows = 4;
    private final int numCols = 5;
    public GameTable(Player playerOne, Player playerTwo, int currentPlayerTurn){
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
        this.round=1;
        this.turn=0;
    }
    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }
    public Player getPlayerOne() {
        return playerOne;
    }
    public Player getPlayerTwo() {
        return playerTwo;
    }
    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }
    public int getRound() {
        return round;
    }
    public int getTurn() {
        return this.turn;
    }
    public int getNumRows() {
        return this.numRows;
    }
    public int getNumCols() {
        return this.numCols;
    }
    public void resetTurn(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if(this.table.get(i).get(j)!=null)
                    this.table.get(i).get(j).setCanAttack(1);
            }
        }
    }
    public void resetFrozen(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if(this.table.get(i).get(j)!=null)
                    this.table.get(i).get(j).setFrozen(0);
            }
        }
    }
    public void StartRound() {
        this.round++;
        this.turn=0;
        this.playerOne.addMana(this.round);
        this.playerTwo.addMana(this.round);
        this.playerOne.addHand();
        this.playerTwo.addHand();
        resetTurn();
        resetFrozen();
    }
    public void endTurn() {
        this.turn++;
        if(this.currentPlayerTurn==1)
            this.currentPlayerTurn = 2;
        else
            this.currentPlayerTurn = 1;
        if (this.turn == 2) {
            StartRound();
        }
    }

    public int getManaPlayer(int playerIdx){
        if(playerIdx==1)
            return this.playerOne.getMana();
        return this.playerTwo.getMana();
    }
    public Deck getHandPlayer(int playerIdx){
        if(playerIdx==1)
            return this.playerOne.getHand();
        return this.playerTwo.getHand();
    }
    public Hero getHeroPlayer(int playerIdx){
        if(playerIdx==1)
            return this.playerOne.getHero();
        return this.playerTwo.getHero();
    }
    public Deck getPlayerDeck(int playerIdx){
        if(playerIdx==1)
            return this.playerOne.getDeck();
        return this.playerTwo.getDeck();
    }

    public boolean VerificationMana(int handIdx){
        if(this.currentPlayerTurn==1 && this.playerOne.getHand().getCards().get(handIdx).getMana()<=this.playerOne.getMana())
            return true;
        if(this.currentPlayerTurn==2 && this.playerTwo.getHand().getCards().get(handIdx).getMana()<=this.playerTwo.getMana())
            return true;
        return false;
    }
    public boolean VerificationHand(int handIdx){
        if(this.currentPlayerTurn==1)
            return 0<=handIdx && handIdx<this.playerOne.getHand().getCards().size();
        return 0<=handIdx && handIdx<this.playerTwo.getHand().getCards().size();
    }
    public boolean VerificationMinion(Coordinate coord){
        if(this.table.get(coord.getX()).get(coord.getY())!=null)
            return true;
        return false;
    }
    public boolean VerificationTable(int handIdx) {
        Coordinate coordinate;
        if (this.currentPlayerTurn == 1) {
            coordinate = this.playerOne.getHand().getCards().get(handIdx).getCoordinate();
        } else {
            coordinate = this.playerTwo.getHand().getCards().get(handIdx).getCoordinate();
        }

        coordinate.MinionPlacement(handIdx, this);

        if (coordinate.getX() < 0 || coordinate.getX() >= this.numRows ||
                coordinate.getY() < 0 || coordinate.getY() >= this.numCols) {
            return false;
        }

        return true;
    }

    public void placeCard(int handIdx){
        Coordinate coordinate;
        Minion minion;
        if(this.currentPlayerTurn == 1) {
            minion = this.playerOne.getHand().getCards().get(handIdx);
            coordinate = minion.getCoordinate();
            coordinate.MinionPlacement(handIdx, this);

            this.table.get(coordinate.getX()).set(coordinate.getY(), minion);
            this.playerOne.removeMana(minion.getMana());
            this.playerOne.removeHand(handIdx);
        } else {
            minion = this.playerTwo.getHand().getCards().get(handIdx);
            coordinate = minion.getCoordinate();
            coordinate.MinionPlacement(handIdx, this);

            this.table.get(coordinate.getX()).set(coordinate.getY(), minion);
            this.playerTwo.removeMana(minion.getMana());
            this.playerTwo.removeHand(handIdx);
        }
    }

    public boolean AttackFriend(Coordinate coordatk, Coordinate coorddef){
        int rowAtk = coordatk.getX();
        int rowDef = coorddef.getX();
        if ((rowAtk < 2 && rowDef < 2) || (rowAtk >= 2 && rowDef >= 2))
            return true;
        return false;
    }
    public boolean AttackAgain(Coordinate coordatk){
        Minion minion=this.table.get(coordatk.getX()).get(coordatk.getY());
        return minion.getCanAttack() == 0;
    }
    private boolean isTank(Coordinate coorddef) {
        Minion minion = this.table.get(coorddef.getX()).get(coorddef.getY());
        String name = minion.getName();
        return name.equals("Goliath") || name.equals("Warden");
    }

    public boolean AttackMinionWithTankActive(Coordinate coordatk,Coordinate coorddef){
        if(!isTank(coorddef)){
            int isATank=0;
            for(int i=0;i<numRows;i++){
                for(int j=0;j<numCols;j++){
                    Coordinate coordinate=new Coordinate(i,j);
                    if(this.table.get(coordinate.getX()).get(coordinate.getY())!=null)
                    if(!AttackFriend(coordatk,coordinate) && isTank(coordinate)){
                        isATank++;
                    }
                }
            }
            if(isATank!=0)
                return true;
            return false;
        }
        return false;
    }
    public ArrayNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode tableJson = mapper.createArrayNode();

        for (int i = 0; i < this.table.size(); i++) {
            ArrayNode rowJson = mapper.createArrayNode();
            for (Card card : this.table.get(i)) {
                if (card != null) {
                    rowJson.add(card.toJson());
                }
            }
            tableJson.add(rowJson);
        }

        return tableJson;
    }
    public boolean isFrozen(Coordinate coordatk){
        if(this.table.get(coordatk.getX()).get(coordatk.getY())!=null) {
            if (this.table.get(coordatk.getX()).get(coordatk.getY()).getFrozen() == 1)
                return true;
        }
        return false;
    }
    public void removeTableMinion(Minion miniondeath){
        Coordinate coord=miniondeath.getCoordinate();
        for(int j=coord.getY();j<numCols-1;j++){
            this.table.get(coord.getX()).set(j, this.table.get(coord.getX()).get(j + 1));
        }
    }
    public void Damage(Minion minionatk,Minion miniondef){
        miniondef.decreaseHealth(minionatk.getAttackDamage());
        if(miniondef.getHealth()<1)
            removeTableMinion(miniondef);
    }
    public void AttackNow(Coordinate coordatk,Coordinate coorddef){
        Minion minionatk=this.table.get(coordatk.getX()).get(coordatk.getY());
        Minion miniondef=this.table.get(coorddef.getX()).get(coorddef.getY());
        minionatk.setCanAttack(0);
        Damage(minionatk,miniondef);
    }
    public void AbilityDisciple(Minion miniondef){
        miniondef.increaseHealth(2);
    }
    public void AbilityTheRipper(Minion miniondef){
        miniondef.decreaseAttackDamage(2);
    }
    public void AbilityMiraj(Minion minionatk,Minion miniondef){
        int auxhealth=minionatk.getHealth();
        minionatk.setHealth(miniondef.getHealth());
        miniondef.setHealth(auxhealth);
    }
    public void AbilityTheCursedOne(Minion miniondef){
        int auxhealthatk=miniondef.getHealth();
        miniondef.setHealth(miniondef.getAttackDamage());
        miniondef.setAttackDamage(auxhealthatk);
        if(miniondef.getHealth()<1)
            removeTableMinion(miniondef);
    }
    public void AbilityNow(Coordinate coordatk,Coordinate coorddef){
        Minion minionatk=this.table.get(coordatk.getX()).get(coordatk.getY());
        Minion miniondef=this.table.get(coorddef.getX()).get(coorddef.getY());
        minionatk.setCanAttack(0);
        switch(minionatk.getName()){
            case "Disciple":
                AbilityDisciple(miniondef);
                break;
            case "The Ripper":
                AbilityTheRipper(miniondef);
                break;
            case "Miraj":
                AbilityMiraj(minionatk,miniondef);
                break;
            case "The Cursed One":
                AbilityTheCursedOne(miniondef);
                break;
            default:
                break;
        }
    }
}
