package utility.collection;

public class LinkedQueue<T> implements QueueADT<T> {
	
	private int count;
	private LinearNode<T> front;
	
	/**
	 * LinkedQueue implementation using a LinearNode<br />
	 * This implementation does not use a dummy node
	 */
	public LinkedQueue()
	{
		count = 0;
		front = null;
	}
	
	public void enqueue(T element) {
		if(isEmpty())
		{
			front = new LinearNode<T>(element);
			count++;
		}
		else
		{
			LinearNode<T> current = front;
			
			for(int i = 0; i < count-1; i++)
				current = current.getNext();
			
			current.setNext(new LinearNode<T>(element));
			count++;
		}
	}


	public T dequeue() {
		if(isEmpty())
			return null;
		
		T element = front.getElement();
		
		front = front.getNext();
		
		count--;
		
		return element;
	}


	public T first() {
		if(isEmpty())
			return null;
		
		
		T element = front.getElement();
		
		return element;
	}


	public int indexOf(T element) {
		if(isEmpty())
			return -1;
		
		LinearNode<T> current = front;
		
		for(int i = 0; i < count; i++)
		{
			if(element == null || current.getElement() == null)
			{
				if(current.getElement() == null)
					return i;
			}
			else
				if(current.getElement().equals(element))
					return i;
			
			current = current.getNext();
		}
		
		return -1;
	}
	
	public String toString()
	{
		String str = "{";
		
		LinearNode<T> current = front;
		
		for(int i = 0; i < count; i++)
		{
			str += current.getElement();
			if(i!=count) str += ", ";
			current = current.getNext();
		}
		
		str += "}";
		
		return str;
	}
	
	@Override
	public boolean isEmpty() {
		return (count==0);
	}

	@Override
	public int size() {
		return count;
	}

}
