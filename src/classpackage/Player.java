package classpackage;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Player {
    private Deck deck;
    private ArrayList<Deck> decks;
    private Deck hand;
    private Hero hero;
    private int mana;
    private int win;

    public Player() {
        this.deck = new Deck();
        this.decks = new ArrayList<>();
        this.hero = new Hero();
        this.hand = new Deck();
        this.mana = 0;
        this.win = 0;
    }

    /**
     *
     */
    public void addHand() {
        if (this.deck.getNrCardsInDeck() > 0) {
            this.hand.addCard(this.deck.getCards().getFirst());
            this.deck.removeCard();
        }
    }

    /**
     * @param indx
     */
    public void removeHand(final int indx) {
        this.hand.removeCard(indx);
    }

    /**
     * @param mana
     */
    public void addMana(final int mana) {
        this.mana = this.mana + mana;
    }

    /**
     * @param mana
     */
    public void removeMana(final int mana) {
        this.mana = this.mana - mana;
    }

    /**
     * @param win
     */
    public void addWin(final int win) {
        this.win = this.win + win;
    }

    /**
     * @return
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * @param deck
     */
    public void setDeck(final Deck deck) {
        this.deck = deck;
    }

    /**
     * @return
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    /**
     * @param decks
     */
    public void setDecks(final ArrayList<Deck> decks) {
        this.decks = decks;
    }

    /**
     * @return
     */
    public Deck getHand() {
        return hand;
    }

    /**
     * @param hand
     */
    public void setHand(final Deck hand) {
        this.hand = hand;
    }

    /**
     * @return
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * @param hero
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     * @return
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * @return
     */
    public int getWin() {
        return win;
    }

    /**
     * @param win
     */
    public void setWin(final int win) {
        this.win = win;
    }

    /**
     * @param decks
     */
    public void setDecks(final DecksInput decks) {
        for (int i = 0; i < decks.getNrDecks(); i++) {
            Deck deck = new Deck();
            for (int j = 0; j < decks.getNrCardsInDeck(); j++) {
                CardInput cardInput = decks.getDecks().get(i).get(j);
                Minion card = new Minion(cardInput);
                deck.addCard(card);
            }
            this.decks.add(deck);
        }
    }

    /**
     * @param player
     * @param k
     */
    public void setDeck(final Player player, final int k) {
        this.deck = player.decks.get(k);
    }
}
