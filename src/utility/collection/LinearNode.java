package utility.collection;

public class LinearNode<T> {
	private T element;
	private LinearNode<T> next;
	
	public LinearNode()
	{
		next = null;
		element = null;
	}
	
	public LinearNode(T element)
	{
		next = null;
		this.element = element;
	}
	
	public LinearNode(T element, LinearNode<T> next)
	{
		this.next = next;
		this.element = element;
	}
	
	public LinearNode<T> getNext()
	{
		return next;
	}
	
	public void setNext(LinearNode<T> node)
	{
		this.next = node;
	}
	
	public T getElement()
	{
		return element;
	}
	
	public void setElement(T element)
	{
		this.element = element;
	}
}
