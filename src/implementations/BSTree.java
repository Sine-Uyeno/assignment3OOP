package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;
import java.util.ArrayList;

/**
 * <p>
 * The <code>BSTree</code> class is designed to be used as a basis for 
 * the BST data structure that will be developed in the CPRG304 F2025 class at
 * SAIT. The implementors of this class will be required to add all the
 * functionality.
 * </p>
 * 
 * @param <E> The type of elements this list holds.
 */

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>
{
	private BSTreeNode<E> root;
	private int nodeCount;

	/**
	 * Default constructor initializes empty tree.
	 */
	public BSTree() {
		root = null;
		nodeCount = 0;
	}

	/**
	 * Constructor that creates a tree with a single root element.
	 * @param initial the initial element to place at the root
	 */
	public BSTree(E initial) {
		if (initial == null) {
			root = null;
			nodeCount = 0;
		} else {
			root = new BSTreeNode<>();
			root.setElement(initial);
			nodeCount = 1;
		}
	}

	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if (root == null) {
			throw new NullPointerException("Tree is empty");
		}
		return root;
	}

	@Override
	public int getHeight() {
		return getHeightHelper(root);
	}

	/**
	 * Helper method to recursively calculate height of the tree.
	 * @param node the current node
	 * @return the height of the subtree rooted at node
	 */
	private int getHeightHelper(BSTreeNode<E> node) {
		if (node == null) {
			return 0;
		}
		return 1 + Math.max(getHeightHelper(node.getLeft()), getHeightHelper(node.getRight()));
	}

	@Override
	public int size() {
		return nodeCount;
	}

	@Override
	public boolean isEmpty() {
		return nodeCount == 0;
	}

	@Override
	public void clear() {
		root = null;
		nodeCount = 0;
	}

	@Override
	public boolean contains(E entry) throws NullPointerException {
		if (entry == null) {
			throw new NullPointerException("Entry cannot be null");
		}
		return search(entry) != null;
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException {
		if (entry == null) {
			throw new NullPointerException("Entry cannot be null");
		}
		return searchHelper(root, entry);
	}

	/**
	 * Helper method to recursively search for an element in the tree.
	 * @param node the current node
	 * @param entry the element to search for
	 * @return the node containing the element, or null if not found
	 */
	private BSTreeNode<E> searchHelper(BSTreeNode<E> node, E entry) {
		if (node == null) {
			return null;
		}
		
		int comparison = entry.compareTo(node.getElement());
		if (comparison == 0) {
			return node;
		} else if (comparison < 0) {
			return searchHelper(node.getLeft(), entry);
		} else {
			return searchHelper(node.getRight(), entry);
		}
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException {
		if (newEntry == null) {
			throw new NullPointerException("Entry cannot be null");
		}
		
		if (root == null) {
			root = new BSTreeNode<>();
			root.setElement(newEntry);
			nodeCount++;
			return true;
		}
		
		return addHelper(root, newEntry);
	}

	/**
	 * Helper method to recursively add an element to the tree.
	 * @param node the current node
	 * @param newEntry the element to add
	 * @return true if added successfully, false if duplicate
	 */
	private boolean addHelper(BSTreeNode<E> node, E newEntry) {
		int comparison = newEntry.compareTo(node.getElement());
		
		if (comparison == 0) {
			return false; // Duplicate, not added
		} else if (comparison < 0) {
			if (node.getLeft() == null) {
				BSTreeNode<E> newNode = new BSTreeNode<>();
				newNode.setElement(newEntry);
				node.setLeft(newNode);
				nodeCount++;
				return true;
			} else {
				return addHelper(node.getLeft(), newEntry);
			}
		} else {
			if (node.getRight() == null) {
				BSTreeNode<E> newNode = new BSTreeNode<>();
				newNode.setElement(newEntry);
				node.setRight(newNode);
				nodeCount++;
				return true;
			} else {
				return addHelper(node.getRight(), newEntry);
			}
		}
	}

	@Override
	public BSTreeNode<E> removeMin() {
		if (root == null) {
			return null;
		}
		// if root is the minimum
		if (root.getLeft() == null) {
			BSTreeNode<E> removed = root;
			root = root.getRight();
			nodeCount--;
			return removed;
		}

		BSTreeNode<E> parent = root;
		BSTreeNode<E> current = root.getLeft();
		while (current.getLeft() != null) {
			parent = current;
			current = current.getLeft();
		}
		// current is minimum
		BSTreeNode<E> removed = current;
		parent.setLeft(current.getRight());
		nodeCount--;
		return removed;
	}

	@Override
	public BSTreeNode<E> removeMax() {
		if (root == null) {
			return null;
		}
		// if root is the maximum
		if (root.getRight() == null) {
			BSTreeNode<E> removed = root;
			root = root.getLeft();
			nodeCount--;
			return removed;
		}

		BSTreeNode<E> parent = root;
		BSTreeNode<E> current = root.getRight();
		while (current.getRight() != null) {
			parent = current;
			current = current.getRight();
		}
		// current is maximum
		BSTreeNode<E> removed = current;
		parent.setRight(current.getLeft());
		nodeCount--;
		return removed;
	}

	@Override
	public Iterator<E> inorderIterator() {
		ArrayList<E> elements = new ArrayList<>();
		inorderTraversal(root, elements);
		return new BSTreeIterator(elements);
	}

	/**
	 * Helper method to perform in-order traversal.
	 * @param node the current node
	 * @param elements the list to store elements in order
	 */
	private void inorderTraversal(BSTreeNode<E> node, ArrayList<E> elements) {
		if (node != null) {
			inorderTraversal(node.getLeft(), elements);
			elements.add(node.getElement());
			inorderTraversal(node.getRight(), elements);
		}
	}

	@Override
	public Iterator<E> preorderIterator() {
		ArrayList<E> elements = new ArrayList<>();
		preorderTraversal(root, elements);
		return new BSTreeIterator(elements);
	}

	/**
	 * Helper method to perform pre-order traversal.
	 * @param node the current node
	 * @param elements the list to store elements in order
	 */
	private void preorderTraversal(BSTreeNode<E> node, ArrayList<E> elements) {
		if (node != null) {
			elements.add(node.getElement());
			preorderTraversal(node.getLeft(), elements);
			preorderTraversal(node.getRight(), elements);
		}
	}

	@Override
	public Iterator<E> postorderIterator() {
		ArrayList<E> elements = new ArrayList<>();
		postorderTraversal(root, elements);
		return new BSTreeIterator(elements);
	}

	/**
	 * Helper method to perform post-order traversal.
	 * @param node the current node
	 * @param elements the list to store elements in order
	 */
	private void postorderTraversal(BSTreeNode<E> node, ArrayList<E> elements) {
		if (node != null) {
			postorderTraversal(node.getLeft(), elements);
			postorderTraversal(node.getRight(), elements);
			elements.add(node.getElement());
		}
	}

	/**
	 * Private inner class implementing the Iterator interface.
	 */
	private class BSTreeIterator implements Iterator<E> {
		private ArrayList<E> elements;
		private int currentIndex;

		/**
		 * Constructor initializes the iterator with a copy of elements.
		 * @param elements the ArrayList of elements to iterate through
		 */
		public BSTreeIterator(ArrayList<E> elements) {
			this.elements = new ArrayList<>(elements);
			this.currentIndex = 0;
		}

		@Override
		public boolean hasNext() {
			return currentIndex < elements.size();
		}

		@Override
		public E next() throws java.util.NoSuchElementException {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException("No more elements in iterator");
			}
			return elements.get(currentIndex++);
		}
	}

}