package MovieCorner.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class MyDate implements Serializable
{
	   private int day;
	   private int month;
	   private int year;
	 
	   /**
	    * MyDate class constructor.
	    * 
	    * @param day
	    *           an integer holding the day value
	    * @param month
	    *           an integer holding the month value
	    * @param year
	    *           an integer holding the year value
	    */
	   public MyDate(int day, int month, int year)
	   {
	      this.day = day;
	      this.month = month;
	      this.year = year;
	   }
	 
	   /**
	    * MyDate no-argument constructor. Creates a MyDate object with the current
	    * date's info.
	    */
	   public MyDate()
	   {
	      this.day = today().day;
	      this.month = today().month;
	      this.year = today().year;
	   }
	 
	   /**
	    * Returns the date's day.
	    * 
	    * @return an integer
	    */
	   public int getDay()
	   {
	      return day;
	   }
	 
	   /**
	    * Changes the date's day.
	    * 
	    * @param day
	    *           the new day
	    */
	   public void setDay(int day)
	   {
	      this.day = day;
	   }
	 
	   /**
	    * Returns the date's month.
	    * 
	    * @return an integer
	    */
	   public int getMonth()
	   {
	      return month;
	   }
	 
	   /**
	    * Changes the date's month.
	    * 
	    * @param month
	    *           the new month
	    */
	   public void setMonth(int month)
	   {
	      this.month = month;
	   }
	 
	   /**
	    * Returns the date's year.
	    * 
	    * @return an integer
	    */
	   public int getYear()
	   {
	      return year;
	   }
	 
	   /**
	    * Changes the date's year.
	    * 
	    * @param year
	    *           the new year
	    */
	   public void setYear(int year)
	   {
	      this.year = year;
	   }
	 
	   /**
	    * Returns a date as a String.
	    * 
	    * @return a String holding the date in the format of "day/month/year"
	    */
	   public String toString()
	   {
	      return day + "/" + month + "/" + year;
	   }
	 
	   /**
	    * Compares two MyDate objects.
	    * 
	    * @return true if the dates are equal, else false
	    */
	   public boolean equals(Object obj)
	   {
	      if (!(obj instanceof MyDate))
	         return false;
	 
	      MyDate other = (MyDate) obj;
	 
	      return day == other.day && month == other.month && year == other.year;
	   }
	 
	   /**
	    * Returns the current date.
	    * 
	    * @return a MyDate object holding today's date
	    */
	   public static MyDate today()
	   {
	      GregorianCalendar currentDate = new GregorianCalendar();
	       
	      int currentDay = currentDate.get(GregorianCalendar.DATE);
	      int currentMonth = currentDate.get(GregorianCalendar.MONTH) + 1;
	      int currentYear = currentDate.get(GregorianCalendar.YEAR);
	 
	      return new MyDate(currentDay, currentMonth, currentYear);
	   }
	 
	   /**
	    * Checks if the date's year is/was a leap year (February 29).
	    * 
	    * @return true if it is/was a leap year, else false
	    */
	   public boolean isLeapYear()
	   {
	      if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
	         return true;
	      else
	         return false;
	   }
	 
	   /**
	    * Calculates how many days the date's month has.
	    * 
	    * @return an integer holding the numbers of days in the month
	    */
	   public int daysInMonth()
	   {
	      if (month <= 8)
	      {
	         if ((month % 2) == 0 && (month != 2))
	            return 30;
	         else if (month == 2 && isLeapYear())
	            return 29;
	         else if (month == 2 && !isLeapYear())
	            return 28;
	         else
	            return 31;
	      }
	      else if (month % 2 == 0)
	         return 31;
	      else
	         return 30;
	   }
	 
	   /**
	    * Increases the day by 1 and modifies the date according to the new date. <br />
	    * (28 February 2001 -> 01 March 2001)
	    */
	   public void nextDay()
	   {
	      day++;
	      if (day > this.daysInMonth())
	      {
	         day = 1;
	         month++;
	         if (month > 12)
	         {
	            month = 1;
	            year++;
	         }
	      }
	   }
	 
	   /**
	    * Checks if today's day is 5 days before the release date.
	    * 
	    * @return true if it is, else false
	    */
	   public boolean isBefore()
	   {
	      MyDate temp = new MyDate();
	 
	      for (int i = 0; i < 5; i++)
	      {
	         temp.nextDay();
	      }
	 
	      return (temp.equals(new MyDate(day, month, year)));
	   }
	 
	   /**
	    * Checks if the release date is today or if it is past the release date
	    * 
	    * @return true if it the release date is today or has been passed, false if it is before the release date
	    */
	   public boolean isPast()
	   {
		   
		   MyDate temp= MyDate.today();
		   
		   if(year<temp.getYear())
			   return true;
		   
		   if(year==temp.getYear())
		   {
			   if(month<temp.getMonth())
				   return true;
		   }
		   
		   if(year==temp.getYear() && month==temp.getMonth())
		   {
			   if(day<=temp.getDay())
				   return true;
		   }
		
		   return false;
	   }
	   
	   public MyDate copy()
	   {
		   return new MyDate(day, month, year);
	   }

}
