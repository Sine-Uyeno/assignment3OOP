package implementations;

public class BSTreeNode<E>
{
	private E Element;
	private BSTreeNode<E> Left;
	private BSTreeNode<E> Right;
	
	public BSTreeNode() {
		Element = null;
		Left = null;
		Right = null;
	}
	
	// G&S for Data
	public E getElement() {
		return Element;
	}
	public void setElement(E val) {
		Element = val;
	}
	// G&S for Left Node
	public BSTreeNode<E> getLeft() {
		return Left;
	}
	public void setLeft(BSTreeNode<E> node) {
		Left = node;
	}
	// G&S for Right Node
	public BSTreeNode<E> getRight() {
		return Right;
	}
	public void setRight(BSTreeNode<E> node) {
		Right = node;
	}
}
