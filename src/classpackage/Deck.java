package classpackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class Deck {
    private final ArrayList<Minion> cards;

    public Deck() {
        this.cards = new ArrayList<Minion>();
    }

    public Deck(Deck deck) {
        this.cards = new ArrayList<Minion>(deck.cards);
    }

    public ArrayList<Minion> getCards() {
        return cards;
    }

    public int getNrCardsInDeck() {
        return this.cards.size();
    }

    /**
     *
     * @param card
     */
    public void addCard(final Minion card) {
        this.cards.add(card);
    }

    /**
     *
     */
    public void removeCard() {
        this.cards.remove(0);
    }

    /**
     *
     * @param idx
     */
    public void removeCard(final int idx) {
        this.cards.remove(idx);
    }

    /**
     *
     * @return
     */
    public ArrayNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode deckJson = mapper.createArrayNode();

        for (int i = 0; i < this.cards.size(); i++) {
            Card card = this.cards.get(i);
            deckJson.add(card.toJson());
        }

        return deckJson;
    }

}
