package classpackage;


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

    /**
     * @param startGameInput
     */
    public StartGame(final StartGameInput startGameInput) {
        this.playerOneDeckIdx = startGameInput.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = startGameInput.getPlayerTwoDeckIdx();
        this.shuffleSeed = startGameInput.getShuffleSeed();
        this.startingPlayer = startGameInput.getStartingPlayer();
        this.playerOneHero = new Hero(startGameInput.getPlayerOneHero());
        this.playerTwoHero = new Hero(startGameInput.getPlayerTwoHero());
        this.startingPlayer = startGameInput.getStartingPlayer();
    }

    /**
     * @param startGame
     * @param playerOne
     * @param playerTwo
     */
    public void setUpGame(final StartGame startGame, final Player playerOne,
                          final Player playerTwo) {
        int playerOneDeckIdx = startGame.getPlayerOneDeckIdx();
        int playerTwoDeckIdx = startGame.getPlayerTwoDeckIdx();
        playerOne.setHero(startGame.getPlayerOneHero());
        playerTwo.setHero(startGame.getPlayerTwoHero());
        Deck deckplayerOne = new Deck(playerOne.getDecks().get((playerOneDeckIdx)));
        Deck deckplayerTwo = new Deck(playerTwo.getDecks().get(playerTwoDeckIdx));
        Collections.shuffle(deckplayerOne.getCards(), new Random(startGame.getShuffleSeed()));
        Collections.shuffle(deckplayerTwo.getCards(), new Random(startGame.getShuffleSeed()));
        playerOne.setDeck(deckplayerOne);
        playerTwo.setDeck(deckplayerTwo);
        while (playerOne.getHand().getNrCardsInDeck() > 0) {
            playerOne.removeHand(0);
        }
        while (playerTwo.getHand().getNrCardsInDeck() > 0) {
            playerTwo.removeHand(0);
        }
        playerOne.addHand();
        playerTwo.addHand();
        playerOne.setMana(0);
        playerTwo.setMana(0);
        playerOne.addMana(1);
        playerTwo.addMana(1);
    }

    /**
     * @return
     */
    public int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    /**
     * @return
     */
    public int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    /**
     * @return
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * @return
     */
    public Hero getPlayerOneHero() {
        return playerOneHero;
    }

    /**
     * @return
     */
    public Hero getPlayerTwoHero() {
        return playerTwoHero;
    }

    /**
     * @return
     */
    public int getStartingPlayer() {
        return startingPlayer;
    }

}

