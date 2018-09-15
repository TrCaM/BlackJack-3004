package a1.blackjack.game;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Hand;
import a1.blackjack.commands.AutoCommandEngine;
import a1.blackjack.commands.Command;
import a1.blackjack.commands.ConsoleCommandEngine;
import a1.blackjack.players.Player;
import a1.blackjack.players.PlayerMode;
import a1.blackjack.views.Console;

import java.util.Queue;

/**
 * {@link Game} class is responsible for or the game logic.
 */
public class Game {
  private Player player;
  private Player dealer;
  private Deck deck;
  private Player activePlayer;

  private Console console;

  private Game(Console console) {
    this.console = console;
  }

  Player getPlayer() {
    return player;
  }

  Player getDealer() {
    return dealer;
  }

  public Player getActivePlayer() {
    return activePlayer;
  }

  public static Game initConsoleInputGame(Console console) {
    Game game = new Game(console);
    // Initialize player
    game.player = new Player(
        new ConsoleCommandEngine(game.console), game.console, "player");
    game.dealer = new Player(
        new AutoCommandEngine(), game.console, "dealer");
    // Create full shuffled deck
    game.deck = Deck.getFullDeck();
    game.deck.shuffle();
    return game;
  }

  public static Game initFileInputGame(Console console, Queue<Command> commandQueue, Deck deck) {
    Game game = new Game(console);
    // Initialize player with commandQueue read from file
    game.player = new Player(new ConsoleCommandEngine(
        game.console, commandQueue), game.console, "player");
    game.dealer = new Player(
        new AutoCommandEngine(), game.console, "dealer");
    // The deck is read from the file
    game.deck = deck;
    return game;
  }

  void beforePlayerTurn() {
    // Reveal player hand.
    player.getPlayingHand().getCards().forEach(Card::faceUp);
    // Reveal only 1 card of dealer's hand
    dealer.getPlayingHand().getCards().get(0).faceUp();
    showPlayersHands();
  }

  void beforeDealerTurn() {
    dealer.getPlayingHand().getCards().forEach(Card::faceUp);
    showPlayersHands();
  }

  void playTurn(Player player) {
    console.notify(String.format("It's %s's turn", player.getName()));
    activePlayer = player;
    while (player.getMode() != PlayerMode.STANDING) {
      try {
        // This happens in split mode
        if (player.getPlayingHand().hasLessThan2Cards()) {
          player.draw(deck, true);
        }
        if (player.getPlayingHand().isBlackjack()) {
          console.notify(String.format("%s gets a blackjack hand!!", player.getName()));
          player.stand();
        } else {
          executeCommand(player, player.getCommandEngine().getNextCommand(this));
        }
        showPlayerHands(player);
      } catch (IllegalArgumentException e) {
        console.notify(e.getMessage());
        console.notify("Please try again");
      }
    }
  }

  private void executeCommand(Player player, Command command) {
    switch (command) {
      case HIT:
        player.hit(deck);
        break;
      case SPLIT:
        player.split();
        break;
      default:
        player.stand();
    }
  }

  private void endGame() {
    player.getPlayingHand().getCards().forEach(Card::faceUp);
    dealer.getPlayingHand().getCards().forEach(Card::faceUp);
    showPlayersHands();
    showPlayersScore();
    console.notify(String.format("%s has won!!!!", getWinner()));
  }

  private void showPlayersHands() {
    console.notify("---------------------------------------");
    showPlayerHands(player);
    showPlayerHands(dealer);
    console.notify("---------------------------------------");
  }

  private void showPlayerHands(Player player) {
    console.notify(String.format("%s's hand contains: ", player.getName()));
    console.notify("\t- Main hand contains:");
    displayHand(player.getMainHand());
    if (player.isSplitting()) {
      console.notify("\t- Second hand contains:");
      displayHand(player.getSplitHand());
    }
  }

  private void displayHand(Hand hand) {
    StringBuilder sb = new StringBuilder();
    hand.getCards().forEach(card -> sb.append("\t\t+ ").append(card).append("\n"));
    console.notify(sb.toString());
  }

  private void showPlayersScore() {
    showPlayerScore(player);
    showPlayerScore(dealer);
  }

  private void showPlayerScore(Player player) {
    StringBuilder sb = new StringBuilder();
    if (player.isBusted()) {
      sb.append(player.getName()).append(" is busted.");
    } else {
      sb.append(player.getName()).append("'s score is ").append(player.getScore());
    }
    console.notify(sb.toString());
  }

  Player getWinner() {
    return player.getScore() > dealer.getScore() ? player : dealer;
  }

}
