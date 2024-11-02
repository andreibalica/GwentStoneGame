package Class;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;


public class Player{
    private Deck deck;
    private ArrayList<Deck> decks;
    private Deck hand;
    private Hero hero;
    private int mana;
    public Player(){
        this.deck = new Deck();
        this.decks = new ArrayList<>();
        this.hero = new Hero();
        this.hand = new Deck();
        this.mana = 0;
    }
    public Deck getHand(){return this.hand;}
    public void addHand(){
        if(this.deck.getNrCardsInDeck()>0){
            this.hand.addCard(this.deck.getCards().get(0));
            this.deck.removeCard();
        }
    }
    public void removeHand(int indx){this.hand.removeCard(indx);}
    public Hero getHero() {
        return hero;
    }
    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public int getMana() {
        return this.mana;
    }
    public void addMana(int mana) {
        this.mana = this.mana+mana;
    }
    public void removeMana(int mana) {
        this.mana = this.mana-mana;
    }

    public void setDecks(DecksInput decks) {
        for(int i = 0; i < decks.getNrDecks(); i++){
            Deck deck = new Deck();
            for(int j = 0; j < decks.getNrCardsInDeck(); j++){
                CardInput cardInput = decks.getDecks().get(i).get(j);
                Minion card = new Minion(cardInput);
                deck.addCard(card);
            }
            this.decks.add(deck);
        }
    }
    public ArrayList<Deck> getDecks() {return this.decks;}

    public void setDeck(Player player, int k) {
        this.deck=player.decks.get(k);
    }
    public Deck getDeck() {return this.deck;}
}
