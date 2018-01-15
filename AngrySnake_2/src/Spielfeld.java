public class Spielfeld {

	private char[][] gameField;
	private int obstacle = 5;
	private int p1X = 0;
	private int p1Y = 3;
	private int p2X = 6;
	private int p2Y = 3;
	private int row = 7;
	private int column = 7;
	private char p1Token = 'O';
	private char p2Token = 'X';
	private char obstactleToken = '#';
	private int freeFields = 49;
	// private boardVal startPositions = new boardVal(p1X, p1Y, p2X, p2Y, p1X, p1Y, 0.0, true); 
	// private int depth = 4;
	
	// didn't find an easy way to extract the start position from the player class
	// BEFORE players add their tokens by themselves
	// something like spieler1.getXKopf(); etc
	// maybe add two fields to the game field which represent the players or something
	// or if necessary got to add the two player parameters to the initializing method

	public Spielfeld(int obstacle) {
		this.setGameField(gameField = new char[7][7]);
		this.setObstacle(obstacle);
		this.initBoard();
		// this.initTree();
		// this is wrong since we won't be able to generate the full tree(!)
		// the three needs to be generated inside the AI with a specific depth
	}
	
	private Spielfeld initBoard() {
		for (int i = 0; i < getRow(); i++) {
			for (int j = 0; j < getColumn(); j++) {
				this.gameField[i][j] = ' ';
			}
		}
		this.gameField[p1X][p1Y] = p1Token;
		this.gameField[p2X][p2Y] = p2Token;
		this.freeFields -= 2;
		this.placeObstacles(getObstacle());
		return this;
	}
	
//	private TreeNode initTree() {
//		TreeNode gameTree = new TreeNode(startPositions, gameField, getDepth());
//		return gameTree;
//	}

	private void placeObstacles(int obstacle) {
		int[][] obstaclePos = new int[obstacle][2];
		for (int i = 0; i < obstacle; i++) {
			do {
			obstaclePos[i][0] = (int) (Math.random() * 7);
			obstaclePos[i][1] = (int) (Math.random() * 7);		
			} while (!obstacleCheckSuccess(obstaclePos[i][0], obstaclePos[i][1],obstaclePos, i));
		}
		for (int i = 0; i < obstacle; i++) {
			setCellValue(obstaclePos[i][0], obstaclePos[i][1], obstactleToken);
		}
	}
	
	private boolean obstacleCheckSuccess(int x, int y, int[][] obstaclePos, int index) {
		// overlap with start positions?
		if ((x == p1X && y == p1Y) || (x == p2X || y == p2Y)) {
			return false;
		} // check for doubles
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
	
	// PrÂ�Een, ob der Spieler Â�Eerhaupt ZÂ�Ee hat. Falls keine ZÂ�Ee vorhanden sind, wird geprÂ�Et,
	// ob es noch freie Felder fÂ�E MaulwÂ�Efe gibt
	public boolean _isRunning(Spieler spieler) {
		if(this.freeFields == 0) {
			return false;
		}
		int xKopf = spieler.getXKopf();
		int yKopf = spieler.getYKopf();
		// left
		if (this.gameField[(xKopf-1+7)%7][yKopf] == ' ') {
			if (this.gameField[(xKopf-2+7)%7][yKopf] == ' ') { 					// left
				return true;
			} else if (this.gameField[(xKopf-1+7)%7][(yKopf-1+7)%7] == ' ') {	// down
				return true;
			} else if (this.gameField[(xKopf-1+7)%7][(yKopf+1+7)%7] == ' ') {	// up
				return true;
			}
		}
		// right
		if (this.gameField[(xKopf+1+7)%7][yKopf] == ' ') {
			if (this.gameField[(xKopf+2+7)%7][yKopf] == ' ') { 					// right
				return true;
			} else if(this.gameField[(xKopf+1+7)%7][(yKopf-1+7)%7] == ' ') {	// down
				return true;
			} else if(this.gameField[(xKopf+1+7)%7][(yKopf+1+7)%7] == ' ') {	// right
				return true;
			}
		}
		// up
		if (this.gameField[xKopf][(yKopf+1+7)%7] == ' ') {
			if (this.gameField[(xKopf+1+7)%7][(yKopf+1+7)%7] == ' ') { 			// right
				return true;
			} else if (this.gameField[xKopf][(yKopf+2+7)%7] == ' ') {			// top
				return true;
			} else if (this.gameField[(xKopf-1+7)%7][(yKopf+1+7)%7] == ' ') {	// left
				return true;
			}
		}
		// down
		if (this.gameField[xKopf][(yKopf-1+7)%7] == ' ') {
			if (this.gameField[(xKopf+1+7)%7][(yKopf-1+7)%7] == ' ') { 			// right
				return true;
			} else if (this.gameField[xKopf][(yKopf-2+7)%7] == ' ') {			// down
				return true;
			} else if (this.gameField[(xKopf-1+7)%7][(yKopf-1+7)%7] == ' ') {	// left
				return true;
			}
		}
		if(spieler.getAnzahlMaulwuerfe() > 0) {
			return true; // snake can't move but moles can be placed
		}
		return false;
	}

	public void setCellValue(int x, int y, char value) {
			gameField[x][y] = value;
	}

	public char getCellValue(int x, int y) {
		return gameField[x][y];
	}
	
	public void setField(int x, int y, char token) {
		gameField[x][y] = token;
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
		return gameField;
	}

	private void setGameField(char[][] gameField) {
		this.gameField = gameField;
	}

	private void setObstacle(int obstacle) {
		this.obstacle = obstacle;
	}

	private int getObstacle() {
		return obstacle;
	}
	
	public int getFreeFields() {
		return this.freeFields;
	}
	
//	public boardVal getStartPositions() {
//		return startPositions;
//	}
//
//	public void setStartPositions(boardVal startPositions) {
//		this.startPositions = startPositions;
//	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

//	public int getDepth() {
//		return depth;
//	}
//
//	public void setDepth(int depth) {
//		this.depth = depth;
//	}
}
