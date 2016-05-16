package utility.collection;

public interface QueueADT<T> {
	/**
	 * Adds an element to the queue
	 * 
	 * @param element The element to add to the queue
	 */
	public void enqueue(T element);
	
	/**
	 * Removes and returns the first element in the queue
	 * 
	 * @return Returns the first element in the queue
	 */
	public T dequeue();
	
	/**
	 * Peeks in the queue and shows the first element
	 * 
	 * @return The first element in the queue
	 */
	public T first();
	
	/**
	 * Looks in the queue for a specific element
	 * 
	 * @return Returns the position of the element if it is found, otherwise -1.
	 */
	public int indexOf(T element);
	
	
	public boolean isEmpty();
	public int size();
}
