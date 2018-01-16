import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeNode {

	private boardVal data;
	private TreeNode parent;
	private ArrayList<TreeNode> children;
	private final static boolean DEBUG = false;
	private int depth;
	private static char[][] ogBoard;
	private static boolean isFirst;

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
		if (parent == null)
			setFirst(data.isP1());
		buildTree();
		if (children.isEmpty()) {
			if (DEBUG)
				System.out.println("LOOOOL");
		}
		if (children.isEmpty() && (data.isP1() && data.SP1Maulwuerfe > 0 || !data.isP1() && data.SP2Maulwuerfe > 0)) {
			this.maulthrows();
		}
		this.foo();

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
	// the possibleMoves method (which generates the children) keeps track of the
	// depth
	// basically if depth == 0 the getChildren().size() will equal zero and our
	// buildTree function stops

	// obviously this shit is recursive

	private void buildTree() {
		if (this.depth > 0) {
			for (int i = 0; i < getChildren().size(); i++) {
				TreeNode childNode = getChildren().get(i);
				childNode.setChildren(childNode.possibleMoves(reconstructBoard(childNode), childNode.getData(),
						childNode.getDepth()));
				getChildren().get(i).buildTree();
			}
		}
	}

	private void maulthrows() {
		char[][] field = reconstructBoard(this);
		List<TreeNode> possibleMoves = new ArrayList<>();
		boardVal pos = data;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (field[i][j] == ' ') {
					boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), pos.getX2(), pos.getY2(), i, j,
							pos.isP1() ? false : true, pos.isP1() ? pos.getSP1Maulwuerfe() : pos.getSP1Maulwuerfe() - 1,
							pos.isP1() ? pos.getSP2Maulwuerfe() - 1 : pos.getSP2Maulwuerfe());
					nVal.setMaulthrow(true);
					TreeNode n = new TreeNode(nVal, depth - 1);
					n.parent = this;
					possibleMoves.add(n);
				}
			}
		}
		Random rn = new Random();
		this.children.add(possibleMoves.get(rn.nextInt(possibleMoves.size())));
	}

	// reconstructBoard takes the ogBoard and the current node
	// it adds the XY and ZZ values into the board
	// then it opens itself with the parent node and the changed field
	// until you reach root - this way the whole tree has to only to store
	// a single board

	private char[][] reconstructBoard(TreeNode node) {
		char[][] field = new char[7][7];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				field[i][j] = getOgBoard()[i][j];
			}
		}
		return reconstructBoard(field, node);
	}

	private char[][] reconstructBoard(char[][] field, TreeNode node) {
		if (node.isRoot()) {
			return field;
		} else {
			if (!node.getData().isMaulthrow()) {
				if (node.getData().isP1()) {
					field[node.getData().getX1()][node.getData().getY1()] = '#';
				} else {
					field[node.getData().getX2()][node.getData().getY2()] = '#';
				}
			}
			field[node.getData().getZ1()][node.getData().getZ2()] = '#';
			return reconstructBoard(field, node.parent);
		}
	}

	private void foo() {
		if (this.depth > 0 && this.children != null) {
			for (int i = 0; i < this.children.size(); i++) {
				TreeNode temp = this.children.get(i);
				temp.foo();
			}
			int[] heurVals = new int[this.children.size()];
			for (int i = 0; i < heurVals.length; i++) {
				heurVals[i] = this.children.get(i).data.heurVal;
			}
			if (isFirst()) {
				if (this.data.isP1()) {
					int max = Integer.MIN_VALUE;
					for (int i : heurVals)
						if (max < i)
							max = i;
					this.data.heurVal = max;
				} else {
					int min = Integer.MAX_VALUE;
					for (int i : heurVals)
						if (min > i)
							min = i;
					this.data.heurVal = min;
				}
			} else {
				if (this.data.isP1()) {
					int min = Integer.MAX_VALUE;
					for (int i : heurVals)
						if (min > i)
							min = i;
					this.data.heurVal = min;
				} else {
					int max = Integer.MIN_VALUE;
					for (int i : heurVals)
						if (max < i)
							max = i;
					this.data.heurVal = max;
				}
			}
		}
	}

	// if there are more than 1 children with the same heurVal to be chosen, this
	// method chooses a random one
	// @return: the move to be done, e.g. A5B5
	public String getMove() {
		int heurVal = this.getData().heurVal;
		int i = 0;
		String answer = "";
		ArrayList<TreeNode> enemy = enemyMoves(getOgBoard(), getData(), getDepth());
		// check if a Mowl shall be set against the enemy
		if (analyzeEnemyMoves(enemy)) {
			// against player 2
			if (data.isP1()) {
				if (data.getSP1Maulwuerfe() > 0) {
					answer += (char) (enemy.get(0).getData().getZ1() + 65);
					answer += (char) (enemy.get(0).getData().getZ2() + 49);
					data.setSP1Maulwuerfe(data.getSP1Maulwuerfe() - 1);
					return answer;
				}
				// against player 1
			} else {
				if (data.getSP2Maulwuerfe() > 0) {
					answer += (char) (enemy.get(0).getData().getZ1() + 65);
					answer += (char) (enemy.get(0).getData().getZ2() + 49);
					data.setSP2Maulwuerfe(data.getSP2Maulwuerfe() - 1);
					return answer;
				}
			}
		}
		if (analyzeEnemyMovesDiagonal(enemy)) {
			// against player 2
			if (data.isP1()) {
				if (data.getSP1Maulwuerfe() > 0) {
					answer += (char) (enemy.get(0).getData().getX2() + 65);
					answer += (char) (enemy.get(0).getData().getY2() + 49);
					data.setSP1Maulwuerfe(data.getSP1Maulwuerfe() - 1);
					return answer;
				}
				// against player 1
			} else {
				if (data.getSP2Maulwuerfe() > 0) {
					answer += (char) (enemy.get(0).getData().getX1() + 65);
					answer += (char) (enemy.get(0).getData().getY1() + 49);
					data.setSP2Maulwuerfe(data.getSP2Maulwuerfe() - 1);
					return answer;
				}
			}
		}
		List<boardVal> valList = new ArrayList<>();
		for (i = 0; i < this.getChildren().size(); i++) {
			if (this.getChildren().get(i).getData().heurVal == heurVal) {
				boardVal x = this.getChildren().get(i).getData();
				valList.add(x);
			}
		}
//		if (getDepth() == 2) {
//			if (data.p1) {
//				if (!data.isMaulthrow()) {
//					TreeNode randomChild = this.getChildren()
//							.get((int) (Math.random() * this.getChildren().size()));
//					answer += (char) (randomChild.getData().getZ1() + 65);
//					answer += (char) (randomChild.getData().getZ2() + 49);
//					answer += (char) (randomChild.getData().getX2() + 65);
//					answer += (char) (randomChild.getData().getY2() + 49);
//					return answer;
//				} else {
//					boardVal x = valList.get((int) (Math.random() * valList.size()));
//					answer += (char) (x.z1 + 65);
//					answer += (char) (x.z2 + 49);
//					return answer;
//				}
//			} else {
//				if (!data.isMaulthrow()) {
//					TreeNode randomChild = this.getChildren()
//							.get((int) (Math.random() * this.getChildren().size()));
//					answer += (char) (randomChild.getData().getZ1() + 65);
//					answer += (char) (randomChild.getData().getZ2() + 49);
//					answer += (char) (randomChild.getData().getX1() + 65);
//					answer += (char) (randomChild.getData().getY1() + 49);
//					return answer;
//				} else {
//					boardVal x = valList.get((int) (Math.random() * valList.size()));
//					answer += (char) (x.z1 + 65);
//					answer += (char) (x.z2 + 49);
//					return answer;
//				}
//			}
//		}
		if (heurVal == 0) {
			if (DEBUG)
				System.out.println();
		}

		if (valList.size() == 1) {
			boardVal x = valList.get(0);
			if (x.p1) {
				if (!x.isMaulthrow()) {
					if (DEBUG)
						System.out.printf("z1:%d z2:%d x:%d y:%d heurVal:%d auswahl: %d \n", x.z1, x.z2, x.x1, x.y1,
								x.heurVal, valList.size());
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
					answer += (char) (x.x1 + 65);
					answer += (char) (x.y1 + 49);
				} else {
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
				}
			} else {
				if (!x.isMaulthrow()) {
					if (DEBUG)
						System.out.printf("z1:%d z2:%d x:%d y:%d heurVal:%d auswahl: %d \n", x.z1, x.z2, x.x2, x.y2,
								x.heurVal, valList.size());
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
					answer += (char) (x.x2 + 65);
					answer += (char) (x.y2 + 49);
				} else {
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
				}
			}
			return answer;
		} else {
			boardVal x = valList.get((int) (Math.random() * valList.size()));
			if (x.p1) {
				if (!x.isMaulthrow()) {
					if (DEBUG)
						System.out.printf("z1:%d z2:%d x:%d y:%d heurVal:%d auswahl: %d \n", x.z1, x.z2, x.x1, x.y1,
								x.heurVal, valList.size());
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
					answer += (char) (x.x1 + 65);
					answer += (char) (x.y1 + 49);
				} else {
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
				}
			} else {
				if (!x.isMaulthrow()) {
					if (DEBUG)
						System.out.printf("z1:%d z2:%d x:%d y:%d heurVal:%d auswahl: %d \n", x.z1, x.z2, x.x2, x.y2,
								x.heurVal, valList.size());
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
					answer += (char) (x.x2 + 65);
					answer += (char) (x.y2 + 49);
				} else {
					answer += (char) (x.z1 + 65);
					answer += (char) (x.z2 + 49);
				}
			}
			return answer;
		}
	}

	// generates the possibleMoves for a single node and saves them as arrayList
	// there isn't much happening here really
	// important is that the boolean for player1 gets changed every time by adding a
	// (!)

	private ArrayList<TreeNode> possibleMoves(char[][] field, boardVal pos, int depth) {
		ArrayList<TreeNode> possibleMoves = new ArrayList<TreeNode>();
		if (depth >= 0) {
			if (!pos.isP1()) { // actually, this is the path the program takes if it's P1's turn
				// top
				if (field[pos.getX1()][(pos.getY1() + 1 + 7) % 7] == ' ') {
					// top
					if (field[pos.getX1()][(pos.getY1() + 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), (pos.getY1() + 2 + 7) % 7, pos.getX2(), pos.getY2(),
								pos.getX1(), (pos.getY1() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
				// down
				if (field[pos.getX1()][(pos.getY1() - 1 + 7) % 7] == ' ') {
					// down
					if (field[pos.getX1()][(pos.getY1() - 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), (pos.getY1() - 2 + 7) % 7, pos.getX2(), pos.getY2(),
								pos.getX1(), (pos.getY1() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
				// right
				if (field[(pos.getX1() + 1 + 7) % 7][pos.getY1()] == ' ') {
					// right
					if (field[(pos.getX1() + 2 + 7) % 7][pos.getY1()] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 2 + 7) % 7, pos.getY1(), pos.getX2(), pos.getY2(),
								(pos.getX1() + 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() + 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() + 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
				// left
				if (field[(pos.getX1() - 1 + 7) % 7][pos.getY1()] == ' ') {
					// left
					if (field[(pos.getX1() - 2 + 7) % 7][pos.getY1()] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 2 + 7) % 7, pos.getY1(), pos.getX2(), pos.getY2(),
								(pos.getX1() - 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() - 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() - 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
			} else {
				// top
				if (field[pos.getX2()][(pos.getY2() + 1 + 7) % 7] == ' ') {
					// top
					if (field[pos.getX2()][(pos.getY2() + 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), pos.getX2(), (pos.getY2() + 2 + 7) % 7,
								pos.getX2(), (pos.getY2() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, pos.getX2(), (pos.getY2() + 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, pos.getX2(), (pos.getY2() + 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
				// down
				if (field[pos.getX2()][(pos.getY2() - 1 + 7) % 7] == ' ') {
					// down
					if (field[pos.getX2()][(pos.getY2() - 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), pos.getX2(), (pos.getY2() - 2 + 7) % 7,
								pos.getX2(), (pos.getY2() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, pos.getX2(), (pos.getY2() - 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, pos.getX2(), (pos.getY2() - 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
				// right
				if (field[(pos.getX2() + 1 + 7) % 7][pos.getY2()] == ' ') {
					// right
					if (field[(pos.getX2() + 2 + 7) % 7][pos.getY2()] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 2 + 7) % 7, pos.getY2(),
								(pos.getX2() + 1 + 7) % 7, pos.getY2(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, (pos.getX2() + 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, (pos.getX2() + 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
				// left
				if (field[(pos.getX2() - 1 + 7) % 7][pos.getY2()] == ' ') {
					// left
					if (field[(pos.getX2() - 2 + 7) % 7][pos.getY2()] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 2 + 7) % 7, pos.getY2(),
								(pos.getX2() - 1 + 7) % 7, pos.getY2(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// top
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, (pos.getX2() - 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
					// down
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, (pos.getX2() - 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						possibleMoves.add(n);
					}
				}
			}
		}
		for (int i = 0; i < possibleMoves.size(); i++) {
			possibleMoves.get(i).parent = this;
		}

		// Okay the following is important
		// Imagine we set the depth to 2
		// we start at root and create its children, the children save a memory of 1
		// then the children create new children of their own which have a depth of 0
		// If we now want to evaluate the children values - we can't!
		// the evaluation depends on the possible options(!) of said children
		// so we have to add another round of children (which I don't see a way around
		// sadly)
		// also not sure whether we to compare options further but I guess not(?)
		if (depth == 0) {
			if (pos.isP1()) {
				this.getData().setHeurVal(possibleMoves.size());
			} else {
				this.getData().setHeurVal(possibleMoves.size());
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

	public char[][] getOgBoard() {
		return ogBoard;
	}

	public void setOgBoard(char[][] baseField) {
		ogBoard = baseField;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public static boolean isFirst() {
		return isFirst;
	}

	public static void setFirst(boolean isFirst) {
		TreeNode.isFirst = isFirst;
	}

	// analyze the possible moves of the enemy. If the coordinates are the same it's
	// nice for a fking maulwurf!
	// returns when it's recommended to set the motherfucker
	private boolean analyzeEnemyMoves(ArrayList<TreeNode> enemyMoves) {
		if (enemyMoves.size() <= 3 && enemyMoves.size() > 1) {
			for (int i = 0; i < (enemyMoves.size() - 1); i++) {
				if (enemyMoves.get(i).getData().getZ1() != enemyMoves.get(i + 1).getData().getZ1()) {
					return false;
				}
				if (enemyMoves.get(i).getData().getZ2() != enemyMoves.get(i + 1).getData().getZ2()) {
					return false;
				}
			}
			return true;
		}
		if (enemyMoves.size() == 1) {
			return true;
		}
		return false;

	}
	
	private boolean analyzeEnemyMovesDiagonal(ArrayList<TreeNode> enemyMoves) {
		if (enemyMoves.size() == 2) {
			for (int i = 0; i < (enemyMoves.size() - 1); i++) {
				if (this.getData().isP1()) {
					if (enemyMoves.get(i).getData().getX1() != enemyMoves.get(i + 1).getData().getX1()) {
						return false;
					}
					if (enemyMoves.get(i).getData().getY1() != enemyMoves.get(i + 1).getData().getY1()) {
						return false;
					}	
				} else {
					if (enemyMoves.get(i).getData().getX2() != enemyMoves.get(i + 1).getData().getX2()) {
						return false;
					}
					if (enemyMoves.get(i).getData().getY2() != enemyMoves.get(i + 1).getData().getY2()) {
						return false;
					}
				}
			}
			return true;
		}
		return false;

	}

	private ArrayList<TreeNode> enemyMoves(char[][] field, boardVal pos, int depth) {
		ArrayList<TreeNode> enemyMoves = new ArrayList<TreeNode>();
		if (depth >= 0) {
			if (pos.isP1()) { // actually, this is the path the program takes if it's P1's turn
				// top
				if (field[pos.getX1()][(pos.getY1() + 1 + 7) % 7] == ' ') {
					// top
					if (field[pos.getX1()][(pos.getY1() + 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), (pos.getY1() + 2 + 7) % 7, pos.getX2(), pos.getY2(),
								pos.getX1(), (pos.getY1() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
				// down
				if (field[pos.getX1()][(pos.getY1() - 1 + 7) % 7] == ' ') {
					// down
					if (field[pos.getX1()][(pos.getY1() - 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), (pos.getY1() - 2 + 7) % 7, pos.getX2(), pos.getY2(),
								pos.getX1(), (pos.getY1() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// right
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// left
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), pos.getX1(), (pos.getY1() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
				// right
				if (field[(pos.getX1() + 1 + 7) % 7][pos.getY1()] == ' ') {
					// right
					if (field[(pos.getX1() + 2 + 7) % 7][pos.getY1()] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 2 + 7) % 7, pos.getY1(), pos.getX2(), pos.getY2(),
								(pos.getX1() + 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// top
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() + 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// down
					if (field[(pos.getX1() + 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() + 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() + 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
				// left
				if (field[(pos.getX1() - 1 + 7) % 7][pos.getY1()] == ' ') {
					// left
					if (field[(pos.getX1() - 2 + 7) % 7][pos.getY1()] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 2 + 7) % 7, pos.getY1(), pos.getX2(), pos.getY2(),
								(pos.getX1() - 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// top
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() + 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() - 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// down
					if (field[(pos.getX1() - 1 + 7) % 7][(pos.getY1() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal((pos.getX1() - 1 + 7) % 7, (pos.getY1() - 1 + 7) % 7, pos.getX2(),
								pos.getY2(), (pos.getX1() - 1 + 7) % 7, pos.getY1(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
			} else {
				// top
				if (field[pos.getX2()][(pos.getY2() + 1 + 7) % 7] == ' ') {
					// top
					if (field[pos.getX2()][(pos.getY2() + 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), pos.getX2(), (pos.getY2() + 2 + 7) % 7,
								pos.getX2(), (pos.getY2() + 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, pos.getX2(), (pos.getY2() + 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, pos.getX2(), (pos.getY2() + 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
				// down
				if (field[pos.getX2()][(pos.getY2() - 1 + 7) % 7] == ' ') {
					// down
					if (field[pos.getX2()][(pos.getY2() - 2 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), pos.getX2(), (pos.getY2() - 2 + 7) % 7,
								pos.getX2(), (pos.getY2() - 1 + 7) % 7, !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// right
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, pos.getX2(), (pos.getY2() - 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// left
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, pos.getX2(), (pos.getY2() - 1 + 7) % 7, !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
				// right
				if (field[(pos.getX2() + 1 + 7) % 7][pos.getY2()] == ' ') {
					// right
					if (field[(pos.getX2() + 2 + 7) % 7][pos.getY2()] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 2 + 7) % 7, pos.getY2(),
								(pos.getX2() + 1 + 7) % 7, pos.getY2(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// top
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, (pos.getX2() + 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// down
					if (field[(pos.getX2() + 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() + 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, (pos.getX2() + 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
				// left
				if (field[(pos.getX2() - 1 + 7) % 7][pos.getY2()] == ' ') {
					// left
					if (field[(pos.getX2() - 2 + 7) % 7][pos.getY2()] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 2 + 7) % 7, pos.getY2(),
								(pos.getX2() - 1 + 7) % 7, pos.getY2(), !pos.isP1(), pos.SP1Maulwuerfe,
								pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// top
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() + 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() + 1 + 7) % 7, (pos.getX2() - 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
					// down
					if (field[(pos.getX2() - 1 + 7) % 7][(pos.getY2() - 1 + 7) % 7] == ' ') {
						boardVal nVal = new boardVal(pos.getX1(), pos.getY1(), (pos.getX2() - 1 + 7) % 7,
								(pos.getY2() - 1 + 7) % 7, (pos.getX2() - 1 + 7) % 7, pos.getY2(), !pos.isP1(),
								pos.SP1Maulwuerfe, pos.SP2Maulwuerfe);
						TreeNode n = new TreeNode(nVal, depth - 1);
						enemyMoves.add(n);
					}
				}
			}
		}
		return enemyMoves;
	}
}