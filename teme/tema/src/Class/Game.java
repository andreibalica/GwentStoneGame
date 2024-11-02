package Class;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.DecksInput;
import fileio.GameInput;
import fileio.StartGameInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private StartGame startGame;
    private ArrayList<Action> actions;
    public Game(GameInput gameInput) {
        StartGameInput startGameInput = gameInput.getStartGame();
        this.startGame = new StartGame(startGameInput);
        this.actions = new ArrayList<>();
        for(int i = 0; i < gameInput.getActions().size(); i++) {
            ActionsInput actionsInput = gameInput.getActions().get(i);
            Action action=new Action(actionsInput);
            this.actions.add(action);
        }
    }
    public StartGame getStartGame() {
        return startGame;
    }
    public ArrayList<Action> getActions() {
        return actions;
    }
    public void RunGame(Player playerOne, Player playerTwo, ArrayNode output) {
        this.startGame.SetUpGame(this.startGame, playerOne, playerTwo);
        GameTable table = new GameTable(playerOne, playerTwo, startGame.getStartingPlayer());
        for(int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode actionResult = mapper.createObjectNode();
            int playerIdx;
            int handIdx;
            Coordinate coordatk;
            Coordinate coorddef;
            Minion minionatk;
            Minion miniondef;
            switch (action.getCommand()){
                case "getPlayerDeck":
                    playerIdx = action.getPlayerIdx();
                    actionResult.put("command", "getPlayerDeck");
                    actionResult.put("playerIdx", playerIdx);
                    actionResult.set("output", table.getPlayerDeck(playerIdx).toJson());
                    output.add(actionResult);
                    break;

                case "getPlayerHero":
                    playerIdx = action.getPlayerIdx();
                    actionResult.put("command", "getPlayerHero");
                    actionResult.put("playerIdx", playerIdx);
                    actionResult.set("output", table.getHeroPlayer(playerIdx).toJson());
                    output.add(actionResult);
                    break;

                case "getPlayerTurn":
                    actionResult.put("command", "getPlayerTurn");
                    actionResult.put("output", table.getCurrentPlayerTurn());
                    output.add(actionResult);
                    break;

                case "getPlayerMana":
                    playerIdx = action.getPlayerIdx();
                    actionResult.put("command", "getPlayerMana");
                    actionResult.put("playerIdx", playerIdx);
                    actionResult.put("output", table.getManaPlayer(playerIdx));
                    output.add(actionResult);
                    break;

                case "getCardsInHand":
                    playerIdx = action.getPlayerIdx();
                    actionResult.put("command", "getCardsInHand");
                    actionResult.put("playerIdx", playerIdx);
                    actionResult.set("output", table.getHandPlayer(playerIdx).toJson());
                    output.add(actionResult);
                    break;

                case "endPlayerTurn":
                    table.endTurn();
                    break;

                case "placeCard":
                    handIdx=action.getHandIdx();
                    if(table.VerificationHand(handIdx)) {
                        if (!table.VerificationMana(handIdx)){
                            actionResult.put("command", "placeCard");
                            actionResult.put("handIdx", handIdx);
                            actionResult.put("error", "Not enough mana to place card on table.");
                            output.add(actionResult);}
                        else if (!table.VerificationTable(handIdx)){
                            actionResult.put("command", "placeCard");
                            actionResult.put("handIdx", handIdx);
                            actionResult.put("error", "Cannot place card on table since row is full.");
                            output.add(actionResult);}
                        else {
                            table.placeCard(handIdx);
                        }
                    }
                    break;

                case "getCardsOnTable":
                    actionResult.put("command", "getCardsOnTable");
                    actionResult.set("output", table.toJson());
                    output.add(actionResult);
                    break;

                case "cardUsesAttack":
                    coordatk=action.getCardAttacker();
                    coorddef=action.getCardAttacked();
                    minionatk=table.getTable().get(coordatk.getX()).get(coordatk.getY());
                    miniondef=table.getTable().get(coorddef.getX()).get(coorddef.getY());
                    if(miniondef!=null && minionatk!=null)
                    {
                        actionResult.put("command", "cardUsesAttack");
                    if(table.AttackFriend(coordatk,coorddef)){
                        actionResult.set("cardAttacker", coordatk.toJson());
                        actionResult.set("cardAttacked", coorddef.toJson());
                        actionResult.put("error", "Attacked card does not belong to the enemy.");
                        output.add(actionResult);
                    }else if(table.AttackAgain(coordatk)){
                        actionResult.set("cardAttacker", coordatk.toJson());
                        actionResult.set("cardAttacked", coorddef.toJson());
                        actionResult.put("error", "Attacker card has already attacked this turn.");
                        output.add(actionResult);
                    }else if(table.isFrozen(coordatk)){
                        actionResult.set("cardAttacker", coordatk.toJson());
                        actionResult.set("cardAttacked", coorddef.toJson());
                        actionResult.put("error", "Attacker card is frozen.");
                        output.add(actionResult);
                    }else if(table.AttackMinionWithTankActive(coordatk,coorddef)) {
                        actionResult.set("cardAttacker", coordatk.toJson());
                        actionResult.set("cardAttacked", coorddef.toJson());
                        actionResult.put("error", "Attacked card is not of type 'Tank'.");
                        output.add(actionResult);
                    }else{
                        table.AttackNow(coordatk,coorddef);
                    }
                    }
                    break;

                case "cardUsesAbility":
                    coordatk=action.getCardAttacker();
                    coorddef=action.getCardAttacked();
                    minionatk=table.getTable().get(coordatk.getX()).get(coordatk.getY());
                    miniondef=table.getTable().get(coorddef.getX()).get(coorddef.getY());
                    if(miniondef!=null && minionatk!=null){
                        actionResult.put("command", "cardUsesAbility");
                        if(table.isFrozen(coordatk)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacker card is frozen.");
                            output.add(actionResult);
                        }else if(table.AttackAgain(coordatk)){
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacker card has already attacked this turn.");
                            output.add(actionResult);
                        }else if(minionatk.getName().equals("Disciple") && !table.AttackFriend(coordatk,coorddef)){
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card does not belong to the current player.");
                            output.add(actionResult);
                        }else if(!minionatk.getName().equals("Disciple") && table.AttackFriend(coordatk,coorddef)){
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card does not belong to the enemy.");
                            output.add(actionResult);
                        }else if(!minionatk.getName().equals("Disciple") && table.AttackMinionWithTankActive(coordatk,coorddef)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card is not of type 'Tank'.");
                            output.add(actionResult);
                        }else{
                            table.AbilityNow(coordatk,coorddef);
                        }
                    }
                    break;

                case "getCardAtPosition":
                    actionResult.put("command", "getCardAtPosition");
                    actionResult.put("x", action.getX());
                    actionResult.put("y", action.getY());
                    Coordinate coord = new Coordinate(action.getX(), action.getY());
                    if(table.VerificationMinion(coord))
                    {
                        actionResult.set("output", table.getTable().get(coord.getX()).get(coord.getY()).toJson());
                        output.add(actionResult);
                    }
                    else {
                        actionResult.put("output", "No card available at that position.");
                        output.add(actionResult);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
