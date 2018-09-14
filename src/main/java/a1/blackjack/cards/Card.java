package a1.blackjack.cards;

/**
 * Model class for the cards.
 */
public class Card{
  private final Suit suit;
  private final int value;
  private boolean isUp;

  /**
   * Create a card with given {@code suit} and {@code value}. {@code value} should be in the range
   * [1-13]:
   *    - If it is in the range [1-10], it represents number card from 1 to 10.
   *    - If it is 11, it's a Jack card.
   *    - If it is 12, it's a Queen card.
   *    - If it is 13, it's a King card.
   *    - Otherwise it throws an error.
   * @param suit the {@code Suit} of the card
   * @param value the {@code value} number of the card
   */
  public Card(Suit suit, int value) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Invalid card value");
    }
    this.suit = suit;
    this.value = value;
    this.isUp = false;
  }

  public void faceUp() {
    this.isUp = true;
  }

  public Suit getSuit() {
    return this.suit;
  }

  public int getValue() {
    return this.value;
  }

  public boolean isUp() {
    return this.isUp;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof  Card))
      return false;
    Card otherCard = (Card)other;
    return value == otherCard.value && suit == otherCard.suit;
  }

  @Override
  public String toString() {
    return toSuitString() + toValueString();
  }

  private String toSuitString() {
    switch (suit) {
      case SPADE:
        return "S";
      case HEART:
        return "H";
      case DIAMOND:
        return "D";
      case CLUB:
        return "C";
      default:
        throw new IllegalStateException("Invalid suit");
    }
  }

  private String toValueString() {
    if (value == 1) {
      return "A";
    }
    if (value == 11) {
      return "J";
    }
    if (value == 12) {
      return "Q";
    }
    if (value == 13) {
      return "K";
    }
    return Integer.toString(value);
  }
}
