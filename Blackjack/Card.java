public class Card{
    private String suit;
    private String value;

    public Card(String valueNum, String suitType){
        suit = suitType;
        value = valueNum;
    }

    public String getCard(){
        return value + suit;
    }
}
