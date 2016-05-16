package MovieCorner.model;

import java.io.Serializable;

public class Review implements Serializable
{
	private String review;
	private int userID;
	private int itemID;
	private MyDate dateAdded;
	private double rating;
	private String nickname;
	
	/**
	 * Constructor for creating a Review with todays date
	 * @param review The text of the review
	 * @param userID The ID of the user writing the review
	 * @param itemID The ID of the item to add the review to
	 * @param rating The rating that is given
	 * @param nickname The nickname of the user writing the review
	 */
	public Review(String review, int userID, int itemID, double rating, String nickname)
	{
		this(review, userID, itemID, rating, MyDate.today(), nickname);
	}
	
	/**
	 * Constructor for creating a Review with a specified date
    * @param review The text of the review
    * @param userID The ID of the user writing the review
    * @param itemID The ID of the item to add the review to
    * @param rating The rating that is given
	 * @param dateAdded The date the review was added
    * @param nickname The nickname of the user writing the review
	 */
	public Review(String review, int userID, int itemID, double rating, MyDate dateAdded, String nickname)
	{
		this.review=review;
		this.userID=userID;
		this.itemID=itemID;
		this.rating=rating;
		this.dateAdded=dateAdded;
		this.nickname = nickname;
	}
	
	/**
	 * 
	 * @return A string containing the nickname
	 */
	public String getNickname()
	{
		return nickname;
	}
	
	/**
	 * 
	 * @return A string containing the review
	 */
	public String getReview()
	{
		return review;
	}
	
	/**
	 * 
	 * @return An integer representing the user ID
	 */
	public int getUserID()
	{
		return userID;
	}
	
	/**
	 * 
	 * @return An integer representing the item ID
	 */
	public int getItemID()
	{
		return itemID;
	}
	
	/**
	 * 
	 * @return The MyDate-object for the Review-object
	 */
	public MyDate getDateAdded()
	{
		return dateAdded;
	}
	
	/**
	 * 
	 * @return A double representing the rating
	 */
	public double getRating()
	{
		return rating;
	}
		
}
