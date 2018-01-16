public class Commander {

	public Gameboard board= new Gameboard();
	private boolean player1=true; // true if player 1 makes the move, false if player
								// 2 makes the move

	public Commander(){
		
	}
	
	public Commander(Gameboard gameBoard, boolean player1) {
		this.board = gameBoard;
		this.player1 = player1;
	}

	// checks if the format is valid
	private static int matchMove(String move) {
		if (move.matches("[a-gA-G][1-7][a-gA-G][1-7]")) // B5B6 format
			return 1;
		if (move.matches("[wasdWASD][wasdWASD]")) // wW format
			return 2;
		if (move.matches("[a-gA-G][1-7]")) // B4 format
			return 3;
		if (move.matches("odus [a-gA-G][1-7][a-gA-G][1-7][a-gA-G][1-7][a-gA-G][1-7]")) // odus
																						// B4B5B6C6
																						// format
			return 4;
		if (move.matches("odus [wasdWASD][wasdWASD][wasdWASD][wasdWASD]")) // odus
																			// wWwW
																			// format
			return 5;
		if (move.matches("odus [a-gA-G][1-7]"))// odus B4 format
			return 6;
		return 0;
	}

	public boolean isValidMove(String move) {
		int formatCase = matchMove(move);
		char firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter, sixthLetter, seventhLetter,
				eighthLetter;
		if (formatCase == 0) {
			System.out.println("Fehlerhafte Eingabe");
			return false;
		}

		// bsp.:B4B5
		else if (formatCase == 1) {

			firstLetter = move.charAt(0);
			secondLetter = move.charAt(1);
			thirdLetter = move.charAt(2);
			fourthLetter = move.charAt(3);
			int row1 = 8 - Character.getNumericValue(secondLetter);
			int column1 = Character.getNumericValue(firstLetter) - 9;
			int row2 = 8 - Character.getNumericValue(fourthLetter);
			int column2 = Character.getNumericValue(thirdLetter) - 9;

			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(row1, column1))
				return false;

			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(row2, column2))
				return false;

			int headRow;
			int headColumn;

			if (player1 == true) {
				headRow = board.player1.getPlayerHeadRow();
				headColumn = board.player1.getPlayerHeadColumn();
			}

			else {
				headRow = board.player2.getPlayerHeadRow();
				headColumn = board.player2.getPlayerHeadColumn();
			}

			int differenceRow = headRow - row1;
			int differenceColumn = headColumn - column1;
			if (!isValidCoordinate(differenceRow, differenceColumn))
				return false;

			differenceRow = row1 - row2;
			differenceColumn = column1 - column2;
			if (!isValidCoordinate(differenceRow, differenceColumn))
				return false;

			return true;
		}
		// bsp.: ww
		else if (formatCase == 2) {

			firstLetter = move.charAt(0);
			secondLetter = move.charAt(1);
			int headRow;
			int headColumn;
			if (player1 == true) {
				headRow = board.player1.getPlayerHeadRow();
				headColumn = board.player1.getPlayerHeadColumn();
			}

			else {
				headRow = board.player2.getPlayerHeadRow();
				headColumn = board.player2.getPlayerHeadColumn();
			}

			switch (firstLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				return false;
			}
			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(headRow, headColumn))
				return false;

			switch (secondLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				return false;
			}
			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(headRow, headColumn))
				return false;

			return true;
		}
		// bsp.:B4 --> Maulwurf
		else if (formatCase == 3) {
			firstLetter = move.charAt(0);
			secondLetter = move.charAt(1);

			int row1 = 8 - Character.getNumericValue(secondLetter);
			int column1 = Character.getNumericValue(firstLetter) - 9;

			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(row1, column1))
				return false;
			

			return true;

		}
		// bsp.: odus B4B5B6C6 -->cheat 4 felder bewegen
		else if (formatCase == 4) {
			firstLetter = move.charAt(5);
			secondLetter = move.charAt(6);
			thirdLetter = move.charAt(7);
			fourthLetter = move.charAt(8);
			fifthLetter = move.charAt(9);
			sixthLetter = move.charAt(10);
			seventhLetter = move.charAt(11);
			eighthLetter = move.charAt(12);
			int column1 = Character.getNumericValue(firstLetter) - 9;
			int row1 = 8 - Character.getNumericValue(secondLetter);
			int column2 = Character.getNumericValue(thirdLetter) - 9;
			int row2 = 8 - Character.getNumericValue(fourthLetter);
			int column3 = Character.getNumericValue(fifthLetter) - 9;
			int row3 = 8 - Character.getNumericValue(sixthLetter);
			int column4 = Character.getNumericValue(seventhLetter) - 9;
			int row4 = 8 - Character.getNumericValue(eighthLetter);

			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(row1, column1))
				return false;

			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(row2, column2))
				return false;
			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(row3, column3))
				return false;

			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(row4, column4))
				return false;

			int headRow;
			int headColumn;

			if (player1 == true) {
				headRow = board.player1.getPlayerHeadRow();
				headColumn = board.player1.getPlayerHeadColumn();
			}

			else {
				headRow = board.player2.getPlayerHeadRow();
				headColumn = board.player2.getPlayerHeadColumn();
			}

			int differenceRow = headRow - row1;
			int differenceColumn = headColumn - column1;
			if (!isValidCoordinate(differenceRow, differenceColumn))
				return false;

			differenceRow = row1 - row2;
			differenceColumn = column1 - column2;
			if (!isValidCoordinate(differenceRow, differenceColumn))
				return false;

			differenceRow = row2 - row3;
			differenceColumn = column2 - column3;
			if (!isValidCoordinate(differenceRow, differenceColumn))
				return false;

			differenceRow = row3 - row4;
			differenceColumn = column3 - column4;
			if (!isValidCoordinate(differenceRow, differenceColumn))
				return false;
			return true;

		}
		// bsp.: odus wwaa -->cheat 4 felder bewegen
		else if (formatCase == 5) {

			firstLetter = move.charAt(5);
			secondLetter = move.charAt(6);
			thirdLetter = move.charAt(7);
			fourthLetter = move.charAt(8);
			int headRow;
			int headColumn;
			if (player1 == true) {
				headRow = board.player1.getPlayerHeadRow();
				headColumn = board.player1.getPlayerHeadColumn();
			}

			else {
				headRow = board.player2.getPlayerHeadRow();
				headColumn = board.player2.getPlayerHeadColumn();
			}

			switch (firstLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				return false;
			}
			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(headRow, headColumn))
				return false;

			switch (secondLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				return false;
			}
			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(headRow, headColumn))
				return false;

			switch (thirdLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				return false;
			}
			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(headRow, headColumn))
				return false;

			switch (fourthLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				return false;
			}

			// √ºberpr√ºfe ob feld belegt ist
			if (board.isOccupied(headRow, headColumn))
				return false;

			return true;

		}
		// bsp.: odus B4-->cheat maulw√ºrf setzen
		else if (formatCase == 6) {
			firstLetter = move.charAt(5);
			secondLetter = move.charAt(6);
			if (!isValidMove("" + firstLetter + secondLetter))
				return false;

			return true;
		}
		return true;

	}

	private boolean isValidCoordinate(int differenceRow, int differenceColumn) {

		if (!(differenceRow == 1 || differenceRow == -1 || differenceRow == 0 || differenceRow == 6
				|| differenceRow == -6))
			return false;
		if (!(differenceColumn == 1 || differenceColumn == -1 || differenceColumn == 0 || differenceColumn == 6
				|| differenceColumn == -6))
			return false;
		// aus dem rand raus gehe oben /unten
		if ((differenceRow == 6 || differenceRow == -6) && !(differenceColumn == 0))
			return false;
		// aus dem rand raus gehe links /rechts
		if ((differenceColumn == 6 || differenceColumn == -6) && !(differenceRow == 0))
			return false;
		if (!((differenceRow == 0)
				&& (differenceColumn == 1 || differenceColumn == -1 || differenceColumn == 6 || differenceColumn == -6)
				|| (differenceColumn == 0)
						&& (differenceRow == 1 || differenceRow == -1 || differenceRow == 6 || differenceRow == -6)))
			return false;

		return true;
	}

	public void makeMove(String move) {
		int formatCase = matchMove(move);
		char firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter, sixthLetter, seventhLetter,
				eighthLetter;

		int headRow;
		int headColumn;

		if (player1 == true) {
			headRow = board.player1.getPlayerHeadRow();
			headColumn = board.player1.getPlayerHeadColumn();
		}

		else {
			headRow = board.player2.getPlayerHeadRow();
			headColumn = board.player2.getPlayerHeadColumn();
		}

		if (formatCase == 0) {
			System.out.println("Fehlerhafte Eingabe");
		}

		// bsp.:B4B5
		else if (formatCase == 1) {

			firstLetter = move.charAt(0);
			secondLetter = move.charAt(1);
			thirdLetter = move.charAt(2);
			fourthLetter = move.charAt(3);
			int row1 = 8 - Character.getNumericValue(secondLetter);
			int column1 = Character.getNumericValue(firstLetter) - 9;
			int row2 = 8 - Character.getNumericValue(fourthLetter);
			int column2 = Character.getNumericValue(thirdLetter) - 9;

			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());
			board.setCell(row1, column1, getcurrentPlayerBodySymbol());
			board.setCell(row2, column2, getcurrentPlayerHeadSymbol());
			if (player1 == true) {
				board.player1.setPlayerHeadRow(row2);
				board.player1.setPlayerHeadColumn(column2);
			} else {
				board.player2.setPlayerHeadRow(row2);
				board.player2.setPlayerHeadColumn(column2);
			}

		}
		// bsp.: ww
		else if (formatCase == 2) {

			firstLetter = move.charAt(0);
			secondLetter = move.charAt(1);
			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());

			switch (firstLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				break;
			}
			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());

			switch (secondLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				break;
			}
			board.setCell(headRow, headColumn, getcurrentPlayerHeadSymbol());

			if (player1 == true) {
				board.player1.setPlayerHeadRow(headRow);
				board.player1.setPlayerHeadColumn(headColumn);
			} else {
				board.player2.setPlayerHeadRow(headRow);
				board.player2.setPlayerHeadColumn(headColumn);
			}
		}
		// bsp.:B4 --> Maulwurf
		else if (formatCase == 3) {
			firstLetter = move.charAt(0);
			secondLetter = move.charAt(1);

			int row1 =8 - Character.getNumericValue(secondLetter);
			int column1 = Character.getNumericValue(firstLetter) - 9;
			board.setCell(row1, column1, '#');

			//hier ne ver‰nderung, anstelle von --Molecount zu board.player.minusEinsMole
			if (player1) {
				board.player1.benutzeMaulwurf();
			} else {
				board.player2.benutzeMaulwurf();
			}

		}
		// bsp.: odus B4B5B6C6 -->cheat 4 felder bewegen
		else if (formatCase == 4) {
			firstLetter = move.charAt(5);
			secondLetter = move.charAt(6);
			thirdLetter = move.charAt(7);
			fourthLetter = move.charAt(8);
			fifthLetter = move.charAt(9);
			sixthLetter = move.charAt(10);
			seventhLetter = move.charAt(11);
			eighthLetter = move.charAt(12);
			int column1 = Character.getNumericValue(firstLetter) - 9;
			int row1 =8 - Character.getNumericValue(secondLetter);
			int column2 = Character.getNumericValue(thirdLetter) - 9;
			int row2 =8 -  Character.getNumericValue(fourthLetter);
			int column3 = Character.getNumericValue(fifthLetter) - 9;
			int row3 =8 -  Character.getNumericValue(sixthLetter);
			int column4 = Character.getNumericValue(seventhLetter) - 9;
			int row4 =8 -  Character.getNumericValue(eighthLetter);

			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());
			board.setCell(row1, column1, getcurrentPlayerBodySymbol());
			board.setCell(row2, column2, getcurrentPlayerBodySymbol());
			board.setCell(row3, column3, getcurrentPlayerBodySymbol());
			board.setCell(row4, column4, getcurrentPlayerHeadSymbol());
			if (player1 == true) {
				board.player1.setPlayerHeadRow(row4);
				board.player1.setPlayerHeadColumn(column4);
			} else {
				board.player2.setPlayerHeadRow(row4);
				board.player2.setPlayerHeadColumn(column4);
			}

		}
		// bsp.: odus wwaa -->cheat 4 felder bewegen
		else if (formatCase == 5) {

			firstLetter = move.charAt(5);
			secondLetter = move.charAt(6);
			thirdLetter = move.charAt(7);
			fourthLetter = move.charAt(8);

			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());

			switch (firstLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				break;
			}
			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());

			switch (secondLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				break;
			}
			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());

			switch (thirdLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				break;
			}
			board.setCell(headRow, headColumn, getcurrentPlayerBodySymbol());

			switch (fourthLetter) {
			case 'w':
			case 'W':
				--headRow;
				if (headRow == 0)
					headRow = 7;
				break;
			case 'a':
			case 'A':
				--headColumn;
				if (headColumn == 0)
					headColumn = 7;
				break;
			case 's':
			case 'S':
				++headRow;
				if (headRow == 8)
					headRow = 1;
				break;
			case 'd':
			case 'D':
				++headColumn;
				if (headColumn == 8)
					headColumn = 1;
				break;
			default:
				break;
			}
			board.setCell(headRow, headColumn, getcurrentPlayerHeadSymbol());

			if (player1 == true) {
				board.player1.setPlayerHeadRow(headRow);
				board.player1.setPlayerHeadColumn(headColumn);
			} else {
				board.player2.setPlayerHeadRow(headRow);
				board.player2.setPlayerHeadColumn(headColumn);
			}
		}
		// bsp.: odus B4-->cheat maulw√ºrf setzen
		else if (formatCase == 6) {
			firstLetter = move.charAt(5);
			secondLetter = move.charAt(6);
			int row1 = 8 - Character.getNumericValue(secondLetter);
			int column1 = Character.getNumericValue(firstLetter) - 9;
			board.setCell(row1, column1, '#');
		}

		player1 = (player1 == true) ? false : true;

	}

	private char getcurrentPlayerHeadSymbol() {
		return (player1 == true) ? 'X' : 'O';

	}

	private char getcurrentPlayerBodySymbol() {
		return (player1 == true) ? '-' : '+';

	}
	
	public boolean getPlayer() {
		return player1;
	}
	
	public void setPlayer(boolean player1) {
		this.player1=player1;
	}

	/**
	 * If current player can't play turn, opponent wins
	 * 
	 * @return 1: Nobody won 2: Player1 won 3: Player2 won
	 */
	public int whoIsTheWinner() {
		if (player1 && !turnPossible(true)) {
			return 3;
		}
		if (!player1 && !turnPossible(false)) {
			return 2;
		}
		
		return 1;
	}

	/**
	 * Look if current player has permission to play this turn
	 * 
	 * @return Turn is possible
	 */
	public boolean turnPossible(boolean forPlayer) {
		// first this method checks, if the board is full
		if (board.isBoardFull()) {
			return false;
		}
		// if the board isnt full, theres theoretically another move possible,
		// so all the possible directions in which the snake can
		// move are beeing checked; it also considers if the player can make
		// another move via a mole
		
		Gameboard newBoard = board.getCopy();
		Commander newCommander = new Commander(newBoard, forPlayer);
		
		String[] possibleDirections = { "wa", "ww", "wd", "dw", "dd", "ds", "sd", "ss", "sa", "as", "aa", "aw" };
		
		if(forPlayer == true) {
			for (int i = 0; i < possibleDirections.length - 1; i++) {

				if (newCommander.isValidMove(possibleDirections[i]) || newBoard.player1.getPlayerMoleCount() != 0) {
					return true;
				}

			}
		}
		
		if(forPlayer == false) {
			for (int i = 0; i < possibleDirections.length - 1; i++) {

				if (newCommander.isValidMove(possibleDirections[i]) || newBoard.player2.getPlayerMoleCount() != 0) {
					return true;
				}

			}
		}
		return false;
	}

}
