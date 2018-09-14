package a1.blackjack.views;

import a1.blackjack.commands.Command;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextConsoleTest {
  private TextConsole textConsole;

  @Test
  public void promptCommand_shouldSucceed() {
    InputStream in = new ByteArrayInputStream("D S H".getBytes());
    textConsole = new TextConsole(new Scanner(in));
    Set<Command> availableCommands = new HashSet<>();
    availableCommands.add(Command.STAND);
    availableCommands.add(Command.HIT);
    availableCommands.add(Command.SPLIT);

    assertThat(textConsole.promptCommand(availableCommands), is(Command.SPLIT));
    assertThat(textConsole.promptCommand(availableCommands), is(Command.STAND));
    assertThat(textConsole.promptCommand(availableCommands), is(Command.HIT));
  }

  @Test(expected = IllegalArgumentException.class)
  public void promptCommand_invalidCommandThrow() {
    InputStream in = new ByteArrayInputStream("SK".getBytes());
    textConsole = new TextConsole(new Scanner(in));
    Set<Command> availableCommands = new HashSet<>();
    availableCommands.add(Command.STAND);
    availableCommands.add(Command.HIT);
    availableCommands.add(Command.SPLIT);

    textConsole.promptCommand(availableCommands);
  }
}
