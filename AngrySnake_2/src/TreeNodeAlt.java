import java.util.Arrays;

public class TreeNodeAlt {
	
	private byte[] moveInfo = new byte[8];
	private TreeNodeAlt[] children;
	private TreeNodeAlt parent;
	
	private int depth;
	private static char[][] ogBoard;
	
	public TreeNodeAlt(char[][] board, byte... coordinates) {
		if (coordinates.length == 0) {
	        throw new IllegalArgumentException("No values motherfucker?");
		}
		for(int outterIndex = 0; outterIndex < ogBoard.length; outterIndex++) {
			  for(int innerIndex = 0; innerIndex < ogBoard[outterIndex].length; innerIndex++) {
			    ogBoard[outterIndex][innerIndex] = board[outterIndex][innerIndex];
			  }
		}
		moveInfo = Arrays.copyOf(coordinates, 8);
	}
	
	public TreeNodeAlt(byte... coordinates) {
		if (coordinates.length == 0) {
	        throw new IllegalArgumentException("No values motherfucker?");
		}
		moveInfo = Arrays.copyOf(coordinates, 8);
	}
	
	public byte[] getMoveInfo() {
		return moveInfo;
	}

	public TreeNodeAlt getParent() {
		return parent;
	}

	public void setParent(TreeNodeAlt parent) {
		this.parent = parent;
	}

	public TreeNodeAlt[] getChildren() {
		return children;
	}

	public void setChildren(TreeNodeAlt[] children) {
		this.children = children;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public static char[][] getOgBoard() {
		return ogBoard;
	}

	public static void setOgBoard(char[][] ogBoard) {
		TreeNodeAlt.ogBoard = ogBoard;
	}
}
