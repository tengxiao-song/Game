import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Deck{
    private List<Card> deck;

    public Deck(){
        deck = new ArrayList<>();
        String[] suits = {"H","D","C","S"};
        String[] values = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        for (String value : values){
            for (String suit : suits){
                Card card = new Card(value, suit);
                deck.add(card);
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public String draw(){
        Card removed = deck.remove(0);
        return removed.getCard();
    }
}

