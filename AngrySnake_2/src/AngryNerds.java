



//verwendung der wrapper klasse: ihr m�sst nur einen objekt von unserer wrapper klasse 
public class AngryNerds {
	public Commander commander = new Commander() ;

	
	public static int gameStatus = 0;
	
	
	public void setStart() {

		gameStatus = 1;	
		
		this.commander.board.player1 = new Player("p1");
		this.commander.board.player2 = new Player("p2");
		// player1
		this.commander.board.player1.setPlayerHeadColumn(1);
		this.commander.board.player1.setPlayerHeadRow(4);
		this.commander.board.setCell(4, 1, 'X');
		// player2
		this.commander.board.player2.setPlayerHeadColumn(7);
		this.commander.board.player2.setPlayerHeadRow(4);
		this.commander.board.setCell(4, 7, 'O');

	}
	AngryNerds(){
		this.setStart();
	}
	
	public boolean isRunning() {
		return (gameStatus == 1) ? true : false;
	}
	
	//true - player1 ; false - player2
	public boolean whoWon() {
		if (gameStatus != 2 && gameStatus != 3)
			throw new IllegalArgumentException("Game is still running");
		else
			return (gameStatus == 2) ? true : false;
	}
	
	public boolean isValidMove(String move){
		return commander.isValidMove(move);
	}
	
	//gives a move to enemy player
	public String yourMove(){
		String move = AI.getMove(commander.getPlayer(), this.commander.board, 3);
		this.commander.makeMove(move);
		return move;

	}
	
	//get a move from the enemy player
	public void myMove(String move){
		if(this.isValidMove(move))
			this.commander.makeMove(move);
		else
			System.out.println("The following move: " + move + " is invalid");
		
		
		
	}
	
	public void printBoard(){
		commander.board.printBoard();
	}
	
	
	
}