// notes - MVC, 3 packages: model, control, view
// mole would probably need to be initialized with a method from outside - to stay clean
// anyone ever noticed how comments and communists are spelled way to similar for comfort?
// if you add every third letter from each method you get 'VLADIMIR LENIN' - coincidence, I think not.

// needs a cleanup, fields and values are a mess - same for constructors

public class Spielfeld {

	private char[][] gameField;
	private static int row = 7;
	private static int column = 7;
	private static int obstacle = 5;
	private int p1X = 0; // placeholder 
	private int p1Y = 3; // placeholder 
	private int p2X = 6; // placeholder 
	private int p2Y = 3; // placeholder 
	private char p1T = 'O'; // placeholder 
	private char p2T = 'X'; // placeholder 
	private char obstT = '#'; // placeholder
	private int freeFields = 49;
	
	// didn't find an easy way to extract the start position from the player class
	// BEFORE players add their tokens by themselves
	// something like spieler1.getXKopf(); etc
	// maybe add two fields to the game field which represent the players or something
	// or if necessary gotta add the two player parameters to the initializing method

	public Spielfeld(int obstacle) {
		this.setGameField(gameField = new char[row][column]);
		this.setObstacle(obstacle);
		// set.players at start position with an outside method (?)
		this.initBoard();
	}
	
	// PrEen, ob der Spieler Eerhaupt ZEe hat. Falls keine ZEe vorhanden sind, wird geprEt,
	// ob es noch freie Felder fE MaulwEfe gibt
	public boolean _isRunning(Spieler spieler) {
		if(this.freeFields == 0) {
			return false;
		}
		int xKopf = spieler.getXKopf();
		int yKopf = spieler.getYKopf();
		// nach links
		if(this.gameField[(xKopf-1+7)%7][yKopf] == ' ') {
			if(this.gameField[(xKopf-2+7)%7][yKopf] == ' ') { 					// nach links
				return true;
			} else if(this.gameField[(xKopf-1+7)%7][(yKopf-1+7)%7] == ' ') {	// nach unten
				return true;
			} else if(this.gameField[(xKopf-1+7)%7][(yKopf+1+7)%7] == ' ') {	// nach oben
				return true;
			}
		}
		// nach rechts
		if(this.gameField[(xKopf+1+7)%7][yKopf] == ' ') {
			if(this.gameField[(xKopf+2+7)%7][yKopf] == ' ') { 					// nach rechts
				return true;
			} else if(this.gameField[(xKopf+1+7)%7][(yKopf-1+7)%7] == ' ') {	// nach unten
				return true;
			} else if(this.gameField[(xKopf+1+7)%7][(yKopf+1+7)%7] == ' ') {	// nach oben
				return true;
			}
		}
		// nach oben
		if(this.gameField[xKopf][(yKopf+1+7)%7] == ' ') {
			if(this.gameField[(xKopf+1+7)%7][(yKopf+1+7)%7] == ' ') { 			// nach rechts
				return true;
			} else if(this.gameField[xKopf][(yKopf+2+7)%7] == ' ') {			// nach oben
				return true;
			} else if(this.gameField[(xKopf-1+7)%7][(yKopf+1+7)%7] == ' ') {	// nach links
				return true;
			}
		}
		// nach unten
		if(this.gameField[xKopf][(yKopf-1+7)%7] == ' ') {
			if(this.gameField[(xKopf+1+7)%7][(yKopf-1+7)%7] == ' ') { 			// nach rechts
				return true;
			} else if(this.gameField[xKopf][(yKopf-2+7)%7] == ' ') {			// nach unten
				return true;
			} else if(this.gameField[(xKopf-1+7)%7][(yKopf-1+7)%7] == ' ') {	// nach links
				return true;
			}
		}
		if(spieler.getAnzahlMaulwuerfe() > 0) {
			return true; // Spieler kann sich nicht bewegen, aber MaulwEfe platzieren
		}
		return false;
	}

	private void initBoard() {
		for (int i = 0; i < getRow(); i++) {
			for (int j = 0; j < getColumn(); j++) {
				this.gameField[i][j] = ' ';
			}
		}
		this.gameField[p1X][p1Y] = p1T;
		this.gameField[p2X][p2Y] = p2T;
		this.freeFields -= 2;
		this.placeObstacles(getObstacle());
	}

	private void placeObstacles(int obstacle) {
		// this isn't very efficient since
		// the snippet will continue to generate x obstacles
		// until they fulfill the criteria
		int[][] obstaclePos = new int[obstacle][2];
		for (int i = 0; i < obstacle; i++) {
			do {
			obstaclePos[i][0] = (int) (Math.random() * 6);
			obstaclePos[i][1] = (int) (Math.random() * 6);		
			} while (!obstacleCheckSuccess(obstaclePos[i][0], obstaclePos[i][1],obstaclePos, i));
		}
		// gotta place em after checking
		for (int i = 0; i < obstacle; i++) {
			// placeholder, mule symbol needs to be defined and setCellValue needs to be
			// addressed by the mole itself I guess?
			setCellValue(obstaclePos[i][0], obstaclePos[i][1], obstT);
		}
	}
	
	private boolean obstacleCheckSuccess(int x, int y, int[][] obstaclePos, int index) {
		// overlap with start positions?
		if ((x == p1X && y == p1Y) || (x == p2X || y == p2Y)) {
			return false;
		} 
		// check for doubles yo
		for (int i = 0; i < index; i++) {
			if (x == obstaclePos[i][0] && y == obstaclePos[i][1]) {
				return false;
			}		
		} 	
		// this could be a bit more efficient if we'd keep track on the flags outside the method
		boolean leftRight = false; boolean leftBottom = false; boolean leftTop = false; boolean rightLeft = false; boolean rightBottom = false; boolean rightTop = false;
		
		// check for surrounding elements and set flags
		for (int i = 0; i <= index; i++) {
			if (obstaclePos[i][0] == p1X + 1 && obstaclePos[i][1] == p1Y) {
				leftRight = true;
			}
			if (obstaclePos[i][0] == p1X && obstaclePos[i][1] == p1Y + 1) {
				leftBottom = true;
			}
			if (obstaclePos[i][0] == p1X && obstaclePos[i][1] == p1Y - 1) {
				leftTop = true;
			}	
			if (obstaclePos[i][0] == p2X - 1 && obstaclePos[i][1] == p2Y) {
				rightLeft = true;
			}
			if (obstaclePos[i][0] == p2X && obstaclePos[i][1] == p2Y + 1) {
				rightBottom = true;
			}
			if (obstaclePos[i][0] == p2X && obstaclePos[i][1] == p2Y - 1) {
				rightTop = true;
			}
		}
		// check whether there is more than one surrounding token
		if ((leftRight ? 1:0) + (leftBottom ? 1:0) + (leftTop ? 1:0) > 1) {
			return false;
		}
		if ((rightLeft ? 1:0) + (rightBottom ? 1:0) + (rightTop ? 1:0) > 1) {
			return false;
		}
		return true;
		
	}

	public void setCellValue(int x, int y, char value) {
		if ((x < getRow() && x >= 0) && (y < getColumn() && y >= 0)) {
			// value needs to be defined by the action and thus not checked here
			this.gameField[x][y] = value;
		} else {
			// link back to user input - should also get checked beforehand tbh
		}
	}

	public char getCellValue(int x, int y) {
		// needs to pre-check x and y to avoid array out of bounds exception
		return this.gameField[x][y];
	}
	
	public void setField(int x, int y, char token) {
		this.gameField[x][y] = token;
	}

	public String printField() {
		// this needs information on whose turn it is
		// also information on remaining moles
		String board = "";
		for (int i = getRow()-1; i >= 0; i--) {
			board += (i+1);
			//System.out.print((i+1));
			for (int j = 0; j < getColumn(); j++) {
				//System.out.print("[" + getCellValue(j, i) + "]");
				board += "[" + getCellValue(j, i) + "]";
			}
			board += "\n";
			//System.out.println();
		}
		board += " ";
		//System.out.print(" ");
		for (char alph = 'A'; alph <= 'G'; alph++) {
			//System.out.print(" " + alph + " ");
			board += " " + alph + " ";
		}
		board += "\n";
		//System.out.println();
		return board;
	}

	public char[][] getGameField() {
		return this.gameField;
	}

	private void setGameField(char[][] gameField) {
		this.gameField = gameField;
	}

	private static int getColumn() {
		return column;
	}

	private static int getRow() {
		return row;
	}

	private void setObstacle(int obstacle) {
		Spielfeld.obstacle = obstacle;
	}

	private static int getObstacle() {
		return obstacle;
	}
	
	public int getFreeFields() {
		return this.freeFields;
	}

}
