package MovieCorner.model;

import java.io.Serializable;
import utility.collection.ArrayList;

public abstract class Item implements Serializable
{

   private String description;
   private String title;
   private int id;
   private MyDate releaseDate;
   private ArrayList<String> genres;
   private ArrayList<String> tags;
   private String director;
   private ArrayList<Comment> comments;

   /**
    * Constructor for creating an item
    * 
    * @param description
    *           Description of the item
    * @param title
    *           Title of the item
    * @param releaseDate
    *           Date of the item's release
    * @param genres
    *           Category of genres ( can be horror, action etc)
    * @param tags
    *           ArrayList of tags that represent keywords for the item
    * @param director
    *           Director of the movie
    * @param id
    *           Id of the item which is used to uniquely identify the item
    * @param comments
    * 			ArrayList containing all the comments for the item
    */
   public Item(String description, String title, MyDate releaseDate,
         ArrayList<String> genres, ArrayList<String> tags, String director,
         int id, ArrayList<Comment> comments)
   {
      this.description = description;
      this.title = title;
      this.releaseDate = releaseDate;
      this.genres = genres;
      this.tags = tags;
      this.director = director;
      this.id = id;
      this.comments=comments;
      

   }
   
   /**
    * Constructor for creating an item
    * 
    * @param description
    *           Description of the item
    * @param title
    *           Title of the item
    * @param releaseDate
    *           Date of the item's release
    * @param genres
    *           Category of genres ( can be horror, action etc)
    * @param tags
    *           ArrayList of tags that represent keywords for the item
    * @param director
    *           Director of the movie
    * @param id
    *           Id of the item which is used to uniquely identify the item
    */
   public Item(String description, String title, MyDate releaseDate,
         ArrayList<String> genres, ArrayList<String> tags, String director,
         int id)
   {
	   this(description, title, releaseDate, genres, tags, director, id,null);
   }

   /**
    * @return Returns the description of the item
    */
   public String getDescription()
   {
      return description;

   }

   /**
    * @return Returns the title for the item
    */
   public String getTitle()
   {
      return title;
   }

   /**
    * @return Returns the item's id
    */
   public int getId()
   {
      return id;
   }

   /**
    * @return Returns the item's release date
    */
   public MyDate getReleaseDate()
   {
      return releaseDate.copy();
   }

   /**
    * @return Returns all the available genres
    */
   public ArrayList<String> getGenres()
   {
      return genres;
   }

   /**
    * @return Returns all the available tags
    */
   public ArrayList<String> getTags()
   {
      return tags;
   }

   /**
    * @return Returns the director of the movie
    */
   public String getDirector()
   {
      return director;
   }
   /**
    * Gets the comments in Item based on userID
    * @param userID the ID of the user whos comment needs to be found
    * @return an Array containing all the comments made by the  user with userID as ID
    */
   public Comment[] getCommentsByUserID(int userID)
   {
	   if(comments==null)
		   return null;
	   
	   ArrayList<Comment> temp= new ArrayList<Comment>();
	   
	   for(int i=0; i<comments.size(); i++)
	   {
		   if(comments.get(i).getUserID()==userID)
			   temp.add(comments.get(i));
		   
	   }
	   
	   return temp.toArray(new Comment[temp.size()]);
   }
   
   /**
    * Adds a comment to the Item with containing the userID, text and nickname
    * @param userID	ID of the user adding the comment
    * @param text	text that should be displayed in the comment
    * @param nickname	nickname of the user commenting
    * @param the date the comment was added
    */
   public void addComment(int userID, String text, String nickname, MyDate date)
   {
	   comments.add(new Comment(userID,text,nickname, date));
   }
   /**
    * Gets all the comments stored in the Item
    * @return an ArrayList containing all the comments left for the item
    */
   public ArrayList<Comment> getAllComments()
   {
	   return comments;
   }

   /**
    * Abstract equals method that will be implemented by the Item subclasses
    */
   public abstract boolean equals(Object obj);

   public String toString() {
      return title;
   }
   
   /**
    * Checks how many of the tags on this object matches with the tags passed as an argument
    * @param tags The tags to match with
    * @return The amount of matches
    */
   public int matchTags(ArrayList<String> tags)
   {
	   if(tags == null)
		   return 0;
	   
	   if(tags.size() == 0)
		   return 0;
	   
	   int matches = 0;
	   
	   for(String tag : tags)
	   {
		   if(this.tags.size() > 0)
			   if(this.tags.contains(tag))
				   matches++;
	   }
	   
	   return matches;
   }
}
