package implementations;

import java.util.ArrayList;

import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>>
{
	private BSTreeNode<E> head;
	
	public BSTree() {
		head = null;
	}
	
	public BSTree(E value) {
		head = new BSTreeNode<E>();
		head.setElement(value);
	}
	
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if (head == null) throw new NullPointerException();
		return head;
	}
	
	// Recursive way to grab height
	private int heightGrab(int height, BSTreeNode<E> node) {
		if (node == null) return height;
		int newheight = height + 1;
		
		int leftheight = heightGrab(newheight, node.getLeft());
		int rightheight = heightGrab(newheight, node.getRight());
		
		if (leftheight > rightheight) return leftheight;
		else return rightheight;
	}
	
	public int getHeight() {
		return heightGrab(0, head);
	}
	
	// Recursive way to get size
	private int sizeGrab(BSTreeNode<E> node) {
		if (node == null)
            return 0;

        int left = sizeGrab(node.getLeft());
        int right = sizeGrab(node.getRight());

        return left + right + 1;
	}
	
	public int size() {
		if (head == null) return 0;
		return sizeGrab(head);
	}
	
	public boolean isEmpty() {
		return (head == null);
	}
	
	public void clear() {
		head = null;
	}
	
	// Recursive way to find item
	private boolean checkContains(E entry, BSTreeNode<E> node) {
		if (node == null) return false;
		if (node.getElement() == entry) return true;
		return checkContains(entry, node.getLeft()) || checkContains(entry, node.getRight());
	}
	
	public boolean contains( E entry ) throws NullPointerException {
		if (entry == null) throw new NullPointerException();
		return checkContains(entry, head);
	}
	
	// Recursive way to retrive item
	private BSTreeNode<E> checkSearch(E entry, BSTreeNode<E> node) {
		if (node == null) return null;
		if (node.getElement() == entry) return node;
		
		BSTreeNode<E> leftsearch = checkSearch(entry, node.getLeft());
		BSTreeNode<E> rightsearch = checkSearch(entry, node.getRight());
		if (leftsearch != null) return leftsearch;
		if (rightsearch != null) return rightsearch;
		return null;
	}
	
	public BSTreeNode<E> search( E entry ) throws NullPointerException {
		if (entry == null) throw new NullPointerException();
		return checkSearch(entry, head);
	}
	
	private boolean addLogic( E newEntry, BSTreeNode<E> node ) {
		int compare = newEntry.compareTo(node.getElement());
		if (compare < 0) {
			if (node.getLeft() == null) {
				BSTreeNode<E> newNode = new BSTreeNode<E>();
				newNode.setElement(newEntry);
				node.setLeft(newNode);
				return true;
			} else {
				return addLogic(newEntry, node.getLeft());
			}
		}
		if (compare > 0) {
			if (node.getRight() == null) {
				BSTreeNode<E> newNode = new BSTreeNode<E>();
				newNode.setElement(newEntry);
				node.setRight(newNode);
				return true;
			} else {
				return addLogic(newEntry, node.getRight());
			}
		}
		return false;
	}
	
	public boolean add( E newEntry ) throws NullPointerException {
		if (newEntry == null) throw new NullPointerException();
		if (head == null) {
			head = new BSTreeNode<E>();
			head.setElement(newEntry);
		}
		return addLogic(newEntry, head);
	}
	
	public BSTreeNode<E> removeMin() {
		if (head == null) return null;
		BSTreeNode<E> parentNode = head;
		BSTreeNode<E> node = parentNode.getLeft();

		while (true) {
			if (node.getLeft() == null) {
				parentNode.setLeft(null);
				return node;
			}
			parentNode = node;
			node = node.getLeft();
		}
	}
	
	public BSTreeNode<E> removeMax() {
		if (head == null) return null;
		BSTreeNode<E> parentNode = head;
		BSTreeNode<E> node = parentNode.getRight();

		while (true) {
			if (node.getRight() == null) {
				parentNode.setRight(null);
				return node;
			}
			parentNode = node;
			node = node.getRight();
		}
	}
	
	private void inorderGrab(ArrayList<E> list, BSTreeNode<E> node) {
        if (node == null)
            return;
        inorderGrab(list, node.getLeft());
        list.add(node.getElement());
        inorderGrab(list, node.getRight());
	}
	
	public Iterator<E> inorderIterator() {
		ArrayList<E> list = new ArrayList<E>();
		inorderGrab(list, head);
		return list.iterator();
	}
	
}
