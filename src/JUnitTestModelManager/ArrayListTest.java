package JUnitTestModelManager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.collection.ArrayList;

public class ArrayListTest {
	
	private ArrayList<String> list;
	
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<String>(5);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddAtIndex() {
		list.add("Element #1");
		list.add("Element #2");
		
		list.add(1, "Element #3");
		
		assertEquals("Element #3", list.get(1));
		assertEquals("Element #2", list.get(2));
	}
	
	@Test
	public void testAdd()
	{
		list.add("Element #1");
		list.add("Element #2");
		
		assertEquals("Element #1, Element #2", list.toString());
	}
	
	@Test
	public void testSet()
	{
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		
		list.set(2, "Element #4");
		
		assertEquals("Element #4", list.get(2));
	}
	
	@Test
	public void testGet()
	{
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals("Element #1", list.get(0));
		assertEquals("Element #2", list.get(1));
		assertEquals("Element #3", list.get(2));
		assertEquals("Element #4", list.get(3));
	}
	
	@Test
	public void testRemoveAtIndex()
	{
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals("Element #1, Element #2, Element #3, Element #4", list.toString());
		
		list.remove(0);
		
		assertEquals("Element #2, Element #3, Element #4", list.toString());
	}
	
	@Test
	public void testRemoveElement()
	{
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals("Element #1, Element #2, Element #3, Element #4", list.toString());
		
		list.remove("Element #2");
		
		assertEquals("Element #1, Element #3, Element #4", list.toString());
	}
	
	@Test
	public void testIndexOf()
	{
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals(2, list.indexOf("Element #3"));
		assertEquals(3, list.indexOf("Element #4"));
		
		assertEquals(-1, list.indexOf("This aint there"));
	}
	
	@Test
	public void testContains()
	{
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals(true, list.contains("Element #1"));
		assertEquals(true, list.contains("Element #3"));
		
		assertEquals(false, list.contains("This aint there"));
	}
	
	@Test
	public void testIsEmpty()
	{
		assertEquals(true, list.isEmpty());
		
		list.add("Element #1");
		
		assertEquals(false, list.isEmpty());
		
		list.remove(0);
		
		assertEquals(true, list.isEmpty());
	}
	
	@Test
	public void testSize()
	{
		assertEquals(0, list.size());
		
		list.add("Element #1");
		list.add("Element #2");
		
		assertEquals(2, list.size());
		
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals(4, list.size());
		
		list.remove("Element #1");
		list.remove("Element #2");
		list.remove("Element #3");
		list.remove("Element #4");
		
		assertEquals(0, list.size());
	}
	
	
	@Test
	public void testToArray()
	{
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		String[] array = list.toArray(new String[list.size()]);
		
		for(int i = 0; i < array.length; i++)
			assertEquals(list.get(i), array[i]);
	}

	
	@Test
	public void testExpandCapacity()
	{
		list = new ArrayList<String>(5);
		
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals(4, list.size());
		assertEquals(5, list.capacity());
		
		list.add("Element #5");
		
		assertEquals(5, list.size());
		assertEquals(5, list.capacity());
		
		list.add("Element #6");
		
		assertEquals(6, list.size());
		assertEquals(10, list.capacity());
	}

	@Test(expected=IllegalStateException.class)
	public void testIsBounded()
	{
		list = new ArrayList<String>(5, true);
		
		list.add("Element #1");
		list.add("Element #2");
		list.add("Element #3");
		list.add("Element #4");
		
		assertEquals(4, list.size());
		assertEquals(5, list.capacity());
		
		list.add("Element #5");
		
		assertEquals(5, list.size());
		assertEquals(5, list.capacity());
		
		list.add("Element #6");
		
		assertEquals(6, list.size());
		assertEquals(10, list.capacity());
	}
}
