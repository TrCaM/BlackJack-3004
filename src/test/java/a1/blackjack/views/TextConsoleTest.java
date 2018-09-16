package a1.blackjack.views;

import a1.blackjack.commands.Command;
import a1.blackjack.game.GameMode;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextConsoleTest {
  private TextConsole textConsole;

  @Test
  public void promptGameMode_shouldSucceed_console() {
    InputStream in = new ByteArrayInputStream("C".getBytes());
    textConsole = new TextConsole(in);

    assertThat(textConsole.promptGameMode(), is(GameMode.CONSOLE));
  }

  @Test
  public void promptGameMode_shouldSucceed_file() {
    InputStream in = new ByteArrayInputStream("a hello F".getBytes());
    textConsole = new TextConsole(in);

    assertThat(textConsole.promptGameMode(), is(GameMode.FILE));
  }

  @Test
  public void promptCommand_shouldSucceed() {
    InputStream in = new ByteArrayInputStream("D S H".getBytes());
    textConsole = new TextConsole(in);
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
    textConsole = new TextConsole(in);
    Set<Command> availableCommands = new HashSet<>();
    availableCommands.add(Command.STAND);
    availableCommands.add(Command.HIT);
    availableCommands.add(Command.SPLIT);

    textConsole.promptCommand(availableCommands);
  }
}
