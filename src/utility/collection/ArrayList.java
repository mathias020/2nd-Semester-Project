package utility.collection;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements Iterable<T>, ListADT<T>, Serializable {

	private static final int DEFAULT_CAPACITY = 100;
	private int size;
	private int capacity;
	private T[] list;
	private boolean isBounded;
	
	
	/**
	 * Constructor taking an initial capacity of the underlying array. The ArrayList will automatically expand when it has reached its capacity.
	 * 
	 * @param initialCapacity The initial capacity of the underlying array
	 */
	public ArrayList(int initialCapacity)
	{
		size = 0;
		isBounded = false;
		list = (T[]) new Object[initialCapacity];
		capacity = initialCapacity;
	}
	
	public ArrayList(ArrayList<T> list)
	{
		this.list = (T[]) new Object[list.size()];
		isBounded = false;
		capacity = list.capacity();
		
		addAll(list);
	}
	
	/**
	 * Constructor that allows to add a bound to the ArrayList so that it will not expand when reaching its capacity.
	 * 
	 * @param maxCapacity The maximum capacity of the ArrayList
	 * @param isBounded Boolean indicating if the ArrayList should be bounded or not
	 */
	public ArrayList(int maxCapacity, boolean isBounded)
	{
		size = 0;
		this.isBounded = isBounded;
		list = (T[]) new Object[maxCapacity];
		capacity = maxCapacity;
	}
	
	/**
	 * No-arg constructor creating an ArrayList with an initial capacity of 100. The capacity will expand when the ArrayList reaches its capacity.
	 */
	public ArrayList()
	{
		size = 0;
		isBounded = false;
		list = (T[]) new Object[DEFAULT_CAPACITY];
		capacity = DEFAULT_CAPACITY;
		
	}
	
	
	/**
	 * Method for adding an element at a specified index.
	 * 
	 * @param index The index at where to insert the element
	 * @param element The element to insert
	 * 
	 * @throws IllegalStateException
	 * 					If the list is bounded and it is already full
	 * 
	 * @throws IndexOutOfBoundsException
	 * 					If the index is not within range of the ArrayList or less than 0.
	 */
	public void add(int index, T element) {
		if(isBounded && size == list.length)
			throw new IllegalStateException("The list is full");
		
		if(index > size || index < 0)
			throw new IndexOutOfBoundsException("Index of out bounds");

		if(size == list.length)
			expandCapacity();
		
		for(int i = size-1; i >= index; i--)
		{
			list[i+1] = list[i];
		}
		
		list[index] = element;
		size++;
	}
	
	
	/**
	 * Insert an element at the end of the ArrayList
	 * 
	 * @param element The element to insert
	 */
	public void add(T element) {
		add(size, element);
	}

	/**
	 * Set the element at a specified index
	 * 
	 * @param index The index at where to set the element
	 * @param element The element to be set
	 * 
	 * @throws IndexOutOfBoundsException 
	 * 				If the index is not within range of the ArrayList or less than 0.
	 */
	public void set(int index, T element) {
		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException("Index of out bounds");
		
		
		list[index] = element;
		
	}

	/**
	 * Get an element at a specified index
	 * 
	 * @param index The index from where to get the element
	 * 
	 * @throws IndexOutOfBoundsException 
	 * 				If the index is not within range of the ArrayList or less than 0.
	 * 
	 * @returns The element at the specified index
	 */
	public T get(int index) {
		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException("Index of out bounds");

		return list[index];
	}

	/**
	 * Remove an element at a specified index
	 * 
	 * @param index The index 
	 * 
	 * @throws IndexOutOfBoundsException 
	 * 				If the index is not within range of the ArrayList or less than 0.
	 * 
	 * @returns The element that has been removed
	 */
	public T remove(int index) {
		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException("Index of out bounds");
		
		T tmp = list[index];
		
		for(int i = index; i <size-1; i++)
		{
			list[i] = list[i+1];
		}
		
		list[size-1] = null;
		size--;
		
		return tmp;
	}

	/**
	 * Remove a specified element from the ArrayList
	 * 
	 * @param element The element to remove from the list
	 * 
	 * @returns The element that has been removed if it was found, otherwise null.
	 */
	public T remove(T element) {
		int pos = indexOf(element);
		
		return pos>-1 ? remove(pos) : null;
	}

	/**
	 * Gets the index of a specified index
	 * 
	 * @param element The element to find the index for
	 * 
	 * @returns Returns the position of the element if it is found, otherwise -1.
	 */
	public int indexOf(T element) {
		int pos = -1;
		
		if(element == null)
		{
			for(int i = 0; i < size; i++)
			{
				if(element == list[i])
				{
					pos = i;
					break;
				}
			}
		}
		else
		{
			for(int i = 0; i < size; i++)
			{
				if(list[i] == null)
				{
					if(element == list[i])
					{
						pos = i;
						break;
					}
				}
				else
				{
					if(list[i].equals(element))
					{
						pos = i;
						break;
					}
				}
			}
		}
		
		return pos;
	}

	/**
	 * Checks the ArrayList for a specified element
	 * 
	 * @param element The element to look for
	 * 
	 * @returns Returns true if the element was found, otherwise false.
	 */
	public boolean contains(T element) {
		return (indexOf(element) != -1);
	}

	public boolean isEmpty() {
		return (size==0);
	}

	@Override
	public int size() {
		return size;
	}
	
	public boolean isBounded()
	{
		return isBounded;
	}
	
	public String toString()
	{
		String str = "";
		
		for(int i = 0; i < size; i++)
		{
			str += list[i];
			if(i != size()-1) str += ", ";
		}
		
		return str;
	}
	
	private void expandCapacity()
	{
		if(!isBounded)
		{
			T[] temp = (T[]) new Object[list.length*2];
			
			for(int i = 0; i < size(); i++)
			{
				temp[i] = list[i];
			}
			
			capacity = list.length*2;
			
			list = temp;
		}
	}
	
	/**
	 * The iterator class for the ArrayList
	 * @author Jonas
	 *
	 */
	private class ArrayListIterator implements Iterator<T>
	{
		private int currentPos;
		
		/**
		 * Creates a new iterator starting at position 0
		 */
		public ArrayListIterator()
		{
			currentPos = 0;
		}
		
		/**
		 * Checks if there is any elements left in the ArrayList
		 * 
		 * @return Returns true if there is elements left, otherwise false.
		 */
		public boolean hasNext() {
			return currentPos < size();
		}

		/**
		 * Gets the next element in the ArrayList and increases the iterators current position
		 * 
		 * @return The next element in the ArrayList
		 * 
		 * @throws NoSuchElementException
		 */
		public T next() {
			if(hasNext())
				return (T) get(currentPos++);
			else
				throw new NoSuchElementException();
		}
	}



	/**
	 * Retrieves a new iterator for the ArrayList
	 * 
	 * @return A new iterator for the ArrayList
	 */
	public Iterator<T> iterator() {
		return new ArrayListIterator();
	}

	/**
	 * Generates an Object-array of the ArrayList
	 * 
	 * @returns An Object-array with all the elements from the ArrayList
	 */
	public Object[] toArray() {
		if(size() < 1)
			return new Object[0];
		
		Object[] arr = new Object[size()];
		
		for(int i = 0; i < size(); i++)
			arr[i] = get(i);
		
		return arr;
	}
	
	/**
	 * Generates a generic array from the elements inside the ArrayList
	 * 
	 * @returns A generic array containing the elements with from the ArrayList
	 */
	public T[] toArray(T[] array) {
		if(array == null)
			return (T[]) new Object[0];
		
		if(array.length != size())
			throw new IllegalArgumentException("Array must be of the same length as the ArrayList");
		
		for(int i = 0; i < size(); i++)
			array[i] = get(i);
		
		return array;
	}
	
	/**
	 * @returns The current maximum capacity of the ArrayList
	 */
	public int capacity()
	{
		return capacity;
	}

	/**
	 * Adds the content of the ArrayList given as a parameter at the end of this ArrayList.
	 * 
	 * @param list The ArrayList to add from
	 */
	public void addAll(ArrayList<T> list) {
		if(list == null)
			return;
		
		for(T element : list)
			add(element);
	}
	
}
