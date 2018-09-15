package a1.blackjack.players;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Hand;
import a1.blackjack.commands.CommandEngine;
import a1.blackjack.views.Console;

/**
 * Represent a player who plays the game.
 */
public class Player {
  private Hand mainHand;
  private Hand splitHand;
  private CommandEngine commandEngine;
  private PlayerMode mode;
  private String name;
  private Console console;
  private boolean isSplitting;

  public Player(CommandEngine commandEngine, Console console, String name) {
    mainHand = new Hand();
    splitHand = new Hand();
    this.console = console;
    this.commandEngine = commandEngine;
    this.mode = PlayerMode.NORMAL;
    this.name =name;
    isSplitting = false;
  }

  public String getName() {
    return name;
  }

  public Hand getMainHand() {
    return mainHand;
  }

  public Hand getSplitHand() {
    return splitHand;
  }

  public Hand getPlayingHand() {
    return mode == PlayerMode.SPLITTING_HAND ? splitHand : mainHand;
  }

  public boolean isSplitting() {
    return isSplitting;
  }

  public PlayerMode getMode() {
    return mode;
  }

  public CommandEngine getCommandEngine() {
    return this.commandEngine;
  }

  public int getScore() {
    int mainHandScore = mainHand.getHandScore();
    int splitHandScore = splitHand.getHandScore();
    mainHandScore = mainHandScore > 21 ? 0 : mainHandScore;
    splitHandScore = splitHandScore > 21 ? 0 : splitHandScore;
    return Math.max(mainHandScore, splitHandScore);
  }

  public boolean isBusted() {
    if (isSplitting) {
      return mainHand.isBusted() && splitHand.isBusted();
    }
    return mainHand.isBusted();
  }

  /**
   * Draw a card to the proper hand from the deck.
   */
  public void draw(Deck deck, boolean shouldOpen) {
    Hand hand;
    switch (mode) {
      case SPLITTING_HAND:
        hand = splitHand;
        break;
      default:
        hand = mainHand;
    }
    Card card = deck.draw();
    if (shouldOpen) {
      card.faceUp();
    }
    hand.addCard(card);
    console.notify(String.format("%s has drawn a card: %s", name, card));
  }

  /**
   * Draw a card from the deck and calculate that next state by analyzing the hand
   * - Validate if the hit action is valid, otherwise throw
   * - Draw a card and add to the hand based on the current mode:
   * + If player is using split hand in split mode, then add the card to split hand
   * + Otherwise add to the normal hand
   * - If the hand is busted then do stand
   */
  public void hit(Deck deck) {
    console.notify(String.format("%s hits", name));
    validatePlayerStateForHit(deck);
    draw(deck, true);
    if (getPlayingHand().isBusted()) {
      console.notify(String.format("%s busts this hand", name));
      stand();
    }
  }

  private void validatePlayerStateForHit(Deck deck) {
    if (mode == PlayerMode.STANDING) {
      throw new IllegalStateException("Hit while standing");
    }
    if (deck.size() == 0) {
      throw new IllegalStateException("Hit while deck is empty");
    }
  }

  public void stand() {
    console.notify(String.format("%s stands", name));
    switch (mode) {
      case NORMAL:
      case SPLITTING_HAND:
        mode = PlayerMode.STANDING;
        break;
      default:
        mode = PlayerMode.SPLITTING_HAND;
        console.notify(String.format("%s uses the second hand", name));
    }
  }

  /**
   * Split the current hand into 2 hands given that the 2 cards of the original hand are identical
   * (this method does not check this condition). Each hand will contain 1 of the originals and draw
   * 1 more extra. If blackjack happens then the player mode should go STANDING.
   */
  public void split() {
    console.notify(String.format("%s splits", name));
    validatePlayerStateForSplit();
    splitHand.addCard(mainHand.removeCard(1));
    isSplitting = true;
    mode = PlayerMode.SPLITTING_MAIN;
  }

  private void validatePlayerStateForSplit() {
    if (mode == PlayerMode.STANDING) {
      throw new IllegalStateException("Split while standing");
    }
  }

  /* Package private setter methods used for testing */
  void setMode(PlayerMode mode) {
    this.mode = mode;
  }

  void setMainHand(Hand hand) {
    mainHand = hand;
  }

  void setSplitHand(Hand hand) {
    splitHand = hand;
  }

  void setSplitting(boolean isSplitting) {
    this.isSplitting = isSplitting;
  }
}
