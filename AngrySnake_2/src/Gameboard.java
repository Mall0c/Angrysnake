public class Gameboard {

	public char[][] board = new char[8][8];
	// anstelle von int Molecount,row und collumn, zwei gescheite Objekte
	Player player1;
	Player player2;
	
	
	Gameboard(){
		this.initBoard();
	}
	private void initBoard() {
		for (int i = 1; i < 8; i++) {
			for (int j = 1; j < 8; j++) {
				this.board[i][j] = ' ';
			}
		}
	}
	
	public char[][] copyMatrix (){
		char[][] copiedMatrix = new char[8][8];
		for (int i = 1; i < 8; i++) {
			for (int j = 1; j < 8; j++) {
				copiedMatrix[i][j] = this.board[i][j];
			}
		}
		return copiedMatrix;
	}
	public Gameboard getCopy() {

		Gameboard copiedBoard = new Gameboard();
		for (int i = 1; i < 8; i++) {
			for (int j = 1; j < 8; j++) {
				copiedBoard.board[i][j] = this.board[i][j];
			}
		}
		copiedBoard.player1 = player1.getCopy();
		copiedBoard.player2 = player2.getCopy();
		return copiedBoard;
	}

	void printBoard() {
		System.out.println("  A  B  C  D  E  F  G");
		for (int i = 1; i < 8; i++) {
			System.out.print(8 - i);
			for (int j = 1; j < 8; j++) {
				System.out.print("["+this.board[i][j]+"]");
			}
			System.out.println();
		}
	}
	
	char getCell(int row,int column){
		return this.board[row][column];
	}
	
	void setCell(int row,int column,char value){
		this.board[row][column]=value;
	}
	
	boolean isOccupied(int row,int column){
		if(this.board[row][column]!=' '){
			return true;
		}
		
		return false;
	}
	
	
	
	//hier ne ver‰nderung, die getter methoden greifen nun auf player objekte zu
	
	

	//checks, if every cell in the board is filled, meaning theres no where for the snake or mole to go
	boolean isBoardFull(){
		for(int i= 0 ; i< board.length-1;i++){
			for(int j= 0; j<board.length-1;j++){
			if(!isOccupied(i, j))
				return false;
		}
	}
		return true;
	}
	
}
