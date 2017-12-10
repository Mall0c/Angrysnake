import java.util.ArrayList;

public class TreeNode {

	private boardVal data;
	private TreeNode parent;
	private ArrayList<TreeNode> children;

	private int depth;
	private char[][] ogBoard;

	// this is the main constructor which builds our root
	// it requires the already filled board to generate a correct tree
	// for now I assume that the information inside the root are not important
	// since they are already included in our board
	// the depth is required (and should come from outside)
	// to generate a depth-height (sub)tree

	public TreeNode(boardVal data, int depth, char[][] board) {
		setData(data);
		setDepth(depth);
		setOgBoard(board);
		setChildren(possibleMoves(getOgBoard(), getData(), getDepth()));
		buildTree();
	}

	public TreeNode(boardVal data, int depth) {
		setData(data);
		setDepth(depth);
	}

	// Okay, here is how I imagine this works (if it works at all)
	// If you have more than zero children it means you have more than zero options,
	// so you address each of your children and tell them to set their own children
	// (generate their move options)
	
	// every child has to generate its own board via the reconstruct function
	// this should save a good amount of memory but obviously costs time
	// the possibleMoves method (which generates the children) keeps track of the depth
	// basically if depth == 0 the getChildren().size() will equal zero and our buildTree function stops
	
	// obviously this shit is recursive

	public void buildTree() {
		if (getChildren().size() > 0) {
			for (int i = 0; i < getChildren().size(); i++) {
				getChildren().get(i).setChildren(possibleMoves(reconstructBoard(this), getData(), getDepth())); 
				getChildren().get(i).buildTree();
			}
		}
	}

	// reconstructBoard takes the ogBoard and the current node
	// it adds the XY and ZZ values into the board 
	// then it opens itself with the parent node and the changed field 
	// until you reach root - this way the whole tree has to only to store
	// a single board
	
	private char[][] reconstructBoard(TreeNode node) {
		char[][] field = getOgBoard();
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
	
	// generates the possibleMoves for a single node and saves them as arrayList
	// there isn't much happening here really
	// important is that the boolean for player1 gets changed every time by adding a (!)

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
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() - 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
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
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() - 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
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
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() + 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX1() + 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() + 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
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
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() - 1) % 7), ((pos.getY1() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() - 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX1() - 1) % 7][(pos.getY1() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX1() + 1) % 7), ((pos.getY1() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX1() - 1) % 7, pos.getY1(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
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
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() - 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() + 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
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
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() - 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), pos.getX2(), (pos.getY2() - 1) % 7, 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
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
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() + 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX2() + 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() + 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
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
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() + 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() - 1) % 7), ((pos.getY2() + 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() - 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX2() - 1) % 7][(pos.getY2() - 1) % 7] == ' ') {
						boardVal nVal = new boardVal(((pos.getX2() + 1) % 7), ((pos.getY2() - 1) % 7), pos.getX2(),
								pos.getY2(), (pos.getX2() - 1) % 7, pos.getY2(), 0.0, !pos.isP1());
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
			}
		}
		for (int i = 0; i < possibleMoves.size(); i++) {
			possibleMoves.get(i).parent = this;
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

	public char[][] getOgBoard() {
		return ogBoard;
	}

	public void setOgBoard(char[][] baseField) {
		this.ogBoard = baseField;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}