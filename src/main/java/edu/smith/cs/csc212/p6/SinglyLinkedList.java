package edu.smith.cs.csc212.p6;

import java.util.Iterator;
import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;
import edu.smith.cs.csc212.p6.errors.RanOutOfSpaceError;

/**
 * 
 * Create Singly Linked List
 * Create Node start as reference will use later
 *
 * @param <T> - what type of items in list
 */
public class SinglyLinkedList<T> implements P6List<T>, Iterable<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	/**
	 * Deletes item at index 0 AKA front of list
	 * 
	 * O(1)-maintain constant rate regardless of amt data
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.next;
		return before;
	}

	/**
	 * Deletes last item of list
	 * 
	 * O(n)-indicate changing rate depending on amt data
	 * 
	 * If there is only one item in the list, will simply remove that item
	 * Otherwise, will loop through to item to item before last value and set last
	 * item to null in order to remove
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeBack() {
		checkNotEmpty();
		if (size()==1) {
			T casper=start.value;
			start=null;
			return casper;
		}
		else {
			for (Node<T> current = start; current != null; current = current.next) {
				if (current.next.next==null) {
					T groot=current.next.value;
					current.next=null;
					return groot;
				}
			}
		}
		return null;
	}

	/**
	 * Removes item at index(int index)
	 * 
	 * O(n)-indicate some change in growth of function, speed proportional to amt data
	 * 
	 * If only one value in list, will remove that item
	 * Otherwise, loop through list until item before item to be deleted. Delete
	 * links and create new link in order to delete desired item. 
	 * 
	 * Ex: a -->b-->c-->null becomes a-->c
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 * @param index a number from 0 to size (excluding size).
	 * @throws BadIndexError  if the index does not exist.
	 */
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		T removed = getIndex(index);
		if (size()==1) {
			T lonely=start.value;
			start=null;
			return lonely;
		}if (index==0) {
			start=start.next;
		}
		else {
			int gl=0;
			Node<T>current=start;
			for (gl=index-1; gl>0; gl--) {
				if(current.next!=null) {
					current=current.next;
				}
				else {
					current.next=null;
				}
			}
			current.next=current.next.next;
		}
		return removed;
	}

	/**
	 * Adds item to front 
	 * 
	 * O(1)-rate constant regardless amt data
	 * 
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	/**
	 * Add an item to the back of this list. 
	 * 
	 * O(n)-indicate rate change proportionally to amt data
	 * 
	 * If nothing in list, just add to front
	 * Otherwise, go to right before last item and add new Node there
	 * 
	 * @param item the data to add to the list.
	 * @throws RanOutOfSpaceError if number of items in list is over array length
	 */
	@Override
	public void addBack(T item) {
		if (size()==0){
			addFront(item);
		}else {
			Node<T> current=start;
			for (current = start; current.next != null; current = current.next) {	
			}
		current.next = new Node<T>(item,null);
	}
	}

	/**
	 *  Add an item before ``index`` in this list. 
	 * 
	 * O(n)-indicate some change in growth of function, speed proportional to amt data
	 * 
	 * Go to item before where want to insert and make next item new Node
	 * 
	 * @param item  the data to add to the list.
	 * @param index the index at which to add the item.
	 * @throws RanOutOfSpaceError if number items is over array length
	 */
	@Override
	public void addIndex(T item, int index) {
		Node<T> current=start;
		Node<T> boo = new Node<T>(item, null);
		if (index<0 || index>size()) {
			throw new BadIndexError();
		}
		if(index==0){
			addFront(item);
		}else if(index==size()) {
			addBack(item);
		}
		else {
			for (int gi=0; gi<index-1;gi++) {
					current=current.next;
				}
			boo.next=current.next;
			current.next=boo;
			}
		}

	/**
	 * Get the first item in the list.
	 * 
	 * O(n)-change proportional to amt data
	 * 
	 * @return the item.
	 * @throws EmptyListError
	 */
	@Override
	public T getFront() {
		if (size()==0) {
			throw new EmptyListError();
		}
		return start.value;
	}

	/**
	 * Get the last item in the list.
	 * 
	 * O(n)-change proportional to amt data
	 * 
	 * If size is one, just get that one item
	 * Otherwise, loop through to item before last and get last value with current.next
	 * 
	 * @return the item.
	 * @throws EmptyListError
	 */
	@Override
	public T getBack() {
		checkNotEmpty();
		if (size()==1) {
			T joe=start.value;
			return joe;
		}
		else {
			for (Node<T> current = start; current != null; current = current.next) {
				if (current.next.next==null) {
					T groot=current.next.value;
					return groot;
				}
			}
		}
		return null;
	}

	/**
	 * Find the index-th element of this list.
	 * 
	 * O(1)-no matter how much data, will execute at constant time
	 * 
	 * @param index a number from 0 to size, excluding size.
	 * @return the value at index.
	 * @throws BadIndexError if the index does not exist.
	 */
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> current = start; current != null; current = current.next) {
			if (at == index) {
				return current.value;
			}
			at++;
		}
		throw new BadIndexError();
	}
	
	/**
	 * Calculate the size of the list.
	 * 
	 * O(1)-no matter how much data, will execute at constant time
	 * 
	 * Create counter and loop through list to count
	 * 
	 * @return the length of the list, or zero if empty.
	 */
	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	/**
	 * This is true if the list is empty. Looks at fill.
	 * 
	 * @return true if the list is empty.
	 */
	@Override
	public boolean isEmpty() {
		if (this.size() ==0)
			return true;
		else {
		return false;
		}
	}
	
	@Override
	public boolean isFull() {
		if (this.size()==size()) 
			return true;
		else {
			return false;
		}
	}
	
	/**
	 * Helper method to throw the right error for an empty state.
	 */
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * I'm providing this class so that SinglyLinkedList can be used in a for loop
	 * for {@linkplain ChunkyLinkedList}. This Iterator type is what java uses for
	 * {@code for (T x : list) { }} lops.
	 * 
	 * @author jfoley
	 *
	 * @param <T>
	 */
	private static class Iter<T> implements Iterator<T> {
		/**
		 * This is the value that walks through the list.
		 */
		Node<T> current;

		/**
		 * This constructor details where to start, given a list.
		 * This is the first statement in a for loop.
		 * @param list - the SinglyLinkedList to iterate or loop over.
		 */
		public Iter(SinglyLinkedList<T> list) {
			this.current = list.start;
		}

		/**
		 * Returns true if there is a "next" item -- the middle statement of the for loop.
		 */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * Returns the current item and moves to the next, kind of like the third statement and the body of the for loop.
		 */
		@Override
		public T next() {
			T found = current.value;
			current = current.next;
			return found;
		}
	}
	
	/**
	 * Implement iterator() so that {@code SinglyLinkedList} can be used in a for loop.
	 * @return an object that understands "next()" and "hasNext()".
	 */
	public Iterator<T> iterator() {
		return new Iter<>(this);
	}
}
