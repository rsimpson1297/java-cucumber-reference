package com.levelup.forestsandmonsters.cli;

import java.util.ArrayList;
import java.util.List;

import com.levelup.forestsandmonsters.GameController;
import com.levelup.forestsandmonsters.GameController.GameStatus;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class LevelUpGame implements Quit.Command {
  
  private final GameController gameController;
  private List<GameStatus> gameHistory;
  private boolean isGameStarted = false;
  
  public LevelUpGame() {
      super();
      this.gameController = new GameController();
      this.gameHistory = new ArrayList<GameStatus>();
  }

	@ShellMethod(value="Create a character (characterName)",key={"create-character","create"})
    public void createCharacter(@ShellOption(defaultValue="Player") String characterName)
	{
      gameController.createPlayer(characterName);
      GameStatus status = gameController.getStatus();
      
      System.out.println("Your character, " + status.playerName + " is created!");
	}

  @ShellMethod("Start the game")
    public void startGame()
  {
    isGameStarted = true;
    System.out.println("Welcome to Forests and Monsters! You have entered a mysterious place.");
    System.out.println("Would you like to go North(N), South(S), East(E), West(W) or Exit(X)?");
  }

  public Availability startedCheck() {
    return isGameStarted
    ? Availability.available()
    : Availability.unavailable("game not started");
 }

  @ShellMethod(value="Move North",key={"N","n"},group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveNorth()
  {
    gameController.move(GameController.DIRECTION.NORTH);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value="Move South",key={"S","s"},group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveSouth()
  {
    gameController.move(GameController.DIRECTION.SOUTH);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value="Move East",key={"E","e"},group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveEast()
  {
    gameController.move(GameController.DIRECTION.EAST);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value="Move West",key={"W","w"},group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveWest()
  {
    gameController.move(GameController.DIRECTION.WEST);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value="End the game",key={"X","x","end","q","quit","exit"})
  public void quit() 
  {
    printSummary();
    System.exit(0);
  }

  private void printSummary() {
    System.out.println("Exiting the mysterious land!");
    for (GameStatus status : gameHistory) {
      //TODO: Override toString on game status to print pretty
      System.out.println(status);

      //TODO: Print anything else you committed to in your mockup
    }
  }

  private void updateStatus(GameStatus status) {
    this.gameHistory.add(status);
  }


}
