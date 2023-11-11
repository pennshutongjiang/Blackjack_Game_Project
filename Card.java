public class Card {
    private String card;
    private final String[] newDeck;

    public Card() {
        Deck d = new Deck();
        this.newDeck = d.getDeck();
    }

    public String deal(int i) {
        this.card = newDeck[i];
        return card;
    }

    public String getCard() {
        return card;
    }

    public int getValue() {
        String[] cards = {"AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH",
                "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC",
                "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS",
                "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD"};
        //Create an array of values; (set Ace as 1 by default; consider "11" later)
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        int i = 0;
        while (!card.equals(cards[i])) {
            i++;
        }
        return values[i % 13];
    }
}