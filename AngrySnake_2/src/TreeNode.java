import java.util.ArrayList;

public class TreeNode {

	private boardVal data;
	private TreeNode parent;
	private ArrayList<TreeNode> children;
	private char[][] baseField;

	// Okay this is probably getting me sued
	// The first constructor creates the root and the first set of children
	// based on the outside defined start positions and obstacles and by calling
	// possibleMoves

	// possibleMoves not only creates new nodes but creates new nodes by calling a
	// constructor for said nodes which again calls possibleMoves

	// This happens until there are no more new nodes to be made
	// Basically the moment you call the first constructor once
	// you create EVERYTHING, so it's not even a real data structure anymore
	// I created a monster.
	
	// So to cut it short, this shit needs to be restructured
	// also right now it's implementation into the game field is total ass
	// and the odds of this running first try are like non existent
	
	// Further, the tree needs to be re-created after every turn (if depth is <24)
	// which needs to be called inside computerAI and not game field holy fuck
	// give me a break I don't even know who I am anymore

	public TreeNode(boardVal basePos, char[][] field, int depth) {
		this.data = basePos;
		this.baseField = field;
		this.children = possibleMoves(field, basePos, depth);
	}

	public TreeNode(boardVal data, int depth) {
		this.data = data;
		char[][] nodeBoard = reconstructBoard(this);
		this.children = possibleMoves(nodeBoard, data, depth);
	}

	private char[][] reconstructBoard(TreeNode node) {
		char[][] field = getBaseField();
		return reconstructBoard(field, node);
	}

	private char[][] reconstructBoard(char[][] field, TreeNode node) {
		if (isRoot()) {
			return field;
		} else {
			if (node.getData().isP1()) {
				field[node.getData().getX1()][node.getData().getY1()] = '#';
			} else {
				field[node.getData().getX2()][node.getData().getY2()] = '#';
			}
			field[node.getData().getZ1()][node.getData().getZ2()] = '#';
			return reconstructBoard(field, node.parent);
		}
	}

	private ArrayList<TreeNode> possibleMoves(char[][] field, boardVal pos, int depth) {
		ArrayList<TreeNode> possibleMoves = new ArrayList<TreeNode>();
		if (depth > 0) { // ?
			if (pos.isP1()) {
				// top
				if (field[pos.getX1()][(pos.getY1() + 1) % 7] == ' ') {
					// top
					if (field[pos.getX1()][(pos.getY1() + 2) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), ((pos.getY1() + 2) % 7), pos.getX2(), pos.getY2(),
								pos.getX1(), (pos.getY1() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() - 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
				// down
				if (field[pos.getX1()][(pos.getY1() - 1) % 7] == ' ') {
					// down
					if (field[pos.getX1()][(pos.getY1() - 2) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), ((pos.getY1() - 2) % 7), pos.getX2(), pos.getY2(),
								pos.getX1(), (pos.getY1() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() - 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
				// right
				if (field[(pos.getX1() + 1) % 7][pos.getY1()] == ' ') {
					// right
					if (field[(pos.getX1() + 2) % 7][pos.getY1()] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 2) % 7), pos.getY1(), pos.getX2(), pos.getY2(),
								(pos.getX1() + 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() + 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() + 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
				// left
				if (field[(pos.getX1() - 1) % 7][pos.getY1()] == ' ') {
					// left
					if (field[(pos.getX1() - 2) % 7][pos.getY1()] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() - 2) % 7), pos.getY1(), pos.getX2(), pos.getY2(),
								(pos.getX1() - 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() - 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() - 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() - 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
			} else {
				// top
				if (field[pos.getX2()][(pos.getY2() + 1) % 7] == ' ') {
					// top
					if (field[pos.getX2()][(pos.getY2() + 2) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX2(), ((pos.getY2() + 2) % 7), pos.getX2(), pos.getY2(),
								pos.getX2(), (pos.getY2() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() - 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
				// down
				if (field[pos.getX2()][(pos.getY2() - 1) % 7] == ' ') {
					// down
					if (field[pos.getX2()][(pos.getY2() - 2) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX2(), ((pos.getY2() - 2) % 7), pos.getX2(), pos.getY2(),
								pos.getX2(), (pos.getY2() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() - 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
				// right
				if (field[(pos.getX2() + 1) % 7][pos.getY2()] == ' ') {
					// right
					if (field[(pos.getX2() + 2) % 7][pos.getY2()] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 2) % 7), pos.getY2(), pos.getX2(), pos.getY2(),
								(pos.getX2() + 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() + 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() + 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
				// left
				if (field[(pos.getX2() - 1) % 7][pos.getY2()] == ' ') {
					// left
					if (field[(pos.getX2() - 2) % 7][pos.getY2()] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() - 2) % 7), pos.getY2(), pos.getX2(), pos.getY2(),
								(pos.getX2() - 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() - 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() - 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() - 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						n.parent = this;
						possibleMoves.add(n);
					}
				}
			}
		} 
		return possibleMoves;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public int getLevel() {
		if (this.isRoot()) {
			return 0;
		} else {
			return parent.getLevel() + 1;
		}
	}

	public boardVal getData() {
		return data;
	}

	public void setData(boardVal data) {
		this.data = data;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}

	public char[][] getBaseField() {
		return baseField;
	}

	public void setBaseField(char[][] baseField) {
		this.baseField = baseField;
	}
}