package JUnitTestModelManager;

import MovieCorner.model.MyDate;

public class MyDateTest 
{
	public static void main(String[] args)
	{
		MyDate today=  new MyDate(20,5,2015);
		MyDate month= new MyDate(21,5,2015);
		MyDate month2= new MyDate(19,5,2015);
		MyDate year= new MyDate(19,5,2014);
		MyDate year2= new MyDate(19,5,2016);
		MyDate day= new MyDate(26,3,2015);
		MyDate day2= new MyDate(27,6,2015);
		
		System.out.println(today.isPast());
		System.out.println(month.isPast());
		System.out.println(month2.isPast());
		System.out.println(year.isPast());
		System.out.println(year2.isPast());
		System.out.println(day.isPast());
		System.out.println(day2.isPast());
		
	}
}
