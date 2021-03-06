package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

/**
 * This is a data structure that has an array inside each node of a Linked List.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyLinkedList<T> implements P6List<T> {
	private int chunkSize;
	private SinglyLinkedList<FixedSizeList<T>> chunks;

	public ChunkyLinkedList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new SinglyLinkedList<>();
	}

	/**
	 * Delete the item at the front of the list.
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override

	public T removeFront() {
		checkNotEmpty();
		FixedSizeList<T> chunk = chunks.getFront();
		T ret = chunk.removeFront();
		if (chunk.isEmpty()) {
			chunks.removeFront();
		}
		return ret;
	}

	/**
	 * Delete the item at the back of the list.
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeBack() {
		checkNotEmpty();
		FixedSizeList<T> chunk =chunks.getBack();
		T get =chunk.removeBack();
		if (chunk.isEmpty()) {
			chunks.removeBack();
		}
		return get;
	}

	/**
	 * Delete the item at the specified index in the list.
	 * 
	 * @param index a number from 0 to size (excluding size).
	 * @return the value that was removed.
	 * @throws EmptyListError if the list is empty.
	 * @throws BadIndexError  if the index does not exist.
	 */
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		if (index<0 || index>size()) {
			throw new BadIndexError();
		}
		if (index==0) {
			removeFront();
		}
		if (index==size()) {
			removeBack();
		}
		FixedSizeList<T> chunk = chunks.getIndex(index);
		T bow = chunk.removeIndex(index);
		if (chunk.isEmpty()) {
			chunks.removeIndex(index);
		}
		return bow;
	}

	/**
	 * Creates New Chunks
	 * @return new chunk/fixed size list
	 */
	public FixedSizeList<T> makeChunk(){
		return new FixedSizeList<T>(this.chunkSize);
	}
	
	/**
	 * Add an item to the front of this list. The item should be at getIndex(0)
	 * after this call.
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addFront(T item) {
		if (size()==0){
			FixedSizeList<T> first= makeChunk();
			chunks.addFront(first);		
		}
		
			FixedSizeList<T> front = chunks.getFront();
			if (front.size()==chunkSize) {
				front = makeChunk();
				chunks.addFront(front);
			}
			front.addFront(item);
	}

	/**
	 * Add an item to the back of this list. The item should be at
	 * getIndex(size()-1) after this call.
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addBack(T item) {
		if (size()==0){
			FixedSizeList<T> last= makeChunk();
			chunks.addBack(last);		
		}
		
		FixedSizeList<T> back = chunks.getBack();	
		if (back.size()==chunkSize) {
			back = makeChunk();
			chunks.addBack(back);
			
		}
		back.addBack(item);	
	}
	
	/**
	 * Add an item before ``index`` in this list. Therefore,
	 * {@code addIndex(item, 0)} is the same as {@code addFront(item)}.
	 * 
	 * @param item  the data to add to the list.
	 * @param index the index at which to add the item.
	 */
	@Override
	public void addIndex(T item, int index) {
		if (index<0 || index>size()) {
			throw new BadIndexError();
		}
		if (index == 0) {
			addFront(item);
			return;
		}
		if (index==size()) {
			addBack(item);
			return;
		}
		FixedSizeList<T> ply = chunks.getIndex(index);
		if (ply.size()==chunkSize) {
		ply = makeChunk();
		chunks.addFront(ply);
		}else {
			ply.addIndex(item,index);
		}
		
	}

	/**
	 * Get the first item in the list.
	 * @return the item.
	 * @throws EmptyListError
	 */
	@Override
	public T getFront() {
		if (isEmpty()) {
			throw new EmptyListError();
		}
		return this.chunks.getFront().getFront();
	}

	/**
	 * Get the last item in the list.
	 * @return the item.
	 * @throws EmptyListError
	 */
	@Override
	public T getBack() {
		if (isEmpty()) {
			throw new EmptyListError();
		}
		return this.chunks.getBack().getBack();
	}

	/**
	 * Find the index-th element of this list.
	 * 
	 * @param index a number from 0 to size, excluding size.
	 * @return the value at index.
	 * @throws BadIndexError if the index does not exist.
	 */
	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError();
	}

	/**
	 * Calculate the size of the list.
	 * 
	 * @return the length of the list, or zero if empty.
	 */
	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	/**
	 * This is true if the list is empty. This is usually implemented by looking at
	 * size() but that's a bad idea for linked lists.
	 * 
	 * @return true if the list is empty.
	 */
	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
	
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}
}
