package Class;


import fileio.StartGameInput;

import java.util.Collections;
import java.util.Random;

public class StartGame {
    private int playerOneDeckIdx;
    private int playerTwoDeckIdx;
    private int shuffleSeed;
    private Hero playerOneHero;
    private Hero playerTwoHero;
    private int startingPlayer;
    public StartGame(StartGameInput startGameInput) {
        this.playerOneDeckIdx = startGameInput.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = startGameInput.getPlayerTwoDeckIdx();
        this.shuffleSeed = startGameInput.getShuffleSeed();
        this.startingPlayer = startGameInput.getStartingPlayer();
        this.playerOneHero = new Hero(startGameInput.getPlayerOneHero());
        this.playerTwoHero = new Hero(startGameInput.getPlayerTwoHero());
        this.startingPlayer = startGameInput.getStartingPlayer();
    }

    public void SetUpGame(StartGame startGame, Player playerOne, Player playerTwo) {
        int playerOneDeckIdx = startGame.getPlayerOneDeckIdx();
        int playerTwoDeckIdx = startGame.getPlayerTwoDeckIdx();
        playerOne.setDeck(playerOne, playerOneDeckIdx);
        playerTwo.setDeck(playerTwo, playerTwoDeckIdx);
        playerOne.setHero(startGame.getPlayerOneHero());
        playerTwo.setHero(startGame.getPlayerTwoHero());
        Collections.shuffle(playerOne.getDeck().getCards(), new Random(startGame.getShuffleSeed()));
        Collections.shuffle(playerTwo.getDeck().getCards(), new Random(startGame.getShuffleSeed()));
        playerOne.addHand();
        playerTwo.addHand();
        playerOne.addMana(1);
        playerTwo.addMana(1);
    }
    public int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }
    public int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }
    public int getShuffleSeed() {
        return shuffleSeed;
    }
    public Hero getPlayerOneHero() {
        return playerOneHero;
    }
    public Hero getPlayerTwoHero() {
        return playerTwoHero;
    }
    public int getStartingPlayer() {
        return startingPlayer;
    }
}

