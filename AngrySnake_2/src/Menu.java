import java.util.InputMismatchException;
import java.util.Scanner;

/* this claas shows the menu at the beginning of the game.
 * the player can set here the enviroment such as amount of players, AI level, random stones
 */

public class Menu {
	// some variables that are needed on this object
	private int playerCount = 0;
	private int aiLevel01 = 0;
	private int aiLevel02 = 0;
	private int maxAiLevel = 2;
	private int maxStoneAmount = 5;
	private String name1 = "Comp1";
	private String name2 = "Comp2";
	private int stoneCount = 0;
	private Scanner scanner = new Scanner(System.in);
	
	// constructor
	public Menu() {
		this.callMenu();
	}
	
	// "Main-method" of this class, asks the players for everything needed
	private void callMenu() {
		// loop runs until the entered number is correct, setPlayerCount(int) also checks the validity
		do {
			System.out.print("How many player should participate? (0, 1 or 2 are allowed!): ");
		} while (!setPlayerCount(getScannerInt()));
		
		switch (playerCount) {
		case 0:
			// loop runs until the entered number is correct, setAiLevel01(int) also checks the validity
			do {
				System.out.print("Please enter ai level or computer 1 (Choose a level between 0 and " + maxAiLevel + "): ");
			} while (!setAiLevel01(getScannerInt()));
			
			// loop runs until the entered number is correct, setAiLevel02(int) also checks the validity
			do {
				System.out.print("Please enter ai level or computer 2 (Choose a level between 0 and " + maxAiLevel + "): ");
			} while (!setAiLevel02(getScannerInt()));
			break;
			
		case 1:
			// loop runs until the entered number is correct, setAiLevel01(int) also checks the validity
			do {
				System.out.print("Please enter ai level or computer 1 (Choose a level between 0 and " + maxAiLevel + "): ");
				aiLevel01 = scanner.nextInt();
			} while (!setAiLevel01(getScannerInt()));
			System.out.print("Enter name for player: ");
			setName1(scanner.next());
			break;
		case 2:
			System.out.print("Enter name for player 1: ");
			setName1(scanner.next());
			System.out.print("Enter name for player 2: ");
			setName2(scanner.next());
			break;
		}
		
		// asks for the amount of random objects in a loop while the amount is not allowed
		do {
			System.out.print("How many stones shall be created on the battle area?: ");
		} while (!setStoneCount(getScannerInt()));
		
	}
	
	// returns the entered amount of player
	public int getPlayerCount() {
		return playerCount;
	}
	
	// returns the entered level of ai 1
	public int getAiLevel01() {
		return aiLevel01;
	}

	// returns the entered level of ai 2
	public int getAiLevel02() {
		return aiLevel02;
	}
	
	// returns the name of player 1, if it's a computer it will be comp1 (see the initialisation of the variable name1)
	public String getName1() {
		return name1;
	}

	// returns the name of player 2, if it's a computer it will be comp2 (see the initialisation of the variable name2)
	public String getName2() {
		return name2;
	}
	
	// this method will set the ai level when the entered value is valid. When it was successful true will be returned, otherwise false
	private boolean setAiLevel01 (int level) {
		if (checkValid(level, 0, maxAiLevel)) {
			aiLevel01 = level;
			System.out.println("");
			return true;
		} else {
			System.out.println("Invalid level!");
			return false;
		}
	}

	// this method will set the ai level when the entered value is valid. When it was successful true will be returned, otherwise false
	private boolean setAiLevel02 (int level) {
		if (checkValid(level, 0, maxAiLevel)) {
			aiLevel02 = level;
			System.out.println("");
			return true;
		} else {
			System.out.println("Invalid level!");
			return false;
		}
	}
	
	// this method will set the amount of players when the entered value is valid. When it was successful true will be returned, otherwise false
	private boolean setPlayerCount(int count) {
		if (checkValid(count, 0, 2)) {
			playerCount = count;
			System.out.println("");
			return true;
		} else {
			System.out.println("Invaldi player amount!");
			return false;
		}
	}
	
	// rework the preinitialised name of player1
	private void setName1(String name) {
		name1 = name;
		System.out.println("");
	}

	// rework the preinitialised name of player2
	private void setName2(String name) {
		name2 = name;
		System.out.println("");
	}
	
	// this method checks the entered amount of stones. When it's valid this will be set and true will be returned, otherwise false is returned
	private boolean setStoneCount(int stones) {
		if (checkValid(stones, 0, maxStoneAmount)) {
			stoneCount = stones;
			System.out.println("");
			return true;
		} else {
			System.out.println("Invalid number of objects! ");
			return false;
		}
	}

	// this method helps by checking the valid range (includes range1 and range2)
	private boolean checkValid(int toCheck, int range1, int range2) {
		if (toCheck >= range1 && toCheck <= range2) {
			return true;
		} else {
			return false;
		}
	}

	// returns the amount of stones that should be placed
	public int getStoneCount() {
		return this.stoneCount;
	}
	
	// this method catches invalid user inputs when a String is entered but an integer was expected
	public int getScannerInt() {
		boolean continueInput = true;
		int number = 0;
		do {
			try {
				number = scanner.nextInt();
				continueInput = false;
			}
			catch (InputMismatchException ex) {
				System.out.println("Invalid input, integer is required. Please try again: ");
				scanner.nextLine();
			}
		} while (continueInput);
		
		return number;
	}

	// don't know why but it is used in Spielmaster so I give him this shit
	public String getScanner() {
		return scanner.next();
	}
}
