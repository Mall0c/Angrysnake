
public class Player {

	private String name;

	private String move;
	//neu
	private int Maulwurf = 2;
	private int playerHeadRow;
	private int playerHeadColumn;

	public Player(String name) {
		this.name = name;

	}
	//neu
	public void benutzeMaulwurf() {
		this.Maulwurf=this.Maulwurf-1;
	}
	
	
	
	

	public void myMove(String move) {
		this.move = move;
	}

	public String yourMove() {
		return move;
	}

	public Player getCopy() {
		
		Player copiedPlayer = new Player(name);
		copiedPlayer.setPlayerHeadColumn(playerHeadColumn);
		copiedPlayer.setPlayerHeadRow(playerHeadRow);
		copiedPlayer.setMaulwurf(getMaulwurf());
		return copiedPlayer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public int getPlayerHeadRow() {
		return playerHeadRow;
	}
	public void setPlayerHeadRow(int playerHeadRow) {
		this.playerHeadRow = playerHeadRow;
	}
	public int getPlayerHeadColumn() {
		return playerHeadColumn;
	}
	public void setPlayerHeadColumn(int playerHeadColumn) {
		this.playerHeadColumn = playerHeadColumn;
	}
	public int getPlayerMoleCount() {
		return this.Maulwurf;
	}
	public void setMaulwurf(int maulwurf) {
		Maulwurf = maulwurf;
	}
	public int getMaulwurf(){
		return this.Maulwurf;
	}
	
}
