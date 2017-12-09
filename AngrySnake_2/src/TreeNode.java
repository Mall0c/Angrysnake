import java.util.ArrayList;

public class TreeNode<T> {

	public T data;
	public TreeNode<T> parent;
	public ArrayList<TreeNode<T>> children;

	public TreeNode(T data) {
		this.data = data;
		this.children = new ArrayList<TreeNode<T>>(9);
	}
	
	public TreeNode<T> addChild(T child) {
		TreeNode<T> childNode = new TreeNode<T>(child);
		childNode.parent = this;
		this.children.add(childNode);
		return childNode;
	}
	
	private TreeNode<T> generateTree() {
		
	}
	
	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}
	
	public int getLevel() {
		if (this.isRoot()) {
			return 0;
		} else {
			return parent.getLevel() + 1;
		}
	}
}