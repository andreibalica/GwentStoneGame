package classpackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.StartGameInput;

import java.util.ArrayList;

public class Game {
    private final StartGame startGame;
    private final ArrayList<Action> actions;

    /**
     * @param gameInput
     */
    public Game(final GameInput gameInput) {
        StartGameInput startGameInput = gameInput.getStartGame();
        this.startGame = new StartGame(startGameInput);
        this.actions = new ArrayList<>();
        for (int i = 0; i < gameInput.getActions().size(); i++) {
            ActionsInput actionsInput = gameInput.getActions().get(i);
            Action action = new Action(actionsInput);
            this.actions.add(action);
        }
    }

    /**
     * @return
     */
    public StartGame getStartGame() {
        return startGame;
    }

    /**
     * @return
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * @param playerOne
     * @param playerTwo
     * @param currentGame
     * @param output
     */
    public void runGame(final Player playerOne, final Player playerTwo, final int currentGame,
                        final ArrayNode output) {
        startGame.setUpGame(startGame, playerOne, playerTwo);
        GameTable table = new GameTable(playerOne, playerTwo, startGame.getStartingPlayer());
        for (Action action : actions) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode actionResult = mapper.createObjectNode();
            int playerIdx;
            int handIdx;
            Coordinate coordatk;
            Coordinate coorddef;
            Minion minionatk;
            Minion miniondef;
            Hero hero;
            switch (action.getCommand()) {
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
                    handIdx = action.getHandIdx();
                    if (table.verificationHand(handIdx)) {
                        if (!table.verificationMana(handIdx)) {
                            actionResult.put("command", "placeCard");
                            actionResult.put("handIdx", handIdx);
                            actionResult.put("error", "Not enough mana to place card on table.");
                            output.add(actionResult);
                        } else if (!table.verificationTable(handIdx)) {
                            actionResult.put("command", "placeCard");
                            actionResult.put("handIdx", handIdx);
                            actionResult.put("error",
                                    "Cannot place card on table since row is " + "full.");
                            output.add(actionResult);
                        } else {
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
                    coordatk = action.getCardAttacker();
                    coorddef = action.getCardAttacked();
                    minionatk = table.getTable().get(coordatk.getX()).get(coordatk.getY());
                    miniondef = table.getTable().get(coorddef.getX()).get(coorddef.getY());
                    if (miniondef != null && minionatk != null) {
                        actionResult.put("command", "cardUsesAttack");
                        if (table.attackFriend(coordatk, coorddef)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card does not belong to the "
                                    + "enemy" + ".");
                            output.add(actionResult);
                        } else if (table.attackAgain(coordatk)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error",
                                    "Attacker card has already attacked this " + "turn.");
                            output.add(actionResult);
                        } else if (table.isFrozen(coordatk)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacker card is frozen.");
                            output.add(actionResult);
                        } else if (table.attackMinionWithTankActive(coordatk, coorddef)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card is not of type 'Tank'.");
                            output.add(actionResult);
                        } else {
                            table.attackNowMinion(coordatk, coorddef);
                        }
                    }
                    break;

                case "cardUsesAbility":
                    coordatk = action.getCardAttacker();
                    coorddef = action.getCardAttacked();
                    minionatk = table.getTable().get(coordatk.getX()).get(coordatk.getY());
                    miniondef = table.getTable().get(coorddef.getX()).get(coorddef.getY());
                    if (miniondef != null && minionatk != null) {
                        actionResult.put("command", "cardUsesAbility");
                        if (table.isFrozen(coordatk)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacker card is frozen.");
                            output.add(actionResult);
                        } else if (table.attackAgain(coordatk)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error",
                                    "Attacker card has already attacked this " + "turn.");
                            output.add(actionResult);
                        } else if (minionatk.getName().equals("Disciple")
                                && !table.attackFriend(coordatk, coorddef)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card does not belong to the "
                                    + "current player.");
                            output.add(actionResult);
                        } else if (!minionatk.getName().equals("Disciple")
                                && table.attackFriend(coordatk, coorddef)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card does not belong to the "
                                    + "enemy" + ".");
                            output.add(actionResult);
                        } else if (!minionatk.getName().equals("Disciple")
                                && table.attackMinionWithTankActive(coordatk, coorddef)) {
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.set("cardAttacked", coorddef.toJson());
                            actionResult.put("error", "Attacked card is not of type 'Tank'.");
                            output.add(actionResult);
                        } else {
                            table.abilityNowMinion(coordatk, coorddef);
                        }
                    }
                    break;

                case "getCardAtPosition":
                    actionResult.put("command", "getCardAtPosition");
                    actionResult.put("x", action.getX());
                    actionResult.put("y", action.getY());
                    Coordinate coord = new Coordinate(action.getX(), action.getY());
                    if (table.verificationMinion(coord)) {
                        actionResult.set("output",
                                table.getTable().get(coord.getX()).get(coord.getY()).toJson());
                        output.add(actionResult);
                    } else {
                        actionResult.put("output", "No card available at that position.");
                        output.add(actionResult);
                    }
                    break;

                case "useAttackHero":
                    coordatk = action.getCardAttacker();
                    minionatk = table.getTable().get(coordatk.getX()).get(coordatk.getY());
                    if (minionatk != null) {
                        if (table.isFrozen(coordatk)) {
                            actionResult.put("command", "useAttackHero");
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.put("error", "Attacker card is frozen.");
                            output.add(actionResult);
                        } else if (table.attackAgain(coordatk)) {
                            actionResult.put("command", "useAttackHero");
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.put("error",
                                    "Attacker card has already attacked this turn.");
                            output.add(actionResult);
                        } else if (table.attackHeroWithTankActive(coordatk)) {
                            actionResult.put("command", "useAttackHero");
                            actionResult.set("cardAttacker", coordatk.toJson());
                            actionResult.put("error", "Attacked card is not of type 'Tank'.");
                            output.add(actionResult);
                        } else {
                            table.attackNowHero(coordatk);
                            if (table.getHeroDeath() == 1) {
                                if (table.getCurrentPlayerTurn() == 1) {
                                    actionResult.put("gameEnded",
                                            "Player one killed the enemy " + "hero.");
                                    table.getPlayerOne().addWin(1);
                                    output.add(actionResult);
                                } else {
                                    actionResult.put("gameEnded",
                                            "Player two killed the enemy " + "hero.");
                                    table.getPlayerTwo().addWin(1);
                                    output.add(actionResult);
                                }
                            }
                        }
                    }
                    break;

                case "useHeroAbility":
                    table.setAffectedrow(action.getAffectedRow());
                    hero = table.getHeroPlayer(table.getCurrentPlayerTurn());
                    if (hero != null) {
                        if (hero.getMana() > table.getManaPlayer(table.getCurrentPlayerTurn())) {
                            actionResult.put("command", "useHeroAbility");
                            actionResult.put("affectedRow", table.getAffectedrow());
                            actionResult.put("error", "Not enough mana to use hero's ability.");
                            output.add(actionResult);
                        } else if (hero.getCanAttack() == 0) {
                            actionResult.put("command", "useHeroAbility");
                            actionResult.put("affectedRow", table.getAffectedrow());
                            actionResult.put("error", "Hero has already attacked this turn.");
                            output.add(actionResult);
                        } else if ((hero.getName().equals("Lord Royce") || hero.getName().equals(
                                "Empress Thorina")) && table.attackFriendRow()) {
                            actionResult.put("command", "useHeroAbility");
                            actionResult.put("affectedRow", table.getAffectedrow());
                            actionResult.put("error", "Selected row does not belong to the enemy.");
                            output.add(actionResult);
                        } else if ((hero.getName().equals("General Kocioraw")
                                || hero.getName().equals("King Mudface"))
                                && !table.attackFriendRow()) {
                            actionResult.put("command", "useHeroAbility");
                            actionResult.put("affectedRow", table.getAffectedrow());
                            actionResult.put("error", "Selected row does not belong to the "
                                    + "current player.");
                            output.add(actionResult);
                        } else {
                            table.abilityNowHero(hero);
                            table.decreaseManaAfterAbilityHero();
                        }
                    }
                    break;
                case "getFrozenCardsOnTable":
                    actionResult.put("command", "getFrozenCardsOnTable");
                    actionResult.set("output", table.freezetoJson());
                    output.add(actionResult);
                    break;
                case "getPlayerOneWins":
                    actionResult.put("command", "getPlayerOneWins");
                    actionResult.put("output", table.getPlayerOne().getWin());
                    output.add(actionResult);
                    break;
                case "getPlayerTwoWins":
                    actionResult.put("command", "getPlayerTwoWins");
                    actionResult.put("output", table.getPlayerTwo().getWin());
                    output.add(actionResult);
                    break;
                case "getTotalGamesPlayed":
                    actionResult.put("command", "getTotalGamesPlayed");
                    actionResult.put("output", currentGame);
                    output.add(actionResult);
                    break;
                default:
                    break;
            }
        }
    }
}
