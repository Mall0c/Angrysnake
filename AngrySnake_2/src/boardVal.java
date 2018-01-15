public class boardVal {

	int x1;
	int y1;
	int x2;
	int y2;
	int z1;
	int z2;
	int SP1Maulwuerfe;
	int SP2Maulwuerfe;
	int heurVal;
	boolean p1;
	boolean maulthrow;

	// x1 and y1 are the coordinates for the snake head of player 1
	// x2 and y2 are the coordinates for the snake head of player 2
	// z1 and z2 are used to reconstruct the last move

	// p1 is to determine whether the node belongs to player 1 or 2
	// which is later required to know to generate possibleMoves which depend on the
	// players snake head position

	// so each node contains information about the move, which player the turn
	// belongs to and where the the two snake heads are right now
	
	public boardVal(int x1, int y1, int x2, int y2, int z1, int z2, boolean p1, int SP1Maulwuerfe, int SP2Maulwuerfe) {
		setX1(x1);
		setY1(y1);
		setX2(x2);
		setY2(y2);
		setZ1(z1);
		setZ2(z2);
		setP1(p1);
		setSP1Maulwuerfe(SP1Maulwuerfe);
		setSP2Maulwuerfe(SP2Maulwuerfe);
	}

	public boolean isMaulthrow() {
		return this.maulthrow;
	}
	
	public void setMaulthrow(boolean x) {
		this.maulthrow = x;
	}
	
	public int getSP1Maulwuerfe() {
		return this.SP1Maulwuerfe;
	}
	
	public void setSP1Maulwuerfe(int x) {
		this.SP1Maulwuerfe = x;
	}
	
	public int getSP2Maulwuerfe() {
		return this.SP2Maulwuerfe;
	}
	
	public void setSP2Maulwuerfe(int x) {
		this.SP2Maulwuerfe = x;
	}
	
	public int getX1() {
		return x1;
	}
	

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getZ1() {
		return z1;
	}

	public void setZ1(int z1) {
		this.z1 = z1;
	}

	public int getZ2() {
		return z2;
	}

	public void setZ2(int z2) {
		this.z2 = z2;
	}

	public int getHeurVal() {
		return heurVal;
	}

	public void setHeurVal(int heurVal) {
		this.heurVal = heurVal;
	}

	public boolean isP1() {
		return p1;
	}

	public void setP1(boolean p1) {
		this.p1 = p1;
	}
}
