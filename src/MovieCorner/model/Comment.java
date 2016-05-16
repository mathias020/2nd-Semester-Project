package MovieCorner.model;

import java.io.Serializable;


public class Comment implements Serializable
{
	private int userID;
	private String text;
	private String nickname;
	private int id;
	private MyDate date;
	
	/**
	 * Constructor for the Comment that does not take an ID for the comment itself
	 * @param userID	ID of the user entering the comment
	 * @param text		Text that has to appear in the comment
	 * @param nickname	nickname of the user entering the comment
	 * @param date		the date of the day the user entered the comment
	 */
	public Comment(int userID, String text, String nickname, MyDate date)
	{
		this(userID,text, nickname,0, date);
	}
	
	/**
	 * Constructor for the Comment that does not take an ID for the comment itself
	 * @param userID	ID of the user entering the comment
	 * @param text		Text that has to appear in the comment
	 * @param nickname	nickname of the user entering the comment
	 * @param date		the date of the day the user entered the comment
	 * @param id		id of the comment itself
	 */
	public Comment(int userID, String text, String nickname, int id, MyDate date)
	{
		this.userID=userID;
		this.nickname=nickname;
		this.text=text;
		this.id=id;
		this.date=date;
	}
	/**
	 * Gets the ID of the user who created the comment
	 * @return an int containing the ID of the user that left the comment
	 */
	public int getUserID()
	{
		return userID;
	}
	/**
	 * Gets the text stored in the comment
	 * @return a string containing the text that was stored as a comment by a user
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * Gets the ID of the comment
	 * @return an int containing the ID of the comment
	 */
	public int getID()
	{
		return id;
	}
	/**
	 * Allows for a change of the text inside the comment
	 * @param text the new text that is wished to be stored
	 */
	public void setText(String text)
	{
		this.text=text;
	}
	/**
	 * Gets the nickname of the user who entered the comment
	 * @return a string containing the nickname of the user who left the comment
	 */
	public String getNickname()
	{
		return nickname;
	}
	
	/**
	 * Gets the date the comment was created
	 * @return a MyDate containing the date the comment was created
	 */
	public MyDate getDate()
	{
		return date.copy();
	}
	
}
