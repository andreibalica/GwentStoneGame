package Class;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class Deck{
    private ArrayList<Minion> cards;

    public Deck() {
        this.cards = new ArrayList<Minion>();
    }

    public ArrayList<Minion> getCards() {return cards;}

    public void setCards(ArrayList<Minion> cards) {this.cards = cards;}

    public int getNrCardsInDeck() {
        return this.cards.size();
    }

    public void addCard(final Minion card) {
        this.cards.add(card);
    }
    public void removeCard() {
        this.cards.remove(0);
    }
    public void removeCard(int idx) {
        this.cards.remove(idx);
    }

    public ArrayNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode deckJson = mapper.createArrayNode();

        for (int i=0; i<this.cards.size(); i++) {
            Card card = this.cards.get(i);
            deckJson.add(card.toJson());
        }

        return deckJson;
    }

}